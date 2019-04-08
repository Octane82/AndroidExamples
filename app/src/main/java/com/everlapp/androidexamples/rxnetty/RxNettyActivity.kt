package com.everlapp.androidexamples.rxnetty

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.everlapp.androidexamples.R
import com.google.gson.Gson
import io.netty.handler.logging.LogLevel
import io.reactivex.netty.protocol.http.client.HttpClient
import io.reactivex.netty.protocol.http.server.HttpServer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import rx.Observable
import timber.log.Timber
import java.net.InetSocketAddress
import java.nio.charset.Charset
import java.util.*

/**
 * https://www.oreilly.com/learning/designing-a-reactive-http-server-with-rxjava
 */
class RxNettyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rxnetty)

        testRxNetty()
    }


    private fun testRxNetty() {
        val serverAddress = InetSocketAddress("127.0.0.1", 2025)

        val jsonString = getStringFromAssetFile("user_response.json")

        // Start server
        val server =  HttpServer.newServer(serverAddress)
                // .start { req, resp -> resp.writeString(Observable.just<String>("Hello World!")) }
                .start { req, resp -> resp.writeString(Observable.just<String>(jsonString)) }

        // Start RxNetty http client
        HttpClient.newClient(serverAddress)
                .enableWireLogging("hello-client", LogLevel.ERROR)
                .createGet("/hello")
                .doOnNext { resp -> kotlin.run {
                    Timber.d("Response from server: $resp")
                }}
                .flatMap { resp -> resp.content.map { bb -> bb.toString(Charset.defaultCharset()) } }
                .toBlocking()
                .forEach { body -> kotlin.run { Timber.d("LOG_Client Message YAY $body") } }

        // OkHttp client
        val httpClient = OkHttpClient()
        val request = Request.Builder()
                .url("http://127.0.0.1:2025")
                .build()

        GlobalScope.launch(Dispatchers.IO) {
            val response = httpClient.newCall(request).execute()
            val user = Gson().fromJson(response.body()?.string(), UserEntity::class.java)

            Timber.d("OkHttp response (Parse user primary phone): ${user.primaryPhone}")
        }
    }


    private fun getStringFromAssetFile(fileName : String) : String {
        val input = resources.assets.open(fileName)
        val scanner = Scanner(input).useDelimiter("\\A")
        val result = if (scanner.hasNext()) scanner.next() else ""
        input.close()
        return result
    }


}