package com.everlapp.androidexamples.coroutines

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.everlapp.androidexamples.R
import com.everlapp.androidexamples.coroutines.restapi.RetrofitFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber

class ActivityCoroutineExample : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutine_example)

        // CoroutinesTest.coroutineTestOne()

        getPosts()
    }



    private fun getPosts() {
        val service = RetrofitFactory.makeRetrofitService()
        GlobalScope.launch(Dispatchers.Main) {
            val request = service.getPosts()

            try {
                val response = request.await()

                // Do something with response body
                val posts = response.body()

                posts?.let {
                    for (post in it) {
                        Timber.d("Post ${post.id} Title: ${post.title}")
                    }
                }
            } catch (e: HttpException) {
                Timber.e(e)
            } catch (e: Throwable) {
                Timber.e("something else went wrong")
            }

        }
    }

}