import groovy.transform.CompileDynamic
import spock.lang.Specification

// ffffffffffbbbbrrlll
// Rover
// - pos (x, y)
// - direction (NSEW)

// 1. init
// 2. single commands
//  - forward
//  - backward
//  - left
//  - right
// 3. Char -> command
// 4. Multiple commands

@CompileDynamic
class MarsRoverTest extends Specification {
  def "initialization"() {
    when: "rover is created"

    then: "rover has expected position"
    and: "an expected direction"
  }
}
