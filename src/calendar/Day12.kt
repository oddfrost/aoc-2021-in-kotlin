package calendar

class Day12 : AdventDay() {

    override fun part1solution(input: List<String>): String = test1(input)

    override fun test1(input: List<String>): String {
        parseInput(input)
        return findPaths()
    }

    val caves = HashSet<Cave>()

    fun parseInput(input: List<String>) {
        input.map { it.split("-") }.forEach { names ->
            val first = caves.firstOrNull { it.name ==  names[0]} ?: Cave(names[0])
            val second = caves.firstOrNull { it.name ==  names[1]} ?: Cave(names[1])
            caves.add(first)
            caves.add(second)
            first.addConnection(second)
        }
    }

    fun findPaths(): String {
        val start = caves.first { it.isStart() }
        val paths = showDaWey(ArrayList<Cave>() + start)
//        paths.map { it.toString() }.sorted().forEach { println(it) }
        return paths.size.toString()
    }

    private fun showDaWey(path: List<Cave>, paths: HashSet<List<Cave>> = HashSet()): HashSet<List<Cave>> {
        val currentPosition = path.last()

        currentPosition.connectedTo.forEach { cave ->
            if (cave.theEnd()) {
                paths.add(ArrayList(path) + cave)
            } else if (!cave.isBig && path.contains(cave)) {
                //
            } else showDaWey(ArrayList(path) + cave, paths)
        }

        return paths
    }

    class Cave(val name: String) {
        val isBig: Boolean
            get() = name[0].isUpperCase()

        val connectedTo = ArrayList<Cave>()

        fun addConnection(other: Cave) {
            if (this.isBig && other.isBig) {
                println("Big caves are connected! ${this.name} <-> ${other.name}")
            }
            this.connectedTo.add(other)
            other.connectedTo.add(this)
        }

        fun isStart(): Boolean {
            return this.name == "start"
        }

        fun theEnd(): Boolean {
            return this.name == "end"
        }

        override fun toString(): String {
            return name
        }
    }
}