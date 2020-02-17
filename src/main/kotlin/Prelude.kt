import java.util.*

fun <U> CharSequence.foldOptional(initial: U, binder: (U, Char) -> Optional<U>) =
    toList().foldOptional(initial, binder)

fun <T, U> Iterable<T>.foldOptional(initial: U, binder: (U, T) -> Optional<U>): Optional<U> =
    fold(Optional.of(initial)) { acc, c -> acc.flatMap { binder(it, c) } }
