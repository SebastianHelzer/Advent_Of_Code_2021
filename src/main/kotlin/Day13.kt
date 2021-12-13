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
        return (0 .. positions.maxOf { it.y }).map { y ->
            (0..positions.maxOf { it.x }).map { x ->
                if(positions.contains(Position(x, y))) '#' else ' '
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