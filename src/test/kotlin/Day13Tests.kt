import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day13Tests {

    val filename1 = TestUtils.testFilename("Day13_1")
    val filename2 = TestUtils.testFilename("Day13_2")

    @Test
    fun readFileAsPositionsAndFolds() {
        val (positions, folds) = Day13.readFile(filename1)

        assertEquals(listOf(
            Position(6,10),
            Position(0,14),
            Position(9,10),
            Position(0,3),
            Position(10,4),
            Position(4,11),
            Position(6,0),
            Position(6,12),
            Position(4,1),
            Position(0,13),
            Position(10,12),
            Position(3,4),
            Position(3,0),
            Position(8,4),
            Position(1,10),
            Position(2,14),
            Position(8,10),
            Position(9,0),
        ), positions)

        assertEquals(listOf(
            Position(0,7),
            Position(5,0)
        ), folds)
    }

    @Test
    fun countUniquePositions() {
        val (positions, _) = Day13.readFile(filename1)
        assertEquals(18, Day13.countUniquePositions(positions))
    }

    @Test
    fun foldPositions() {
        val (positions, folds) = Day13.readFile(filename1)
        val foldedPositions = Day13.fold(positions, folds.first())

        assertEquals(listOf(
            Position(0,0),
            Position(2,0),
            Position(3,0),
            Position(6,0),
            Position(9,0),
            Position(0,1),
            Position(4,1),
            Position(6,2),
            Position(10,2),
            Position(0,3),
            Position(4,3),
            Position(1,4),
            Position(3,4),
            Position(6,4),
            Position(8,4),
            Position(9,4),
            Position(10,4),
        ), foldedPositions.sortedBy { it.y * 1000 + it.x })

        assertEquals(17, Day13.countUniquePositions(foldedPositions))
    }

    @Test
    fun simpleFold() {
        assertEquals(Position(0,0), Day13.fold(listOf(Position(4,0)), Position(2,0)).first())
        assertEquals(Position(0,0), Day13.fold(listOf(Position(0,0)), Position(2,0)).first())
        assertEquals(Position(4,0), Day13.fold(listOf(Position(4,0)), Position(0,2)).first())
        assertEquals(Position(4,2), Day13.fold(listOf(Position(4,6)), Position(0,4)).first())
        assertEquals(Position(4,3), Day13.fold(listOf(Position(4,3)), Position(0,4)).first())
        assertEquals(Position(4,3), Day13.fold(listOf(Position(4,5)), Position(0,4)).first())
    }

    @Test
    fun part1() {
        assertEquals(17,Day13.part1(filename1))
        assertEquals(807,Day13.part1(filename2))
    }
    @Test
    fun part2() {
        assertEquals(16,Day13.part2(filename1))
        assertEquals(98,Day13.part2(filename2))
    }
}

object Day13 {

    fun part1(filename: String): Int {
        val (positions, folds) = readFile(filename)
        return countUniquePositions(fold(positions, folds.first()))
    }

    fun part2(filename: String): Int {
        val (positions, folds) = readFile(filename)
        val foldedPositions = folds.fold(positions) { a, p -> fold(a, p) }
        readout(foldedPositions).forEach { println(it) }
        return countUniquePositions(foldedPositions)
    }

    private fun readout(positions: List<Position>): List<String> {
        val maxX = positions.maxOf { it.x }
        val maxY = positions.maxOf { it.y }

        return (0 .. maxY).map { y ->
            (0..maxX).map { x ->
                if(positions.contains(Position(x,y))) '#' else ' '
            }.joinToString("")
        }
    }

    fun readFile(filename: String): Pair<List<Position>, List<Position>> {
        val lines = TxtIO.readLinesFromFile(filename)

        val (positionLines, foldLines) = lines.filter { it.isNotBlank() }.partition { it.contains(',') }

        return positionLines.map { it.toInts().let { Position(it[0], it[1]) } } to foldLines.map {
            if (it.contains('x')) {
                Position(it.substringAfter('=').toInt(), 0)
            } else if (it.contains('y')) {
                Position(0, it.substringAfter('=').toInt())
            } else throw Error("Unable to read file")
        }
    }

    fun countUniquePositions(positions: List<Position>): Int = positions.toSet().size
    fun fold(positions: List<Position>, fold: Position): List<Position> {
        return if (fold.x != 0) {
            foldAlongX(positions, fold.x)
        } else if (fold.y != 0) {
            foldAlongY(positions, fold.y)
        } else throw Error("Unable to fold along: $fold")
    }

    private fun foldAlongX(positions: List<Position>, x: Int): List<Position> {
        val (steady, toFold) = positions.filter { it.x != x }.partition { it.x < x }
        return steady.union(toFold.map { it.copy(x = 2 * x - it.x) }).toList()
    }

    private fun foldAlongY(positions: List<Position>, y: Int): List<Position> {
        val (steady, toFold) = positions.filter { it.y != y }.partition { it.y < y }
        return steady.union(toFold.map { it.copy(y = 2 * y - it.y) }).toList()
    }

}