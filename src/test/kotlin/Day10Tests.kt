import TestUtils.testFilename
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test

class Day10Tests {

    @Test
    fun determineIfChunkIsValid() {
        val validChunks = listOf(
            "()",
            "[]",
            "([])",
            "{()()()}",
            "<([{}])>",
            "[<>({}){}[([])<>]]",
            "(((((((((())))))))))",
        )

        getChunkStatuses(validChunks).forEach { assertEquals(Status.Valid, it) }
    }

    @Test
    fun determineIfChunkIsCorrupted() {
        val corruptedChunks = listOf(
            "]",
            "{()()()>",
            "(((()))}",
            "<([]){()}[{}])",
        )
        getChunkStatuses(corruptedChunks).forEach { assertNotEquals(Status.Valid, it) }
    }

    @Test
    fun determineIfChunkIsIncomplete() {
        val corruptedChunks = listOf(
          "{([(<{}[<>[]}>{[]{[(<()>",
          "[[<[([]))<([[{}[[()]]]",
          "[{[{({}]{}}([{[{{{}}([]",
          "[<(<(<(<{}))><([]([]()",
          "<{([([[(<>()){}]>(<<{{",
        )
        getChunkStatuses(corruptedChunks).forEach { assertNotEquals(Status.Valid, it) }
    }

    @Test
    fun readTestFile() {
        val lines = TxtIO.readLinesFromFile(testFilename("Day10"))
        val thing = getChunkStatuses(lines)

        assertEquals(5, thing.filterIsInstance<Status.Incomplete>().size)
        assertEquals(5, thing.filterIsInstance<Status.Corrupted>().size)

        val points = scoreIncompleteChars(thing.filterIsInstance<Status.Corrupted>().map { it.illegalChar })

        assertEquals(26397, points)
    }

    @Test
    fun scoreAutoComplete() {
        val autoComplete = listOf(
            "}}]])})]",
            ")}>]})",
            "}}>}>))))",
            "]]}}]}]}>",
            "])}>",
        )

        val expectedScores = listOf<Long>(
            288957,
            5566,
            1480781,
            995444,
            294,
        )

        val scores = autoComplete.map { scoreAutoChars(it) }

        assertEquals(expectedScores, scores)

        val middleScore = scores.sorted()[scores.size/2]
        assertEquals(middleScore, 288957)
    }

    @Test
    fun scoreAutoCompleteFromFile() {
        assertEquals(288957, scoreChunksAutoCompleteByFilename(testFilename("Day10")))
    }

    @Test
    fun convertCharsToString() {
        val string = arrayListOf<Char>('A', 'B', 'C', 'D', 'E')
        assertEquals("ABCDE",string.joinToString(separator = ""))
    }

}