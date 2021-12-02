import java.io.File

fun getPositionAfterMovement(filename: String): Pair<Int, Int> {
    val movementCommands: List<MovementCommand> = readMovementCommandsFromFile(filename)
    val (horizontalCommands, verticalCommands) = movementCommands.partition { it.direction == MovementDirection.Forward }
    return horizontalCommands.sumOf { it.distance } to verticalCommands.sumOf { if(it.direction == MovementDirection.Up) -it.distance else it.distance }
}

data class Position(val x: Int, val y: Int)

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

fun readMovementCommandsFromFile(filename: String): List<MovementCommand> {
    return File(filename).readLines().map { parseLineIntoMovementCommand(it) }
}

fun parseLineIntoMovementCommand(line: String): MovementCommand {
    val words = line.split(' ')
    if (words.size < 2) throw Error("Cannot parse command")
    val directionWord = words[0]
    val distanceWord = words[1]

    val direction = MovementDirection.values().firstOrNull { it.name.lowercase() == directionWord.lowercase() } ?: throw Error("Cannot parse direction $directionWord")
    val distance = distanceWord.toIntOrNull() ?: throw Error("Cannot parse distance $distanceWord")
    return MovementCommand(direction, distance)
}


data class MovementCommand(val direction: MovementDirection, val distance: Int)

enum class MovementDirection{ Forward, Down, Up, }
