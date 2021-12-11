fun scoreChunksByFilename(filename: String): Int = scoreIncompleteChars(getChunkStatuses(TxtIO.readLinesFromFile(filename)).filterIsInstance<Status.Corrupted>().map { it.illegalChar })
fun scoreChunksAutoCompleteByFilename(filename: String): Long = getMiddleScore(
    getChunkStatuses(TxtIO.readLinesFromFile(filename)).filterIsInstance<Status.Incomplete>().map { scoreAutoChars(it.neededChars) }
)

fun scoreIncompleteChars(chars: List<Char>): Int = chars.sumOf { scoreIncompleteChar(it) }

fun scoreIncompleteChar(char: Char): Int = when (char) {
    ')' -> 3
    ']' -> 57
    '}' -> 1197
    '>' -> 25137
    else -> 0
}

fun scoreAutoChars(chars: String): Long {
    var currentScore: Long = 0
    chars.forEach { currentScore = currentScore * 5 + scoreAutoChar(it) }
    return currentScore
}

fun scoreAutoChar(char: Char): Long  = when (char) {
    ')' -> 1
    ']' -> 2
    '}' -> 3
    '>' -> 4
    else -> 0
}

sealed class Status {
    object Valid: Status()
    data class Incomplete(val neededChars: String): Status()
    data class Corrupted(val illegalChar: Char): Status()
}

fun getChunkStatuses(validChunks: List<String>): List<Status> = validChunks.map { getChunkStatus(it) }

fun getChunkStatus(chunk: String): Status {

    val charToCloseList = arrayListOf<Char>()

    chunk.forEach {
        when (it) {
            '<' -> charToCloseList.add('>')
            '(' -> charToCloseList.add(')')
            '[' -> charToCloseList.add(']')
            '{' -> charToCloseList.add('}')
            else -> {
                if(charToCloseList.lastOrNull() == it) {
                    charToCloseList.removeLast()
                } else {
                    return Status.Corrupted(it)
                }
            }
        }
    }

    return if (charToCloseList.isEmpty()) Status.Valid
    else Status.Incomplete(charToCloseList.asReversed().joinToString(""))

}

fun getMiddleScore(scores: List<Long>): Long {
    return scores.sorted()[scores.size/2]
}