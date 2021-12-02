package calendar

import java.io.File
import java.util.*

abstract class AdventDay {
    open fun main() {
        val testData = readInput("-test")
        info(testData)

        print("Test: ")
        println(test(testData))
        println()

        val input = readInput()
        info(input)

        print("First part: ")
        println(part1solution(input))

        print("Second part: ")
        println(part2solution(input))
    }

    open fun info(input: List<String>) {
        println("Processing ${input.size} lines")
    }

    open fun test(input: List<String>): String {
        return "Nothing happened"
    }

    open fun part1solution(input: List<String>): String {
        return "IMPLEMENT ME"
    }

    open fun part2solution(input: List<String>): String {
        return "IMPLEMENT ME"
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