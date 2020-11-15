data class MarsRover(val position: Position = Position(0, 0),
                     val direction: Direction = Direction.NORTH) {
  fun commands(list: String) = list.fold(this, MarsRover::command)

  fun forward() = copy(position = position.forward(direction))

  fun backward() = left().left().forward().right().right()

  fun right() = copy(direction = direction.cycleRight())

  fun left() = right().right().right()

  private fun command(c: Char) = when (c) {
    'f' -> forward()
    'b' -> backward()
    'l' -> left()
    'r' -> right()
    else -> this
  }

  private fun Position.forward(direction: Direction) = when (direction) {
    Direction.NORTH -> copy(y = y + 1)
    Direction.EAST -> copy(x = x + 1)
    Direction.SOUTH -> copy(y = y - 1)
    Direction.WEST -> copy(x = x - 1)
  }

  private fun Direction.cycleRight(): Direction {
    val directions = enumValues<Direction>()
    return directions[(ordinal + 1) % directions.size]
  }
}

enum class Direction {
  NORTH,
  EAST,
  SOUTH,
  WEST,
}

data class Position(val x: Int, val y: Int)
