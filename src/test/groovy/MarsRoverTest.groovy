import groovy.transform.CompileDynamic
import spock.lang.Specification

@CompileDynamic
class MarsRoverTest extends Specification {
  def "initialization"() {
    when: "rover is created"

    then: "rover has expected position"
    and: "an expected direction"
  }
}
