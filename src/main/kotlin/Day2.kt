import TxtIO.readMovementCommandsFromFile

fun getPositionAfterMovement(filename: String): Pair<Int, Int> {
    val movementCommands: List<MovementCommand> = readMovementCommandsFromFile(filename)
    val (horizontalCommands, verticalCommands) = movementCommands.partition { it.direction == MovementDirection.Forward }
    return horizontalCommands.sumOf { it.distance } to verticalCommands.sumOf { if(it.direction == MovementDirection.Up) -it.distance else it.distance }
}

fun getPositionAfterMovementWithAim(filename: String): Pair<Int, Int> {
    val movementCommands: List<MovementCommand> = readMovementCommandsFromFile(filename)
    var aim = 0
    var position = Position(0,0)

    movementCommands.forEach {
        when(it.direction) {
            MovementDirection.Forward -> {
                position = Position(it.distance + position.x, it.distance * aim + position.y)
            }
            MovementDirection.Down -> {
                aim += it.distance
            }
            MovementDirection.Up -> {
                aim -= it.distance
            }
        }
    }

    return position.x to position.y
}

data class Position(val x: Int, val y: Int)

data class MovementCommand(val direction: MovementDirection, val distance: Int)

enum class MovementDirection{ Forward, Down, Up, }
