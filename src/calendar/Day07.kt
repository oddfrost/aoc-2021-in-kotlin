package calendar

import kotlin.math.abs
import kotlin.math.min
import kotlin.math.roundToInt

class Day07 : AdventDay() {
    override fun part2solution(input: List<String>): String {
        return test2(input)
    }

    override fun test2(input: List<String>): String {
        val data = input[0].split(",").map { it.toInt() }
        val avg = data.average().roundToInt()
        val avgFl = data.average().toInt()
        val fuelFl = data.sumToAvg(avgFl)
        val fuel = if (avg == avgFl) fuelFl else min(fuelFl, data.sumToAvg(avg))
        return fuel.toString()
    }

    override fun part1solution(input: List<String>): String {
        return test1(input)
    }

    override fun test1(input: List<String>): String {
        val sorted = input[0].split(",").map { it.toInt() }.sorted()
        val median = sorted[sorted.size / 2]
        val fuel = sorted.sumOf { abs(it - median) }
        return fuel.toString()
    }

    fun List<Int>.sumToAvg(avg: Int): Int {
        return this.sumOf { val diff = abs(it - avg); diff * (1 + diff) / 2 }
    }
}