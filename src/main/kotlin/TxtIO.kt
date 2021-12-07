import java.io.File

object TxtIO {

    // Day 1
    fun getNumbersFromFile(filename: String): List<Int> = File(filename).readLines().map { it.toInt() }

    // Day 2
    fun readMovementCommandsFromFile(filename: String): List<MovementCommand> {
        return File(filename).readLines().map { parseLineIntoMovementCommand(it) }
    }

    // Day 3
    fun readBinaryNumbersFromFile(filename: String): List<String> = File(filename).readLines()

    // Day 4
    fun parseBingoFile(filename: String): Pair<List<Int>, List<BingoCard>> {
        val lines = File(filename).readLines()
        if (lines.size < 5) throw Error("Not enough data")
        val numbers = lines.first().toInts()

        val cards: List<BingoCard> = lines.drop(1).filter { it != "" }.toBingoCards()

        return numbers to cards
    }

    // Day 5
    fun getLinesFromFile(filename: String): List<Line> {
        val lines = File(filename).readLines()
        return lines.map { it.toLine() }
    }

    // Day 6
    fun getNumbersFromFileWithDDelimiter(filename: String): List<Int> = File(filename).readLines().map { it.split(",").map { it.toInt() } }.flatten()


}

private fun parseLineIntoMovementCommand(line: String): MovementCommand {
    val words = line.split(' ')
    if (words.size < 2) throw Error("Cannot parse command")
    val directionWord = words[0]
    val distanceWord = words[1]

    val direction = MovementDirection.values().firstOrNull { it.name.lowercase() == directionWord.lowercase() } ?: throw Error("Cannot parse direction $directionWord")
    val distance = distanceWord.toIntOrNull() ?: throw Error("Cannot parse distance $distanceWord")
    return MovementCommand(direction, distance)
}

fun List<String>.toBingoCards(): List<BingoCard> {
    val rows = this.map { it.toInts() }

    return (rows.indices step 5).map {
        BingoCard(
            listOf(
                rows[it + 0],
                rows[it + 1],
                rows[it + 2],
                rows[it + 3],
                rows[it + 4],
            )
        )
    }
}

private fun lineToNumbers(line: String): List<Int> {
    val temp = line.replace("  ", " ").trimStart().split(' ', ',')
    return temp.map { it.toInt() }
}

fun String.toInts(): List<Int> = lineToNumbers(this)

fun String.toLine(): Line {
    val ints = this.split(","," -> ").map { it.toInt() }
    if(ints.size != 4) throw Error("Cannot convert $this to line")
    return Line(Position(ints[0],ints[1]), Position(ints[2],ints[3]))
}