package calendar

class Day03 : AdventDay() {

    override fun test(input: List<String>): String {
        return calculatePowerConsumption(input).toString()
    }

    override fun test2(input: List<String>): String {
        return calculateLifeSupportRating(input)
    }

    override fun part1solution(input: List<String>): String = test(input)
    override fun part2solution(input: List<String>): String = test2(input)

    fun calculatePowerConsumption(input: List<String>): Int {
        val lineLen = input[0].length
        val counter = IntArray(lineLen) { 0 }
        input.forEach { line ->
            for (i in 0 until lineLen) {
                counter[i] += Character.getNumericValue(line[i])
            }
        }
        var gammaRay = ""
        var epsilonRate = ""
        for (i in 0 until lineLen) {
            if (counter[i] >= input.size / 2) {
                gammaRay += '1'
                epsilonRate += '0'
            } else {
                gammaRay += '0'
                epsilonRate += '1'
            }
        }

        return gammaRay.toInt(2) * epsilonRate.toInt(2)
    }

    fun calculateLifeSupportRating(input: List<String>): String {
        val stopAt = input[0].length
        val ogr = filterNumbers(input, 0, stopAt, true)
        val c02sr = filterNumbers(input, 0, stopAt, false)
        return "${ogr * c02sr}"
    }

    private fun filterNumbers(input: List<String>, pos: Int, stopAt: Int, byCommon: Boolean): Int {
        if (input.size == 1 || pos == stopAt) {
            return input.first().toInt(2)
        }

        var sum = 0
        input.forEach { line ->
            sum += Character.getNumericValue(line[pos])
        }
        val common = if (sum >= input.size / 2.0) '1' else '0'
        val unCommon = if (sum < input.size / 2.0) '1' else '0'
        val data = if (byCommon) {
            input.filter { it[pos] == common }
        } else {
            input.filter { it[pos] == unCommon }
        }
        return filterNumbers(data, pos + 1, stopAt, byCommon)
    }

}