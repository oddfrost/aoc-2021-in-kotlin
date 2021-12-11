package calendar

class Day04 : AdventDay() {

    override fun part2solution(input: List<String>) = test2(input)

    override fun test2(input: List<String>): String {
        val numbers = ArrayDeque(numbersCalled(input))
        val boards = parseBoards(input)

        while (boards.size > 1){
            val calledNumber = numbers.removeFirst()
            val boardsToRemove = ArrayList<Board>()
            boards.forEach { board ->
                val sum = board.mark(calledNumber)
                if (sum >= 0) boardsToRemove.add(board)
            }
            while (boardsToRemove.isNotEmpty()){
                boards.remove(boardsToRemove.removeFirst())
            }
        }

        val lastBoard = boards.removeLast()

        while (!lastBoard.isFinished){
            val calledNumber = numbers.removeFirst()
            val sum = lastBoard.mark(calledNumber)
            if (sum >= 0) return sum.toString()
        }

        return lastBoard.sumUnMarked().toString()
    }

    override fun part1solution(input: List<String>) = test1(input)

    override fun test1(input: List<String>): String {
        val numbers = ArrayDeque(numbersCalled(input))
        val boards = parseBoards(input)
        val winningSums = HashSet<Int>()

        while (winningSums.isEmpty()) {
            val calledNumber = numbers.removeFirst()
            boards.forEach { board ->
                val sum = board.mark(calledNumber)
                if (sum >= 0) winningSums.add(sum)
            }
        }

        return winningSums.maxOrNull()?.toString() ?: "No one wins"
    }

    fun numbersCalled(input: List<String>): List<Int> {
        return input[0].trim().split(',').map { it.toInt() }
    }

    fun parseBoards(input: List<String>): ArrayList<Board> {
        val boards = ArrayList<Board>()
        var startPos = 2
        for (i in 2 until input.size) {
            if (input[i].isEmpty()) {
                boards.add(Board(input.subList(startPos, i)))
                startPos = i + 1
            } else if (i == input.size - 1) {
                boards.add(Board(input.subList(startPos, i + 1)))
            }
        }
        return boards
    }

    class Board(input: List<String>) {
        private val data: List<List<BingoNumber>> = List(input.size) { i ->
            input[i].split(' ').filter { it.isNotEmpty() }.map { BingoNumber(it.toInt()) }
        }

        var isFinished = false

        fun mark(number: Int): Int {
            for (y in data.indices) {
                for (x in data[y].indices) {
                    val field = data[y][x]
                    if (field.number == number) {
                        field.mark()
                        val sum = areYaWinningSon(x, y)
                        if (sum >= 0) isFinished = true
                        return sum
                    }
                }
            }
            return -1
        }

        fun areYaWinningSon(x: Int, y: Int): Int {
            val rowFinished = checkRow(y)
            val columnFinished = checkColumn(x)
            return if (rowFinished || columnFinished) {
                sumUnMarked() * data[y][x].number
            } else {
                -1
            }
        }

        fun sumUnMarked() = data.sumOf { line -> line.filter { it.isNotMarked }.sumOf { it.number } }

        private fun checkRow(y: Int): Boolean {
            var allMarked = true
            data[y].forEach { if (it.isNotMarked) allMarked = false }
            return allMarked
        }

        private fun checkColumn(x: Int): Boolean {
            var allMarked = true
            data.forEach { if (it[x].isNotMarked) allMarked = false }
            return allMarked
        }

        override fun toString(): String {
            return data.toString()
        }
    }

    class BingoNumber(val number: Int) {
        var isMarked = false
            private set

        val isNotMarked get() = !isMarked

        fun mark() {
            isMarked = true
        }

        override fun toString(): String {
            return if (isMarked) "*$number*" else number.toString()
        }
    }
}