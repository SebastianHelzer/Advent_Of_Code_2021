import TxtIO.getLinesFromFile
import kotlin.math.abs

fun howManyDangerousVents(filename: String, checkDiagonals: Boolean = false): Int {
    val lines = getLinesFromFile(filename).filter { checkDiagonals || it.isNotDiagonal }
    val dangerPoints = lines.map { it.toPoints() }.flatten()
    val groupedPoint = dangerPoints.groupingBy { it }.eachCount().filter { it.value > 1 }
    return groupedPoint.size
}

data class Line(val start: Position, val end: Position) {
    val isHorizontal: Boolean = start.y == end.y
    val isVertical: Boolean = start.x == end.x
    val isNotDiagonal: Boolean = isHorizontal || isVertical
}

fun Line.toPoints(): List<Position> {
    return if(isHorizontal) {
        if(start.x > end.x) {
            end.x.rangeTo(start.x).map { Position(it, start.y) }
        } else {
            start.x.rangeTo(end.x).map { Position(it, start.y) }
        }
    } else if (isVertical) {
        if(start.y > end.y) {
            end.y.rangeTo(start.y).map { Position(start.x, it) }
        } else {
            start.y.rangeTo(end.y).map { Position(start.x, it) }
        }
    } else {


        val posX = start.x < end.x
        val posY = start.y < end.y
        return (0..abs(start.x - end.x)).map {
            val x = if(posX) start.x + it else start.x - it
            val y = if(posY) {start.y + it} else {start.y - it}
            Position(x, y)
        }
    }
}