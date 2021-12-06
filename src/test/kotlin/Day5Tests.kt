import org.junit.jupiter.api.Test
import kotlin.test.assertContentEquals

class Day5Tests {
    @Test
    fun makeSureDiagonalLineCounts() {
        val line = Line(Position(1,1), Position(3,3))
        val expectedPoints = listOf(Position(1,1), Position(2,2), Position(3,3))
        assertContentEquals(expectedPoints, line.toPoints())
    }

    @Test
    fun makeSureOtherDiagonalLineCounts() {
        val line = Line(Position(9,7), Position(7,9))
        val expectedPoints = listOf(Position(9,7), Position(8,8), Position(7,9))
        assertContentEquals(expectedPoints, line.toPoints())
    }
}
