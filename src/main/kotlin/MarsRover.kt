import java.util.*

data class MarsRover @JvmOverloads constructor(val position: Position = Position(0, 0),
                                               val direction: Direction = Direction.NORTH) {
  fun forward() = copy(position = position.forward(direction))

  fun right() = copy(direction = direction.cycleRight())

  fun left() = right().right().right()

  fun backward() = right().right().forward().left().left()

  fun commands(list: String): MarsRover = list
    .foldOptional(this, MarsRover::tryCommand)
    .orElse(this)

  private fun tryCommand(c: Char): Optional<MarsRover> = Optional.ofNullable(
      when (c) {
        'f'  -> forward()
        'b'  -> backward()
        'l'  -> left()
        'r'  -> right()
        else -> null
      })

  private fun Position.forward(direction: Direction) =
      when (direction) {
        Direction.NORTH -> copy(y = y + 1)
        Direction.EAST  -> copy(x = x + 1)
        Direction.SOUTH -> copy(y = y - 1)
        Direction.WEST  -> copy(x = x - 1)
      }

  private fun Direction.cycleRight(): Direction {
    val values = enumValues<Direction>()
    return values[(ordinal + 1) % values.size]
  }
}

data class Position(val x: Int, val y: Int)

enum class Direction {
  NORTH,
  EAST,
  SOUTH,
  WEST
}
