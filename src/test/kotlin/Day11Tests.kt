import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day11Tests {

    val step0 = listOf(
        "11111",
        "19991",
        "19191",
        "19991",
        "11111",
    )

    val step1 = listOf(
        "34543",
        "40004",
        "50005",
        "40004",
        "34543",
    )

    val step2 = listOf(
        "45654",
        "51115",
        "61116",
        "51115",
        "45654",
    )

    @Test
    fun smallSteps() {
        assertEquals(step1, Day11.nextStep(step0).first)
        assertEquals(step2, Day11.nextStep(step1).first)
    }

    @Test
    fun convertStepToInts() {
        assertEquals(listOf(
            listOf(1,1,1,1,1),
            listOf(1,9,9,9,1),
            listOf(1,9,1,9,1),
            listOf(1,9,9,9,1),
            listOf(1,1,1,1,1),
        ), Day11.convertLinesToInts(step0))
    }


    @Test
    fun doPart1() {
        Day11.part1(TestUtils.testFilename("Day11_1"))
    }

    @Test
    fun doPart2() {
        Day11.part2(TestUtils.testFilename("Day11_1"))
    }



}

