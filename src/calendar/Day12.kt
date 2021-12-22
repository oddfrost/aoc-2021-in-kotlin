package calendar

class Day12 : AdventDay() {
    override fun test1(input: List<String>): String {
        return super.test1(input)
    }

    val caves = HashSet<Cave>()

    fun parseInput(input: List<String>){
        input.map { it.split("-") }.forEach {
            val first = Cave(it[0])
            val second = Cave(it[1])
            caves.add(first)
            caves.add(second)
            first.addConnection(second)
        }
    }

    fun findPaths(){

    }

    class Cave(val name: String) {
        val isBig: Boolean
            get() = name[0].isUpperCase()

        val connectedTo = ArrayList<Cave>()

        fun addConnection(other: Cave){
            this.connectedTo.add(other)
            other.connectedTo.add(this)
        }
    }
}