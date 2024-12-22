package server.kotlinstart

import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.asserter

class SimpleTest {
    @Test
    fun test() {
        val immutableName = "immutable"
        var mutableName = "mutable"
        var any: Any = "123"
        any = 123
        any = 123L
        any = 123.0
        any = 1234.123f
        any = 'a'

        println("immutableName = $immutableName")
        println("mutableName = $mutableName")
        mutableName = "change"
        println("change mutableName = $mutableName")
        println("any = $any")
    }

    @Test
    fun test2() {
        val a = 1
        val b = 1

        println("a == b = ${a == b}")
        println("a === b = ${a === b}")

        val first = InnerClass(1, 2)
        val copy1 = first
        val second = InnerClass(1, 2)
        val copy2 = second

        asserter.assertEquals(null, first, copy1) // 값 비
        asserter.assertNotSame(null, first, copy2) // 참조 주소 비교
        asserter.assertNotEquals(null, first, second)
    }

    @Test
    fun test3() {
        val value = Random.nextInt(1, 11)

        val result = function(value)

        println("result = $result")
    }

    private fun function(value: Int): String {
        if (value > 5) {
            return "up"
        }
        return "down"
    }

    private fun function2(value: Int): String =
        if (value > 5) {
            "up"
        } else {
            "down"
        }

    private fun function3(value: Int): String {
        when {
            value > 5 -> {
                return "up"
            }

            else -> {
                return "down"
            }
        }
    }

    private fun function4(value: Int): String =
        when {
            value > 5 -> {
                "up"
            }

            else -> {
                "down"
            }
        }

    private fun function5(value: Int): String =
        when {
            value > 5 -> "up"
            else -> "down"
        }

    @Test
    fun test4() {
        val number = 10
        val result = if (number > 5) true else false

        val result2 =
            when {
                number < 5 -> true
                else -> false
            }
    }

    @Test
    fun test5() {
        val list = listOf(1, 2, 3, 4, 5)
        val evenNumbers =
            list
                .stream()
                .filter { it % 2 == 0 }
                .toList()

        for (i in evenNumbers) {
            println(i)
        }

        evenNumbers.forEach({ println(it) })
        evenNumbers.forEach { println(it) }

        for (i in 1..10) {
            println("i in 1..10")
            println(i)
        }

        for (i in 1 until 10) {
            println("i in 1 until 10")
            println(i)
        }

        for (i in 1 until 10 step 2) {
            println("i in 1 until 10 step 2")
            println(i)
        }
    }

    inner class InnerClass(
        private val a: Any,
        private val b: Any,
    )
}
