import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day8Tests {

    /*
    0:      1:      2:      3:      4:
    aaaa    ....    aaaa    aaaa    ....
    b    c  .    c  .    c  .    c  b    c
    b    c  .    c  .    c  .    c  b    c
    ....    ....    dddd    dddd    dddd
    e    f  .    f  e    .  .    f  .    f
    e    f  .    f  e    .  .    f  .    f
    gggg    ....    gggg    gggg    ....

    5:      6:      7:      8:      9:
    aaaa    aaaa    aaaa    aaaa    aaaa
    b    .  b    .  .    c  b    c  b    c
    b    .  b    .  .    c  b    c  b    c
    dddd    dddd    ....    dddd    dddd
    .    f  e    f  .    f  e    f  .    f
    .    f  e    f  .    f  e    f  .    f
    gggg    gggg    ....    gggg    gggg
    */

    // # -   Code  - Size
    // 0 - abc efg - 6
    // 1 =   c  f  - 2
    // 2 = a cde g - 5
    // 3 = a cd fg - 5
    // 4 =  bcd f  - 4
    // 5 = ab d fg - 5
    // 6 = ab defg - 6
    // 7 = a c  f  - 3
    // 8 = abcdefg - 7
    // 9 = abcd fg - 6

    // Size - #
    // 2 - 1
    // 3 - 7
    // 4 - 4
    // 5 - 2,3,5
    // 6 - 0,6,9
    // 7 - 8
    @Test
    fun decipherSignal() {
        val ten = "be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb"
        val output = "fdgacbe cefdb cefbgd gcbe"

        val expectedNumbers = mapOf<Int, String>(
            1 to "be",
            7 to "edb",
            4 to "cgeb",
            8 to "cfbegad",
        )

        val number: Map<Int, String> = decipherSevenSegmentDisplay(ten)

        expectedNumbers.forEach {
            assertEquals(number[it.key]?.toSortedSet(), it.value.toSortedSet())
        }

        assertEquals(2, countDigits(number.filterKeys { listOf(1,4,7,8).contains(it) }, output))
        assertEquals(4, countDigits(number, output))
    }

    @Test
    fun decipherSignalFromFile() {
        val lines = TxtIO.readLinesFromFile(testFilename("Day8_1"))
        assertEquals(1, lines.size)

        val parts = lines.single().split(" | ")
        val input = parts[0]
        val output = parts[1]

        val numbers = decipherSevenSegmentDisplay(input)
        assertEquals(4, countDigits(numbers, output))
    }

    @Test
    fun decipherSignalsFromFile() {
        assertEquals(26, decipherSignalsFromFile(testFilename("Day8_2")))
    }

    @Test
    fun sumSevenSegsFromFIle() {
        assertEquals(5353, sumSevenSegsFromFIle(testFilename("Day8_1")))
        assertEquals(61229, sumSevenSegsFromFIle(testFilename("Day8_2")))
    }

    private fun testFilename(filename: String): String = "src/test/resources/$filename.txt"
}