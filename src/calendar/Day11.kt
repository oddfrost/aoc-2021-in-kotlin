package calendar

class Day11 : AdventDay() {

    override fun part2solution(input: List<String>): String = test2(input)

    override fun test2(input: List<String>): String {
        return Simulation(input.map { line -> line.map { it.digitToInt() } }).simulatePart2().toString()
    }

    override fun part1solution(input: List<String>): String = test1(input)

    override fun test1(input: List<String>): String {
        return Simulation(input.map { line -> line.map { it.digitToInt() } }).simulate(100).toString()
    }

    class Simulation(val input: List<List<Int>>) {
        var flashes: Int = 0
        val map = HashMap<Pair<Int, Int>, Dumbo>()
        val yLen = input.size
        val xLen = input[0].size

        fun simulatePart2(): Int {
            for (y in input.indices) {
                for (x in input[y].indices) {
                    map[Pair(x, y)] = Dumbo(input[y][x], Pair(x, y), this) { flashes++ }
                }
            }

            var day = 0
            do {
                flashes = 0
                map.forEach { (pos, dumbo) -> dumbo.energyLevel++ }
                map.forEach { (pos, dumbo) -> dumbo.refresh()}
                day++
            } while (flashes != xLen * yLen)
            return day
        }

        fun simulate(days: Int): Int {
            for (y in input.indices) {
                for (x in input[y].indices) {
                    map[Pair(x, y)] = Dumbo(input[y][x], Pair(x, y), this) { flashes++ }
                }
            }
            for (i in 0 until days) {
                map.forEach { (pos, dumbo) -> dumbo.energyLevel++ }
                map.forEach { (pos, dumbo) -> dumbo.refresh() }
            }
            return flashes
        }

        class Dumbo(
            eLevel: Int,
            private val position: Pair<Int, Int>,
            private val sim: Simulation,
            private val onFlash: () -> Unit
        ) {
            var energyLevel = eLevel
                set(value) {
                    if (!flashed) {
                        field = value
                    }
                    if (field > 9) {
                        field = 0
                        flashed = true
                        onFlash.invoke()
                        this.position.sendEnergy()
                    }
                }

            private var flashed = false
                get() = field

            fun refresh() {
                flashed = false
            }

            fun Pair<Int, Int>.sendEnergy() {
                val result = ArrayList<Dumbo>()

                if (this.first > 0) result.add(sim.map[Pair(this.first - 1, this.second)]!!)
                if (this.first < sim.xLen - 1) result.add(sim.map[Pair(this.first + 1, this.second)]!!)
                if (this.second > 0) result.add(sim.map[Pair(this.first, this.second - 1)]!!)
                if (this.second < sim.yLen - 1) result.add(sim.map[Pair(this.first, this.second + 1)]!!)

                if (this.first > 0 && this.second > 0) result.add(sim.map[Pair(this.first - 1, this.second - 1)]!!)
                if (this.first > 0 && this.second < sim.yLen - 1) result.add(
                    sim.map[Pair(
                        this.first - 1,
                        this.second + 1
                    )]!!
                )
                if (this.first < sim.xLen - 1 && this.second > 0) result.add(
                    sim.map[Pair(
                        this.first + 1,
                        this.second - 1
                    )]!!
                )
                if (this.first < sim.xLen - 1 && this.second < sim.yLen - 1) result.add(
                    sim.map[Pair(
                        this.first + 1,
                        this.second + 1
                    )]!!
                )

                result.forEach { it.energyLevel++ }
            }
        }
    }

}