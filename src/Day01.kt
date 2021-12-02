fun main() {
    fun part1(input: List<Int>): Int {
        var counter = 0
        for (i in 1 until input.size) {
            if (input[i] > input[i-1]) counter++
        }
        return counter
    }

    fun sumNumbers(input: List<Int>, pos: Int, number: Int): Int {
        var sum = 0
        for (i in pos until (pos+number)){
            sum += input[i]
        }
        return sum
    }

    fun part2(input: List<Int>): Int {
        var counter = 0
        var prevSum = sumNumbers(input, 0, 3)
        for (i in 1 until input.size - 2) {
            val currentSum = sumNumbers(input, i, 3)
            if (currentSum > prevSum) counter++
            prevSum = currentSum
        }
        return counter
    }

    val input = readInput("day01")
    val parsed = input.map { it.toInt() }
    println("Processing ${parsed.size} lines")

    print("First part: ")
    println(part1(parsed))
    print("Second part: ")
    println(part2(parsed))
}
