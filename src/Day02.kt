fun main() {
    val input = readInput("day02")
    println(part1(input))
    println(part2(input))
}

fun part1(input: List<String>): String {
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
    println(pos)
    return pos.distance.toString()
}

fun part2(input: List<String>): String {
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
    println(pos)
    return pos.distance.toString()
}

fun parseCommand(command: String): Pair<String, Int> {
    val words = command.split(' ')
    return Pair(words[0], words[1].toInt())
}

data class Position(var x: Int = 0, var z: Int = 0, var aim: Int = 0) {
    val distance: Int
        get() = x * z
}