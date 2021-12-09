import TxtIO.readLinesFromFile

fun getRiskLevelFromFile(filename: String): Int {
    return getRiskLevel(getLowPointsFromMap(getHeightMapFromFile(filename)))
}

fun getRiskLevel(lowPoints: List<PointWithValue>): Int = lowPoints.sumOf { it.value + 1 }

fun getLowPointsFromMap(map: List<List<Int>>): List<PointWithValue> {
    val lowPoints = arrayListOf<PointWithValue>()
    map.forEachIndexed { r, list ->
        list.forEachIndexed { c, i ->
            if(
                map.getOrDefault(r+1, c, Int.MAX_VALUE) > i &&
                map.getOrDefault(r-1, c, Int.MAX_VALUE) > i &&
                map.getOrDefault(r, c+1, Int.MAX_VALUE) > i &&
                map.getOrDefault(r, c-1, Int.MAX_VALUE) > i
            ) lowPoints.add(PointWithValue(c, r, i))
        }
    }
    return lowPoints
}

fun List<List<Int>>.getOrDefault(r: Int, c: Int, default: Int): Int = getOrNull(r)?.getOrNull(c) ?: default

fun getHeightMapFromFile(filename: String): List<List<Int>> = readLinesFromFile(filename)
    .map { line -> line.map { it.digitToInt() } }


fun getCombinedBasinSizeFromFile(filename: String): Int {
    val heightMap = getHeightMapFromFile(filename)
    val lowPoints: List<PointWithValue> = getLowPointsFromMap(heightMap)
    val basins: List<Basin> = lowPoints.map { getBasinFromLowPoint(heightMap, it) }
    return basins.map { it.size }.sortedDescending().take(3).fold(1) { a, v -> a * v }
}

fun getBasinFromLowPoint(map: List<List<Int>>, p: PointWithValue): Basin {
    val points = map.mapIndexed { r, row -> row.mapIndexed { c, value -> PointWithValue(c, r, value) } }.flatten()
    return getBasinFromLowPointWith(points, p)
}

private fun getBasinFromLowPointWith(points: List<PointWithValue>, p: PointWithValue): Basin {
    return getBasinFromLowPointWith(points, listOf(p)).toSet().toList()
}

private fun getBasinFromLowPointWith(points: List<PointWithValue>, ps: List<PointWithValue>): Basin {
    val adjacentPoints = ps.map { getAdjacentPoints(points, it) }.flatten()
    val notNines = adjacentPoints.filterNot { it.value == 9 }

    val newPoints = notNines.toSet().subtract(ps.toSet())
    return if (newPoints.isEmpty()) notNines
    else getBasinFromLowPointWith(points, ps + newPoints)
}

private fun getAdjacentPoints(points: List<PointWithValue>, p: PointWithValue): List<PointWithValue> {
    return points.filter {
        it.position == Position(p.position.x + 1, p.position.y) ||
        it.position == Position(p.position.x - 1, p.position.y) ||
        it.position == Position(p.position.x, p.position.y + 1) ||
        it.position == Position(p.position.x, p.position.y - 1)
    }
}

typealias Basin = List<PointWithValue>

data class PointWithValue(val position: Position, val value: Int) {
    constructor(x: Int, y: Int, v: Int): this(Position(x, y), v)
}