package com.everlapp.androidexamples.coroutines

import androidx.fragment.app.Fragment
import kotlinx.coroutines.*
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

/**
 * https://github.com/Kotlin/kotlinx.coroutines/blob/master/coroutines-guide.md
 *
 * https://kotlinlang.org/docs/reference/coroutines-overview.html
 * https://codelabs.developers.google.com/codelabs/kotlin-coroutines/#4
 * https://proandroiddev.com/android-coroutine-recipes-33467a4302e9
 */
object CoroutinesTest {

    // dispatches execution into Android main thread
    val uiDispatcher: CoroutineDispatcher = Dispatchers.Main

    // represent a pool of shared threads as coroutine dispatcher
    val bgDispatcher: CoroutineDispatcher = Dispatchers.IO

    // ---------------------------------------

    private val globalJob = Job()

    private val supervisorJob = SupervisorJob()

    private val uiScope = CoroutineScope(Dispatchers.Main + globalJob)



    fun coroutineTestOne() {
        Timber.d("Coroutine test One")

        // First coroutine
        GlobalScope.launch {                                // launch new coroutine in background and continue
            delay(5 * 1000L)                       // non-blocking delay for 5 seconds (default time unit is ms)
            Timber.d("Hello from coroutine")
        }

        Timber.d("Main thread")                    // main thread continues here immediately
        runBlocking {                                       // Block Main thread    (Instead Thread.sleep(2000L))
            delay(2000L)
        }

        // Kotlin Thread
        // thread { }

        /*runBlocking {
            launch(Dispatchers.Main){

            }
        }*/
    }


    // ------ Coroutine scope ------------------


    // Global scope example
    fun loadData() = GlobalScope.launch {  }


    // Coroutine scope example
    fun loadDataTwo() = uiScope.launch {  }


    // Fragment implements CoroutineScope example
    class MainFragment : androidx.fragment.app.Fragment(), CoroutineScope {

        override val coroutineContext: CoroutineContext
            get() = Dispatchers.Main

        fun loadData() = launch {   }
    }




    // Cancel job when viewModel onCleared() or Activity onDestroy()
    // When the parent coroutine is cancelled, all its children are recursively cancelled, too.
    fun onClearedExample() {
        globalJob.cancel()
    }



    // --------- Launch + Async(Execute task)


    fun loadSomeData() = uiScope.launch {
        // view.showLoading() - UI Thread

        val task = async(bgDispatcher) {    // Background thread
            // your blocking call
        }

        val result = task.await()

        // view.showData(result)            // UI Thread
    }


    // ---- Launch + withContext(Execute task)
    fun loadSomeDataWithContext() = uiScope.launch {
        // view.showLoading() - UI Thread

        val result = withContext(bgDispatcher) {    // Background thread
            // your blocking call
        }

        // view.showData(result)            // UI Thread
    }


    // ---- Launch + withContext(Execute two tasks sequentially)
    fun loadSomeDataWithContextTwoTasksSequentially() = uiScope.launch {
        // view.showLoading() - UI Thread

        val result1 = withContext(bgDispatcher) {    // Background thread
            // your blocking call
        }

        val result2 = withContext(bgDispatcher) {    // Background thread
            // your blocking call
        }

        // val result = result1 + result2

        // view.showData(result)            // UI Thread
    }


    // ---- Launch + withContext(Execute two tasks parallel)
    fun loadSomeDataWithContextTwoTasksParallel() = uiScope.launch {
        // view.showLoading() - UI Thread

        val result1 = async(bgDispatcher) {    // Background thread
            // your blocking call
        }

        val result2 = async(bgDispatcher) {    // Background thread
            // your blocking call
        }

        // val result = result1.await() + result2.await()

        // view.showData(result)            // UI Thread
    }


    // Launch coroutine with timeout
    // suspend until task is finished or return null in 2 sec
    // val result = withTimeoutOrNull(2000) { task.await() }




    // Example
    fun main() = runBlocking<Unit> { // start main coroutine
        val job = GlobalScope.launch { // launch new coroutine in background and continue
            delay(1000L)
            println("World!")
        }
        println("Hello,")   // main coroutine continues here immediately

        job.join()          // (instead Delay) // wait until child coroutine completes
        //sampleEnd
        //delay(2000L)      // delaying for 2 seconds to keep JVM alive
    }


    // Test for suspending function
    /*class MyTest {
        @Test
        fun testMySuspendingFunction() = runBlocking<Unit> {
            // here we can use suspending functions using any assertion style that we like
        }
    }*/


}