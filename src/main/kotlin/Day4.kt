import java.io.File

fun playBingo(filename: String): Int {
    val (hints, cards) = parseBingoFile(filename)
    val (card, hintIndex) = findWinningCard(hints, cards)
    return scoreCard(card, hints, hintIndex)
}

fun loseAtBingo(filename: String): Int {
    val (hints, cards) = parseBingoFile(filename)
    val (card, hintIndex) = findLosingCard(hints, cards)
    return scoreCard(card, hints, hintIndex)
}

private fun scoreCard(card: BingoCard, hints: List<Int>, hintIndex: Int): Int {
    val hintsGiven = hints.take(hintIndex + 1).toSet()
    val hint = hints[hintIndex]
    return card.rows.flatten().toSet().minus(hintsGiven).sum() * hint
}

private fun parseBingoFile(filename: String): Pair<List<Int>, List<BingoCard>> {
    val lines = File(filename).readLines()
    if (lines.size < 5) throw Error("Not enough data")
    val numbers = lines.first().toInts()

    val cards: List<BingoCard> = lines.drop(1).filter { it != "" }.toBingoCards()

    return numbers to cards
}

private fun findWinningCard(hints: List<Int>, cards: List<BingoCard>): Pair<BingoCard, Int> {
    (4 until hints.size).forEach { index ->
        cards.firstOrNull { checkIsWinningCard(hints.take(index + 1), it) }?.let { return it to index}
    }

    throw Error("Unable to find a winning card")
}

private fun checkIsWinningCard(hints: List<Int>, card: BingoCard): Boolean {
    // Check Rows
    card.rows.forEach {
        if(hints.toSet().intersect(it.toSet()).size == 5) return true
    }
    // Check Columns
    (0 until 5).forEach { index ->
        if(hints.toSet().intersect(card.rows.map { it[index] }.toSet()).size == 5) return true
    }

    return false
}

private fun findLosingCard(hints: List<Int>, cards: List<BingoCard>): Pair<BingoCard, Int> {
    val cardsRemaining = cards.toMutableList()
    (4 until hints.size).mapNotNull { index ->
        cardsRemaining.filter { checkIsWinningCard(hints.take(index + 1), it) }.map {
            cardsRemaining.remove(it)
            it to index
        }
    }.flatten().let { return it.lastOrNull() ?: throw Error("Unable to find a losing card") }
}

private fun List<String>.toBingoCards(): List<BingoCard> {
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

private fun String.toInts(): List<Int> = lineToNumbers(this)

class BingoCard(val rows: List<List<Int>>)