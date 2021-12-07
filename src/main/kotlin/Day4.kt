import TxtIO.parseBingoFile

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

class BingoCard(val rows: List<List<Int>>)