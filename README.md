# Mars Rover in Kotlin

Project setup to do the [Mars Rover Kata](https://kata-log.rocks/mars-rover-kata)
and show some folks kotlin basics.
While the implementation is supposed to be written
in kotlin, the tests are in groovy as I consider
[Spock](http://spockframework.org/) to be the greatest thing ever<sup>TM</sup>.

## rationale

Many Coding Katas emphasize on algorithms.
While this can be fun, I consider the main selling points
of kotlin its strong type system, which needs a different
type of exercise. The Mars Rover forces you to create
some types (such as rover, position, direction) and
lots of functions. I usually do the kata in a FP
way (without ever mutating the state).

## takeaways

- class creation without any javaesque boilerplate
- data classes
- receiver functions
- expression-style functions
- type inference

## run tests

```shell script
mvn test
```

# solution branch

Take a look at the solution branch for a
possible solution!
