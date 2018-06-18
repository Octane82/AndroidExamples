package com.everlapp.androidexamples.kotlinlang

import android.os.Build
import java.io.File
import java.io.FileInputStream
import java.nio.file.Files
import java.nio.file.Paths


class KotlinSyntax : Base() {


    fun baseSyntax() : Unit {
        val a = 2
        val s1 = "a равно $a"
        // произвольное выражение в шаблоне:
        val s2 = "${s1.replace("равно", "было")}, но теперь $a"

        val obj: Any = Any()

        // When ( return when()... )
        when (obj) {
            1 -> print("Int")
            "Hello" -> print("String")
            !is String -> print("Not a string")
            else -> print("Unknown type")
        }

        // Intervals
        val x: Int = 77
        if(x in 1..80) print("Ok") else print("Not in interval")

        val arr = arrayOf(1, 2, 3)
        if (x !in 0..arr.lastIndex) print("Out interval")

        // Classes
        val customer = Customer("John", "john@mail.com")

        // val p: String by lazy { print("Compute") }

        val name = Resource.name
        val data = Resource.getData()

        val stream = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Files.newInputStream(Paths.get("/some/file.txt"))
        } else { TODO("VERSION.SDK_INT < O") }
        stream.buffered().reader().use { reader -> println(reader.readText()) }

        val l = 1L + 3  // неявное преобразование

        // Строки
        val str = "Hello everybody"
        for (ch in str) {           // ch - каждый символ строки
            print(ch)
        }


        // thread {  }
    }


    private fun loops() {
        val args: List<String> = listOf("one", "two", "three")
        for (arg in args) {             // or: i in args.indices -> args[i]
            print("Arg is: $arg")
        }

        //val arr = arrayOf(1, 2, 3, 4, 5)
        // Перебор значений в заданном интервале
        for (x in 1..5) {
            print(x)
        }

        for (x in 1..10 step 2) {
            print(x)
        }
        for (x in 9 downTo 0 step 3) {
            print(x)
        }

        // Iterate array
        val arr = arrayOf(1, 2, 3)
        for (i in arr.indices)
            print(arr[i])

        for ((index, value) in arr.withIndex()) {
            println("the element at $index is $value")
        }


        // Map iterate
        val map = mapOf(1 to "One", 2 to "Two", 3 to "Three")
        for ((k, v) in map) {
            print("Key: $k value: $v")
        }
        // or
        print(map[1])
        //map[1] = "Helo" - unmutable map

        val mutableMap = mutableMapOf(1 to "One", 2 to "Two")
        mutableMap[3] = "Three" // or put

        val istr = FileInputStream(File(""))
        istr.channel
    }


    private fun collections() {
        val sets = setOf("Apple", "Banana", "Kiwi")
        // check
        when {
            "Apple" in sets -> print("Juice")
        }

        val names: List<String> = listOf("John", "Sam", "Andrew", "Mike", "Anton")
        names
            .filter { it.startsWith("A") }
            .sortedBy { it }
            .map { it.toUpperCase() }
            .forEach { print("Name $it") }
    }



    data class Customer(val name: String, val email: String)


    /**
     * Singleton
     */
    object Resource {
        const val name = "Name"

        fun getData(): Int {
            return 77
        }
    }


    override fun getView() {
        super.getView()
        print("Inherited view")
    }
}