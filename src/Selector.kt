import calendar.AdventDay

fun main(args: Array<String>) {
    args.forEach { dayNumber ->
        val day = Class.forName("calendar.Day$dayNumber")?.getDeclaredConstructor()?.newInstance()
        if (day is AdventDay) {
            println("********* Day ${day.dayNumber} *********")
            day.main()
            println("********* Day ${day.dayNumber} *********")
            println()
        }
    }
}
