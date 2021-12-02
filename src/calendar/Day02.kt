package calendar

class Day02 : AdventDay() {
    override fun part1solution(input: List<String>): String {
        val pos = Position()
        input.forEach {
            val command = parseCommand(it)
            when (command.first) {
                "forward" -> pos.x += command.second
                "up" -> pos.z -= command.second
                "down" -> pos.z += command.second
                else -> println("Unknown command: $command")
            }
        }
        return pos.distance.toString()
    }

    override fun part2solution(input: List<String>): String {
        val pos = Position()
        input.forEach {
            val command = parseCommand(it)
            when (command.first) {
                "forward" -> {
                    pos.x += command.second; pos.z += pos.aim * command.second
                }
                "up" -> pos.aim -= command.second
                "down" -> pos.aim += command.second
                else -> println("Unknown command: $command")
            }
        }
        return pos.distance.toString()
    }

    private fun parseCommand(command: String): Pair<String, Int> {
        val words = command.split(' ')
        return Pair(words[0], words[1].toInt())
    }
}

data class Position(var x: Int = 0, var z: Int = 0, var aim: Int = 0) {
    val distance: Int
        get() = x * z
}