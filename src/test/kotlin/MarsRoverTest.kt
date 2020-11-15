import io.kotest.core.spec.style.FunSpec
import io.kotest.data.forAll
import io.kotest.data.headers
import io.kotest.data.row
import io.kotest.data.table
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.Exhaustive
import io.kotest.property.arbitrary.bind
import io.kotest.property.arbitrary.char
import io.kotest.property.arbitrary.filter
import io.kotest.property.arbitrary.int
import io.kotest.property.checkAll
import io.kotest.property.exhaustive.enum

class MarsRoverTest : FunSpec({
  val arbPosition = Arb.bind(Arb.int(), Arb.int(), ::Position)
  val arbDirection = Exhaustive.enum<Direction>()
  val arbRover = Arb.bind(arbPosition, arbDirection, ::MarsRover)

  context("initialization") {
    test("rover has default position") {
      MarsRover().position shouldBe Position(0, 0)
    }

    test("rover has default direction") {
      MarsRover().direction shouldBe Direction.NORTH
    }
  }

  context("single commands") {
    context("moving forward") {
      table(
          headers("start", "direction", "expected"),
          row(Position(0, 0), Direction.NORTH, Position(0, 1)),
          row(Position(1, 7), Direction.NORTH, Position(1, 8)),
          row(Position(6, 3), Direction.EAST, Position(7, 3)),
          row(Position(8, 1), Direction.SOUTH, Position(8, 0)),
          row(Position(4, 9), Direction.WEST, Position(3, 9)),
      ).forAll { position, direction, expected ->
        test("from $position facing $direction to $expected") {
          val rover = MarsRover(position = position, direction = direction)

          val result = rover.forward()

          result.position shouldBe expected
          result.direction shouldBe rover.direction
        }
      }
    }
    context("turning right") {
      table(
          headers("start", "expected"),
          row(Direction.NORTH, Direction.EAST),
          row(Direction.EAST, Direction.SOUTH),
          row(Direction.SOUTH, Direction.WEST),
          row(Direction.WEST, Direction.NORTH),
      ).forAll { direction, expected ->
        test("from $direction to $expected") {
          checkAll(arbPosition) { position ->
            val rover = MarsRover(position = position, direction = direction)

            val result = rover.right()

            result.direction shouldBe expected
            result.position shouldBe position
          }
        }
      }
    }
    context("turning left") {
      checkAll(arbRover) { rover ->
        rover.left() shouldBe rover.right().right().right()
      }
    }
    context("moving backward") {
      checkAll(arbRover) { rover ->
        rover.backward() shouldBe rover.right().right().forward().left().left()
      }
    }
  }
  context("multiple commands") {
    test("f translates to forward") {
      checkAll(arbRover) { rover ->
        rover.commands("f") shouldBe rover.forward()
      }
    }
    test("b translates to backward") {
      checkAll(arbRover) { rover ->
        rover.commands("b") shouldBe rover.backward()
      }
    }
    test("l translates to left") {
      checkAll(arbRover) { rover ->
        rover.commands("l") shouldBe rover.left()
      }
    }
    test("r translates to right") {
      checkAll(arbRover) { rover ->
        rover.commands("r") shouldBe rover.right()
      }
    }
    test("invalid characters are ignored") {
      val arbInvalidInput = Arb.char().filter { "fblr".contains(it).not() }
      checkAll(arbRover, arbInvalidInput) { rover, input ->
        rover.commands(input.toString()) shouldBe rover
      }
    }
    test("empty input is ignored") {
      checkAll(arbRover) { rover ->
        rover.commands("") shouldBe rover
      }
    }
    test("multiple commands are executed") {
      MarsRover().commands("fffffllbbrf") shouldBe MarsRover(Position(-1, 7), Direction.WEST)
    }
  }
})