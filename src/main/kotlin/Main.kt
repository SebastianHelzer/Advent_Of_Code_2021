fun main(args: Array<String>) {
    println("Hello World!")

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")

    val day = 2
    val test = false
    val testFileAppendage = if (test) "_test" else ""

    when(day) {
        1 -> {
            println("getDepthIncreases: ${getDepthIncreases("src/main/resources/depth$testFileAppendage.txt")}")
            println("getDepthIncreasesWithRollingSum: ${getDepthIncreasesWithRollingSum("src/main/resources/depth$testFileAppendage.txt")}")
        }
        2 -> {
            getPositionAfterMovement("src/main/resources/distance$testFileAppendage.txt" ).let { (x, y) -> println("getMovement: $x $y ${x*y}") }
            getPositionAfterMovementWithAim("src/main/resources/distance$testFileAppendage.txt" ).let { (x, y) -> println("getMovement: $x $y ${x*y}") }
        }
    }
}
