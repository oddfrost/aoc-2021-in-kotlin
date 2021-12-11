package calendar

//I can understand that there is some kind of progression, but I am bad at it
class Day06 : AdventDay() {

    override fun part2solution(input: List<String>): String {
        return test2(input)
    }

    override fun test2(input: List<String>): String {
        return fishySimulation(input[0], 256).toString()
    }

    class FishNet(firstFish: List<Int>, size: Int = 9) {
        var net = LongArray(size) { 0 }

        init {
            firstFish.forEach { net[it] = net[it] + 1 }
        }

        fun simulate(days: Int): Long {
            for (day in 0 until days) {
                val darkNet = LongArray(net.size) { 0 }
                for (i in 0..8) {
                    when (i) {
                        0 -> {
                            darkNet[8] += net[0]
                            darkNet[6] += net[0]
                        }
                        else -> darkNet[i - 1] += net[i]
                    }
                }
                net = darkNet
            }
            return net.sum()
        }
    }

    override fun part1solution(input: List<String>): String = test1(input)

    override fun test1(input: List<String>): String {
        return fishySimulation(input[0]).toString()
    }

    private fun fishySimulation(input: String, days: Int = 80): Long {
        val data = input.split(',').map { it.toInt() }
        val fishNet = FishNet(data)
        return fishNet.simulate(days)
    }
}