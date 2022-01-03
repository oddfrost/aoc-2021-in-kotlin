package calendar

class Day12 : AdventDay() {

    override fun part2solution(input: List<String>): String = test2(input)

    override fun test2(input: List<String>): String {
        caves = HashSet()
        parseInput(input)
        return part2()
    }

    override fun part1solution(input: List<String>): String = test1(input)

    override fun test1(input: List<String>): String {
        caves = HashSet()
        parseInput(input)
        return part1()
    }

    var caves = HashSet<Cave>()

    fun parseInput(input: List<String>) {
        input.map { it.split("-") }.forEach { names ->
            val first = caves.firstOrNull { it.name == names[0] } ?: Cave(names[0])
            val second = caves.firstOrNull { it.name == names[1] } ?: Cave(names[1])
            caves.add(first)
            caves.add(second)
            first.addConnection(second)
        }
    }

    fun part2(): String {
        val start = caves.first { it.isStart() }
        val paths = showDaWey2(ArrayList<Cave>() + start)
//        paths.map { it.toString() }.sorted().forEach { println(it) }
        return paths.size.toString()
    }

    fun part1(): String {
        val start = caves.first { it.isStart() }
        val paths = showDaWey1(ArrayList<Cave>() + start)
//        paths.map { it.toString() }.sorted().forEach { println(it) }
        return paths.size.toString()
    }

    private fun showDaWey1(path: List<Cave>, paths: HashSet<List<Cave>> = HashSet()): HashSet<List<Cave>> {
        val currentPosition = path.last()

        currentPosition.connectedTo.forEach { cave ->
            if (cave.theEnd()) {
                paths.add(ArrayList(path) + cave)
            } else if (!cave.isBig && path.contains(cave)) {
                //
            } else showDaWey1(ArrayList(path) + cave, paths)
        }

        return paths
    }

    private fun showDaWey2(
        path: List<Cave>,
        paths: HashSet<List<Cave>> = HashSet(),
        visitCounters: HashMap<String, Int> = HashMap()
    ): HashSet<List<Cave>> {
        val currentPosition = path.last()
        if (!currentPosition.isBig) {
            visitCounters[currentPosition.name] = visitCounters.getOrDefault(currentPosition.name, 0) + 1
        }

        currentPosition.connectedTo.forEach { nextCave ->
            if (nextCave.theEnd()) {
                paths.add(ArrayList(path) + nextCave)
            } else if (nextCave.isStart()) {
                //
            } else if (!nextCave.isBig && path.contains(nextCave) && visitCounters.containsValue(2)) {
                //
            } else {
                showDaWey2(ArrayList(path) + nextCave, paths, visitCounters.deepCopy())
            }
        }

        return paths
    }

    fun HashMap<String, Int>.deepCopy(): HashMap<String, Int> {
        val newMap: HashMap<String, Int> = HashMap(this.size)
        this.forEach { (k, v) -> newMap[k] = v }
        return newMap
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