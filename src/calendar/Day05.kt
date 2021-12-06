package calendar

/**
 * Was octopus bored to death waiting to play bingo??? (from day 04)
 */
class Day05 : AdventDay() {
    override fun part2solution(input: List<String>): String = test2(input)

    override fun test2(input: List<String>): String {
        val points = input.map { it.split(" -> ") }
        val detector = VentsDetector<Pair<Int, Int>>()

        for ((first, second) in points) {
            first.toPoint().drawLine(second.toPoint()).forEach { point -> detector.add(point) }
        }
//        for(y in 0..9){
//            for(x in 0..9){
//                print(detector[Pair(x,y)])
//                print(" ")
//            }
//            println()
//        }
        return detector.countOverlaps().toString()
    }

    override fun part1solution(input: List<String>): String = test(input)

    override fun test(input: List<String>): String {
        val points = input.map { it.split(" -> ") }
        val detector = VentsDetector<Pair<Int, Int>>()

        for ((first, second) in points) {
            first.toPoint().drawStraightLine(second.toPoint()).forEach { point -> detector.add(point) }
        }

        return detector.countOverlaps().toString()
    }

    private fun String.toPoint(): Pair<Int, Int> {
        val numbers = this.split(',').map { it.toInt() }
        return Pair(numbers[0], numbers[1])
    }

    private fun Pair<Int, Int>.drawStraightLine(another: Pair<Int, Int>): ArrayList<Pair<Int, Int>> {
        val result = ArrayList<Pair<Int, Int>>()
        if (this.first == another.first) {
            val range = intArrayOf(this.second, another.second)
            range.sort()
            for (i in range[0]..range[1]) {
                result.add(Pair(this.first, i))
            }
        } else if (this.second == another.second) {
            val range = intArrayOf(this.first, another.first)
            range.sort()
            for (i in range[0]..range[1]) {
                result.add(Pair(i, this.second))
            }
        }
        return result
    }

    private fun Pair<Int, Int>.drawLine(another: Pair<Int, Int>): List<Pair<Int, Int>> {
        val result = this.drawStraightLine(another)

        val xs = if (this.first < another.first) this.first..another.first else this.first.downTo(another.first)
        val ys = if (this.second < another.second) this.second..another.second else this.second.downTo(another.second)

        if (xs.count() == ys.count()){
            result.addAll(xs.zip(ys))
        }

        return result
    }

    class VentsDetector<T>() {
        private val map = HashMap<T, Int>()
        fun add(i: T) {
            map[i] = map.getOrDefault(i, 0) + 1
        }

        operator fun get(i: T): Int {
            return map.getOrDefault(i, 0)
        }

        fun countOverlaps(min: Int = 2): Int{
            return map.count { (_, value) -> value >= min }
        }

        override fun toString(): String {
            return map.toString()
        }
    }
}
