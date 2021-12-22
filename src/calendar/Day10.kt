package calendar

class Day10 : AdventDay() {

    override fun part2solution(input: List<String>): String = test2(input)

    override fun test2(input: List<String>): String {
        val result = input.map { ArrayDeque(it.toList()) }
            .map { calculateChunkScore(it, false) }
            .filter { it > 0 }
            .sorted()

        return result[result.size / 2].toString()
    }

    override fun part1solution(input: List<String>): String = test1(input)

    override fun test1(input: List<String>): String {
        return input.map { ArrayDeque(it.toList()) }.sumOf { calculateChunkScore(it) }.toString()
    }

    private val boundaries = listOf(
        Boundaries('(', ')', 3, 1),
        Boundaries('[', ']', 57, 2),
        Boundaries('{', '}', 1197, 3),
        Boundaries('<', '>', 25137, 4)
    )

    private fun Char.isRB(): Boolean {
        return boundaries.any { it.rb == this }
    }

    private fun calculateChunkScore(chunk: ArrayDeque<Char>, calcErrors: Boolean = true): Long {
        val stack = ArrayDeque<Boundaries>()
        while (chunk.isNotEmpty()) {
            val symbol = chunk.removeFirst()
            if (symbol.isRB()) {
                if (stack.isEmpty() || stack.last().rb != symbol) {
                    return if (calcErrors) boundaries.first { it.rb == symbol }.errorPoints.toLong() else 0
                } else if (stack.last().rb == symbol) {
                    stack.removeLast()
                }
            } else {
                stack.addLast(boundaries.first { it.lb == symbol })
            }
        }
        return if (calcErrors) 0 else {
            stack.reversed().map { it.completePoints }.map { it.toLong() }.reduce { acc, i -> acc * 5 + i }
        }
    }

    data class Boundaries(var lb: Char, val rb: Char, val errorPoints: Int, val completePoints: Int)
}