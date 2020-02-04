package com.everlapp.androidexamples.coroutines

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.everlapp.androidexamples.R
import com.everlapp.androidexamples.coroutines.restapi.RetrofitFactory
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.flow.*

import retrofit2.HttpException
import timber.log.Timber
import kotlin.sequences.take
import kotlin.sequences.toList
import kotlin.system.measureTimeMillis

/**
 * https://github.com/Kotlin/kotlinx.coroutines/blob/master/coroutines-guide.md#composing-suspending-functions
 */
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

            //isActive - флаг (вутри Coroutine scope) проверки не отменена ли корутина вызовом cancelAndJoin()
        }

        println("Coroutine scope is over") // This line is not printed until the nested launch completes
    }


    /**
     * Job будет автоматически отменена через 5 секунд
     */
    private fun cancelJobWithTimeout() = runBlocking {
        // Метод withTimeout выбрасывает exception при остановке
        // есть другой метод --- val result = withTimeoutOrNull(1300L) - который отдает NULL вместо Exception
        withTimeout(5000) {
            repeat(1000) { i ->
                print("Run $i ...")
                delay(500)
            }
        }
    }



    // ========== Execution coroutines ===============

    /**
     * По умолчанию исполнение корутин выполняется последовательно (друг за другом)
     * Получим ответ медленнее чем async
     */
    private fun sequentalyExecution() = runBlocking {
        //sampleStart
        val time = measureTimeMillis {
            val one = doSomethingUsefulOne()
            val two = doSomethingUsefulTwo()
            println("The answer is ${one + two}")
        }
        println("Completed in $time ms")
        //sampleEnd
    }


    /**
     * Если нет разницы в порядке выполнения suspend методов можно вызывать асинхронное выполнение
     * Ответ получим быстрее !!!
     */
    private fun asyncExecution() = runBlocking {
        //sampleStart
        val time = measureTimeMillis {
            // two coroutines execute concurrently
            val one = async { doSomethingUsefulOne() }
            val two = async { doSomethingUsefulTwo() }
            println("The answer is ${one.await() + two.await()}")
        }
        println("Completed in $time ms")
        //sampleEnd
    }


    /**
     * Ленивое исполнение корутин
     * Запускается исполнение, когда вызван await() или если вызывается функция запуска ее задания
     */
    private fun lazyStartCoroutine() = runBlocking {
        //sampleStart
        val time = measureTimeMillis {
            val one = async(start = CoroutineStart.LAZY) { doSomethingUsefulOne() }
            val two = async(start = CoroutineStart.LAZY) { doSomethingUsefulTwo() }
            // some computation
            one.start() // start the first one
            two.start() // start the second one
            println("The answer is ${one.await() + two.await()}")
        }
        println("Completed in $time ms")
        //sampleEnd
    }




    suspend fun doSomethingUsefulOne(): Int {
        delay(1000L) // pretend we are doing something useful here
        return 13
    }

    suspend fun doSomethingUsefulTwo(): Int {
        delay(1000L) // pretend we are doing something useful here, too
        return 29
    }
    // ============== DISPATCHERS ================================


    private fun dispatchersExample() = runBlocking{
        // newSingleThreadContext("myOwnThread") - создает поток для корутины

    }


    private fun childrenCoroutine() = runBlocking{
        //sampleStart
        // launch a coroutine to process some kind of incoming request
        val request = launch {
            // it spawns two other jobs, one with GlobalScope
            // GlobalScope не отменяется, если отменяется родительская корутина
            GlobalScope.launch {
                println("job1: I run in GlobalScope and execute independently!")
                delay(1000)
                println("job1: I am not affected by cancellation of the request")
            }
            // and the other inherits the parent context
            // launch{ } - оменяется, если отменяется родительская корутина
            launch {
                delay(100)
                println("job2: I am a child of the request coroutine")
                delay(1000)
                println("job2: I will not execute this line if my parent request is cancelled")
            }
        }
        delay(500)
        request.cancel() // cancel processing of the request
        delay(1000) // delay a second to see what happens
        println("main: Who has survived request cancellation?")
        //sampleEnd
    }

    /**
     * Родительская корутина всегда ждет завершения всех своих потомков
     */
    private fun parentalResponsibilitiesExample() = runBlocking {
        //sampleStart
        // launch a coroutine to process some kind of incoming request
        val request = launch {
            repeat(3) { i -> // launch a few children jobs
                launch  {
                    delay((i + 1) * 200L) // variable delay 200ms, 400ms, 600ms
                    println("Coroutine $i is done")
                }
            }
            println("request: I'm done and I don't explicitly join my children that are still active")
        }
        request.join() // Ожидает завершение запроса, включая всех его детей
        println("Now processing of the request is complete")
        //sampleEnd
    }


    /**
     * С помощью + можем комбинировать различные параметры, например Dispatcher and custom coroutine name
     */
    private fun combiningContextElements() = runBlocking {
        //sampleStart
        // CoroutineName - присваиваем свое имя корутине (test#1)
        launch(Dispatchers.Default + CoroutineName("test")) {
            println("I'm working in thread ${Thread.currentThread().name}")
        }
        //sampleEnd
    }



    // ================ Async flow  ==============================

    private fun fooFlow() : Flow<Int> = flow {
        for (i in 1..3) {
            delay(100)
            emit(i)
        }
    }

    private suspend fun performRequest(request: Int): String {
        delay(1000) // imitate long-running asynchronous work
        return "response $request"
    }


    private fun flowOnOperatorExample(): Flow<Int> = flow {
        for (i in 1..3) {
            Thread.sleep(100) // pretend we are computing it in CPU-consuming way
            Timber.d("Emitting $i")
            emit(i) // emit next value
        }
    }.flowOn(Dispatchers.Default) // RIGHT way to change context for CPU-consuming code in flow builder


    /**
     * Example flow operator
     */
    @ExperimentalCoroutinesApi
    private fun flowExample() = runBlocking {
        fooFlow()
                .onCompletion { Timber.d("Done!") }
                // Or (completion)
                .onCompletion { cause -> if (cause != null) println("Flow completed exceptionally") }
                .catch { cause -> println("Caught exception") }
                // -----
                .collect { value -> println(value) }

        (1..3).asFlow().collect { i -> println(i) }

        (1..3).asFlow()
                // Поток входящих запросов может быть сопоставлен с параметрами Request
                // Может применяться map, даже если request длительная операция
                .map { request -> performRequest(request) }
                .collect { response -> println(response) }

        // Transform flow
        (1..3).asFlow() // a flow of requests
                .transform { request ->
                    emit("Making request $request")
                    emit(performRequest(request))
                }
                .collect { response -> println(response) }

        (1..10).asFlow()
                .buffer()   // // buffer emissions, don't wait
                .collect { i -> print(i) }
    }


    @ExperimentalCoroutinesApi
    private fun composingFlows() = runBlocking {
        val nums = (1..3).asFlow()
        val strs = flowOf("one", "two", "three")

        // Zip operator
        nums.zip(strs) { a, b -> "$a -> $b" }.collect { println(it) }
        // 1 -> one
        // 2 -> two
        // 3 -> three


    }


    // ================ Channel interaction coroutines ================================


    private fun channelExample() = runBlocking {
        val channel = Channel<Int>()

        launch {
            // this might be heavy CPU-consuming computation or async logic, we'll just send five squares
            for (i in 0..5) channel.send(i * i)
            // channel.close() // we're done sending
        }
        // here we print five received integers:
        repeat(5) { channel.receive() }
        println("Done!")

        // coroutineContext.cancelChildren() // game over, cancel them -- if needed
    }



    fun CoroutineScope.produceSquares(): ReceiveChannel<Int> = produce {
        for (x in 1..5) send(x * x)
    }
    
    
    private fun channelBuilder() = runBlocking {
        val squares = produceSquares()
        squares.consumeEach { println(it) }
        println("Done!")
    }
    



    // ==============================================

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