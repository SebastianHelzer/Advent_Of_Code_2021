fun main(args: Array<String>) {
    println("Hello World!")

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")

    val day = 11
    val test = false
    val testFileAppendage = if (test) "_test" else ""

    when(day) {
        1 -> {
            println("getDepthIncreases: ${getDepthIncreases(localFilename("depth$testFileAppendage.txt"))}")
            println("getDepthIncreasesWithRollingSum: ${getDepthIncreasesWithRollingSum(localFilename("depth$testFileAppendage.txt"))}")
        }
        2 -> {
            getPositionAfterMovement(localFilename("distance$testFileAppendage.txt")).let { (x, y) -> println("getMovement: $x $y ${x*y}") }
            getPositionAfterMovementWithAim(localFilename("distance$testFileAppendage.txt")).let { (x, y) -> println("getMovement: $x $y ${x*y}") }
        }
        3 -> {
            println("Power Consumed: ${processPowerConsumption(localFilename("diagnostics$testFileAppendage.txt"))}")
            println("Life Support Rating: ${getLifeSupportRating(localFilename("diagnostics$testFileAppendage.txt"))}")
        }
        4 -> {
            println("Winning bingo score: ${playBingo(localFilename("bingo$testFileAppendage.txt"))}")
            println("Losing bingo score: ${loseAtBingo(localFilename("bingo$testFileAppendage.txt"))}")
        }
        5 -> {
            println("How many dangerous orthogonal vents: ${howManyDangerousVents(localFilename("vents$testFileAppendage.txt"))}")
            println("How many dangerous vents: ${howManyDangerousVents(localFilename("vents$testFileAppendage.txt"), true)}")
        }
        6 -> {
            println("Lantern Fish Simulation: ${simulateLanternFish(localFilename("fish$testFileAppendage.txt"))}")
            println("Lantern Fish Simulation for 256: ${simulateLanternFish(localFilename("fish$testFileAppendage.txt"), 256)}")
        }
        7 -> {
            println("Crab submarine fuel consumption ${findLowestCrabSubmarineFuelConsumption(localFilename("crab$testFileAppendage.txt"))}")
            println("Crab submarine true fuel consumption ${findLowestCrabSubmarineFuelConsumption(localFilename("crab$testFileAppendage.txt"), true)}")
        }
        8 -> {
            println("Seven segment output values: ${decipherSignalsFromFile(localFilename("seven_seg.txt"))}")
            println("Seven segment output values: ${sumSevenSegsFromFIle(localFilename("seven_seg.txt"))}")
        }
        9 -> {
            println("Risk level ${getRiskLevelFromFile(localFilename("smoke.txt"))}")
            println("Combined basin size ${getCombinedBasinSizeFromFile(localFilename("smoke.txt"))}")
        }
        10 -> {
            println("Chunk score ${scoreChunksByFilename(localFilename("chunk.txt"))}")
            println("Chunk score ${scoreChunksAutoCompleteByFilename(localFilename("chunk.txt"))}")
        }
        11 -> {
            Day11.part1(localFilename("octopuses.txt"))
            Day11.part2(localFilename("octopuses.txt"))
        }
    }
}

fun localFilename(filename: String): String = "src/main/resources/$filename"