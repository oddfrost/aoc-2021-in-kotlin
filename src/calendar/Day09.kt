package calendar

class Day09 : AdventDay() {

    override fun part2solution(input: List<String>): String = test2(input)

    override fun test2(input: List<String>): String {
        return Heightmap(input).getBasins().sortedByDescending { it.size }.take(3)
            .map { list -> list.size }.reduce { acc, i -> acc * i }
            .toString()
    }

    override fun part1solution(input: List<String>): String = test1(input)

    override fun test1(input: List<String>): String {
        return Heightmap(input).calcRiskLevel().toString()
    }

    class Heightmap(input: List<String>) {
        val xLen = input[0].length
        val yLen = input.size

        val map = HashMap<Pair<Int, Int>, Point>()

        init {
            for (y in input.indices) {
                val line = input[y].map { it.digitToInt() }
                for (x in line.indices) {
                    map[Pair(x, y)] = Point(Pair(x, y), line[x])
                }
            }
        }

        fun getBasins(): List<List<Point>> {
            val result = ArrayList<List<Point>>()
            val startingPoints = findLowPoints()
            startingPoints.forEach {
                result.add(collectBasinPoints(mutableSetOf(it)))
            }
            return result
        }

        private fun collectBasinPoints(toCollect: MutableSet<Point>, collected: ArrayList<Point> = arrayListOf()): ArrayList<Point> {
            while (toCollect.isNotEmpty()){
                val p = toCollect.first()
                toCollect.remove(p)
                p.check()

                if (p.value != 9 && !collected.contains(p)){
                    collected.add(p)
                    toCollect.addAll(p.coordinates.getSurroundingPoints().filter { !it.checked && !collected.contains(it) })
                }

            }

            return collected
        }

        fun calcRiskLevel(): Int {
            val lowPoints = findLowPoints()
            return lowPoints.size + lowPoints.sumOf(Point::value)
        }

        private fun findLowPoints(): List<Point> {
            val lowPoints = ArrayList<Point>()
            for (x in 0 until xLen) {
                for (y in 0 until yLen) {
                    val point = map[Pair(x, y)]
                    val surroundPoints = point!!.coordinates.getSurroundingPoints()
                    if (!surroundPoints.any { it.value <= point.value }) {
                        lowPoints.add(point)
                    }
                }
            }
            return lowPoints
        }

        private fun Pair<Int, Int>.getSurroundingPoints(): List<Point> {
            val result = ArrayList<Point>()
            if (this.first > 0) result.add(map[Pair(this.first - 1, this.second)]!!)
            if (this.first < xLen - 1) result.add(map[Pair(this.first + 1, this.second)]!!)
            if (this.second > 0) result.add(map[Pair(this.first, this.second - 1)]!!)
            if (this.second < yLen - 1) result.add(map[Pair(this.first, this.second + 1)]!!)
            return result
        }

        data class Point(val coordinates: Pair<Int, Int>, val value: Int, var checked: Boolean = false) {
            fun check(){checked = true}
        }
    }
}