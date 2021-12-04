package calendar

import java.io.File
import java.util.*

const val stubResult = "UNIMPLEMENTED"
abstract class AdventDay {
    open fun main() {
        val testData = readInput("-test")
        val testResult = test(testData)
        if (testResult != stubResult){
            println("Test 1: $testResult")
        }

        val input = readInput()
        print("First part: ")
        println(part1solution(input))

        val test2Result = test2(testData)
        if (test2Result != stubResult){
            println("Test 2: $test2Result")
        }

        print("Second part: ")
        println(part2solution(input))
    }

    open fun info(input: List<String>) {
        println("Processing ${input.size} lines")
    }

    open fun test(input: List<String>): String {
        return stubResult
    }

    open fun test2(input: List<String>): String {
        return stubResult
    }

    open fun part1solution(input: List<String>): String {
        return stubResult
    }

    open fun part2solution(input: List<String>): String {
        return stubResult
    }

    /**
     * Reads lines from the given input txt file.
     */
    fun readInput(postfix: String = ""): List<String> {
        val file = File("input", "${this.javaClass.simpleName.lowercase(Locale.getDefault()) + postfix}.txt")
        return if (file.exists()) {
            file.readLines()
        } else {
            println("File '${file.absolutePath}' not found")
            emptyList()
        }
    }

    val dayNumber: String = this.javaClass.simpleName.removePrefix("Day")
}