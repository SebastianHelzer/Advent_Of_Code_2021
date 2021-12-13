import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day12Tests {

    val filename1 = TestUtils.testFilename("Day12_1")
    val filename2 = TestUtils.testFilename("Day12_2")
    val filename3 = TestUtils.testFilename("Day12_3")
    val filename4 = TestUtils.testFilename("Day12_4")

    @Test
    fun getConnectionsFromFile() {
        val connections = Day12.getConnectionsFromFile(filename1)
        val expectedConnections = listOf(
            "start" to "A",
            "start" to "b",
            "A" to "c",
            "A" to "b",
            "b" to "d",
            "A" to "end",
            "b" to "end",
        )
        assertEquals(expectedConnections, connections)
    }

    @Test
    fun constructCaveSystemWithConnections() {
        val connections = Day12.getConnectionsFromFile(filename1)

        val paths: List<String> =  Day12.getPathsFromConnections(connections, Day12.FilterType.SmallCaveOnce)
        val expectedPaths: List<String> = listOf(
            "start,A,b,A,c,A,end",
            "start,A,b,A,end",
            "start,A,b,end",
            "start,A,c,A,b,A,end",
            "start,A,c,A,b,end",
            "start,A,c,A,end",
            "start,A,end",
            "start,b,A,c,A,end",
            "start,b,A,end",
            "start,b,end",
        )

        assertEquals(expectedPaths.sorted(), paths.sorted())
    }

    @Test
    fun getDestinationsFromNode() {
        val connections = Day12.getConnectionsFromFile(filename1)

        assertEquals(listOf("A", "b"), Day12.getDestinationsFromNode(connections, "start"))
        assertEquals(listOf("A", "start", "d", "end").sorted(), Day12.getDestinationsFromNode(connections, "b").sorted())
        assertEquals(listOf("b"), Day12.getDestinationsFromNode(connections, "d"))
    }

    @Test
    fun getSmallCaveOncePathCount() {
        assertEquals(10, Day12.part1(filename1))
    }

    @Test
    fun pathCountForMediumCave() {
        assertEquals(19, Day12.part1(filename2))
    }

    @Test
    fun pathCountForLargeCave() {
        assertEquals(226, Day12.part1(filename3))
    }

    @Test
    fun pathCountForActualCave() {
        assertEquals(4411, Day12.part1(filename4))
    }

    @Test
    fun part2() {
        assertEquals(36, Day12.part2(filename1))
        assertEquals(103, Day12.part2(filename2))
        assertEquals(3509, Day12.part2(filename3))
        assertEquals(136767, Day12.part2(filename4))
    }
}

