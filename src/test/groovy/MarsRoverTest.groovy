import groovy.transform.CompileDynamic
import spock.lang.Specification
import spock.lang.Unroll

import java.security.SecureRandom

@CompileDynamic
class MarsRoverTest extends Specification {
  def "initialization"() {
    when: "rover is created"
    def rover = new MarsRover()

    then: "rover has expected position"
    rover.position == new Position(0, 0)
    and: "an expected direction"
    rover.direction == Direction.NORTH
  }

  @Unroll
  def "moving forward from #startPosition, #startDirection to #expected"() {
    given: "a rover"
    def rover = new MarsRover(startPosition, startDirection)

    when: "forward command is received"
    def result = rover.forward()

    then: "position changes"
    result.position == expected
    and: "direction remains unchanged"
    result.direction == startDirection

    where:
    startPosition       | startDirection  | expected
    new Position(0, 0)  | Direction.NORTH | new Position(0, 1)
    new Position(11, 5) | Direction.NORTH | new Position(11, 6)
    new Position(7, 3)  | Direction.EAST  | new Position(8, 3)
    new Position(2, 9)  | Direction.SOUTH | new Position(2, 8)
    new Position(5, 19) | Direction.WEST  | new Position(4, 19)
  }

  @Unroll
  def "moving backward from #startPosition, #startDirection to #expected"() {
    given: "a rover"
    def rover = new MarsRover(startPosition, startDirection)

    when: "backward command is received"
    def result = rover.backward()

    then: "position changes"
    result.position == expected
    and: "direction remains unchanged"
    result.direction == startDirection

    where:
    startPosition       | startDirection  | expected
    new Position(0, 0)  | Direction.NORTH | new Position(0, -1)
    new Position(11, 5) | Direction.NORTH | new Position(11, 4)
    new Position(7, 3)  | Direction.EAST  | new Position(6, 3)
    new Position(2, 9)  | Direction.SOUTH | new Position(2, 10)
    new Position(5, 19) | Direction.WEST  | new Position(6, 19)
  }

  @Unroll
  def "turning right from #startDirection to #expected"() {
    given: "a rover"
    def rover = new MarsRover(aPosition(), startDirection)

    when: "turn right command is received"
    def result = rover.right()

    then: "direction changes"
    result.direction == expected
    and: "position remains unchanged"
    result.position == rover.position

    where:
    startDirection  | expected
    Direction.NORTH | Direction.EAST
    Direction.EAST  | Direction.SOUTH
    Direction.SOUTH | Direction.WEST
    Direction.WEST  | Direction.NORTH
  }

  @Unroll
  def "turning left from #startDirection to #expected"() {
    given: "a rover"
    def rover = new MarsRover(aPosition(), startDirection)

    when: "turn left command is received"
    def result = rover.left()

    then: "direction changes"
    result.direction == expected
    and: "position remains unchanged"
    result.position == rover.position

    where:
    startDirection  | expected
    Direction.NORTH | Direction.WEST
    Direction.WEST  | Direction.SOUTH
    Direction.SOUTH | Direction.EAST
    Direction.EAST  | Direction.NORTH
  }

  @Unroll
  def "executing #command changes rover to #expected"() {
    given: "a rover"
    def rover = new MarsRover()

    when: "command is received"
    def result = rover.commands(command)

    then: "rover has expected state"
    result == expected

    where:
    command      | expected
    "f"          | new MarsRover(new Position(0, 1), Direction.NORTH)
    "b"          | new MarsRover(new Position(0, -1), Direction.NORTH)
    "l"          | new MarsRover(new Position(0, 0), Direction.WEST)
    "r"          | new MarsRover(new Position(0, 0), Direction.EAST)
    "a"          | new MarsRover()
    "fff"        | new MarsRover(new Position(0, 3), Direction.NORTH)
    "fffrbbll"   | new MarsRover(new Position(-2, 3), Direction.WEST)
    ""           | new MarsRover()
    "asdffffbrl" | new MarsRover()
  }

  private static Position aPosition() {
    new Position(new SecureRandom().nextInt(), new SecureRandom().nextInt())
  }
}
