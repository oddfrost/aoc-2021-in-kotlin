package calendar

class Day01: AdventDay(){
    override fun part1solution(input: List<String>): String {
        val parsed = parseInput(input)
        var counter = 0
        for (i in 1 until parsed.size) {
            if (parsed[i] > parsed[i-1]) counter++
        }
        return counter.toString()
    }

    override fun part2solution(input: List<String>): String {
        val parsed = parseInput(input)
        var counter = 0
        var prevSum = sumNumbers(parsed, 0, 3)
        for (i in 1 until parsed.size - 2) {
            val currentSum = sumNumbers(parsed, i, 3)
            if (currentSum > prevSum) counter++
            prevSum = currentSum
        }
        return counter.toString()
    }

    fun parseInput(input: List<String>): List<Int> = input.map { it.toInt() }

    fun sumNumbers(input: List<Int>, pos: Int, number: Int): Int {
        var sum = 0
        for (i in pos until (pos+number)){
            sum += input[i]
        }
        return sum
    }
}
