package calendar

class Day08 : AdventDay() {

    override fun part2solution(input: List<String>): String = test2(input)

    override fun test2(input: List<String>): String {
        var counter: Long = 0
        input.forEach { line ->
            val (display, numbers) = line.separate()
            val number = numbers.map { display.signals[it] }.joinToString(separator = "").toLong()
            counter += number
        }
        return counter.toString()
    }

    override fun part1solution(input: List<String>): String = test1(input)

    override fun test1(input: List<String>): String {
        val uniqueNumbers = setOf(1, 4, 7, 8)
        var counter = 0

        input.forEach { line ->
            val (display, numbers) = line.separate()
            counter += numbers.count { display.signals[it] in uniqueNumbers }
        }
        return counter.toString()
    }

    fun String.separate(): Pair<Display, List<Set<Char>>> {
        val raw = this.split("|")
        return Pair(
            Display(raw[0].trim().split(" ").map { it.toSet() }.toMutableList()),
            raw[1].trim().split(" ").map { it.toSet() }
        )
    }

    class Display(digits: MutableList<Set<Char>>) {
        val signals = HashMap<Set<Char>, Int>()

        init {
            signals[digits.first { it.size == 2 }] = 1
            digits.remove(signals.keyOf(1))

            signals[digits.first { it.size == 4 }] = 4
            digits.remove(signals.keyOf(4))

            signals[digits.first { it.size == 3 }] = 7
            digits.remove(signals.keyOf(7))

            signals[digits.first { it.size == 7 }] = 8
            digits.remove(signals.keyOf(8))

            signals[digits.first {
                val part = signals.keyOf(8) - it
                part.size == 1 && signals.keyOf(1).contains(part.first())
            }] = 6
            digits.remove(signals.keyOf(6))


            signals[digits.first { signals.keyOf(6).containsAll(it) && it.size == 5 }] = 5
            digits.remove(signals.keyOf(5))

            signals[signals.keyOf(5) + signals.keyOf(1)] = 9
            digits.remove(signals.keyOf(9))

            signals[digits.first { !it.containsAll(signals.keyOf(1)) }] = 2
            digits.remove(signals.keyOf(2))

            signals[digits.first { digit -> (digit - signals.keyOf(1)).size == 3 }] = 3
            digits.remove(signals.keyOf(3))

            signals[digits.first()] = 0
        }


        fun <K, V> HashMap<K, V>.keyOf(value: V): K {
            return this.entries.first { it.value == value }.key
        }
    }
}