import TestUtils.testFilename
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day9Tests {

    @Test
    fun readMapFromFile() {
        val lines: List<String> = TxtIO.readLinesFromFile(testFilename("Day9"))
        val heightMap: List<List<Int>> = lines.map { line -> line.map { it.digitToInt() } }
        assertEquals(5, heightMap.size)
        assertEquals(10, heightMap[0].size)
    }

    @Test
    fun getLowPoints() {
        val map = getHeightMapFromFile(testFilename("Day9"))

        val lowPoints = getLowPointsFromMap(map)

        assertEquals(listOf(1,0,5,5), lowPoints.map { it.value })
    }

    @Test
    fun getRiskLevelFromLowPoints() {
        val lowPoints = listOf(1,0,5,5).map { PointWithValue(0,0, it) }
        val riskLevel = getRiskLevel(lowPoints)
        assertEquals(15, riskLevel)
    }

    @Test
    fun getRiskLevelFromLowPointsFromFile() {
        assertEquals(15, getRiskLevelFromFile(testFilename("Day9")))
    }

    @Test
    fun getBasic() {
        val expectedBasin = listOf(
            PointWithValue(0,0, 2),
            PointWithValue(1,0, 1),
            PointWithValue(0,1, 3),
        )

        val map = getHeightMapFromFile(testFilename("Day9"))

        val basin = getBasinFromLowPoint(map, PointWithValue(1,0,1))

        assertEquals(expectedBasin, basin)
    }

    @Test
    fun getCombinedBasinSize() {
        assertEquals(1134,getCombinedBasinSizeFromFile(testFilename("Day9")))
    }
}