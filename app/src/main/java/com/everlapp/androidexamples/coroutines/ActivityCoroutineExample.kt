package com.everlapp.androidexamples.coroutines

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.everlapp.androidexamples.R
import com.everlapp.androidexamples.coroutines.restapi.RetrofitFactory
import kotlinx.coroutines.*
import retrofit2.HttpException
import timber.log.Timber

class ActivityCoroutineExample : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutine_example)

        // CoroutinesTest.coroutineTestOne()

        // getPosts()

        coroutineExample()
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


    /** ========================================================================================= */


    private fun coroutineExample() {
        // job - используется для отмены выполнения или для его ожидания - (join())
        // GlobalScope - время жизни как у всего приложения
//        runBlocking {     // Это выражение блокирует главный поток JVM
//            delay(2000L)  // ... while we delay for 2 seconds to keep JVM alive
//        }
    }


    fun exampleBlockMainThread() = runBlocking<Unit> { // start main coroutine
        GlobalScope.launch { // launch a new coroutine in background and continue
            delay(1000L)
            println("World!")
        }
        println("Hello,") // main coroutine continues here immediately
        delay(2000L)      // delaying for 2 seconds to keep JVM alive
    }


    // runBlocking - удобно применять для тестирования приостановленных (suspend) функций
    class MyTest {
        //@Test
        fun testMySuspendingFunction() = runBlocking<Unit> {
            // here we can use suspending functions using any assertion style that we like
        }
    }


    // Waiting a Job
    // Ставить delay() после job плохая практика, необходимо join()
    private fun waitingJob() = runBlocking {
        //sampleStart
            val job = GlobalScope.launch { // launch a new coroutine and keep a reference to its Job
                delay(1000L)
                println("World!")
            }
            println("Hello,")
            job.join() // wait until child coroutine completes
        //sampleEnd
    }


    // Structured concurrency
    // // this: CoroutineScope
    private fun structuredConcurrency() = runBlocking {
        // launch a new coroutine in the scope of runBlocking
        launch {
            delay(1000)
            print("World!")
        }
        print("Hello")
    }


    // Можно создать свой coroutineScope builder
    // Он создаст область действия сопрограммы и не завершится, пока не завершаться все запущенные дочерние элементы
    // runBlocking - regular function
    fun scopeBuilder() = runBlocking { // this: CoroutineScope
        launch {
            delay(200L)
            println("Task from runBlocking")
        }

        // suspending function
        coroutineScope { // Creates a coroutine scope
            launch {
                delay(500L)
                println("Task from nested launch")
            }

            delay(100L)
            println("Task from coroutine scope") // This line will be printed before the nested launch
        }

        println("Coroutine scope is over") // This line is not printed until the nested launch completes
    }



    /**
     * Sequence - реализуют генераторы, т. е. предоставляют лёгкую возможность построить ленивые последовательности:
     */
    private fun sequenceExam() {
        val fibonacciSeq = sequence {
            var a = 0
            var b = 1

            yield(1)

            while (true) {
                yield(a + b)

                val tmp = a + b
                a = b
                b = tmp
            }
        }

        // Print the first eight Fibonacci numbers
        println(fibonacciSeq.take(8).toList())
    }


}