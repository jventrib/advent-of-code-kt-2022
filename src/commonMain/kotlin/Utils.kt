import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * Reads lines from the given input txt file.
 */
fun readInput(day: Int, name: String = "input.txt") =
    readAllText("src/commonMain/resources/d${day.toString().padStart(2, '0')}/$name")


expect fun readAllText(filePath: String): List<String>

fun List<String>.parseLineToIntList() = first().split(",").map(String::toInt)

fun String.isNumeric() = this.toIntOrNull() != null

fun <E> day(dayNumber: Int, block: Day<E>.() -> Unit): Day<E> {
    val day = Day<E>(dayNumber, block)
    return day
}


class Day<E>(val dayNumber: Int, val block: Day<E>.() -> Unit) {
    lateinit var input: List<String>
    lateinit var part1: Part<E>
    lateinit var part1Example: Part<E>
    lateinit var part2: Part<E>
    lateinit var part2Example: Part<E>

    fun part1(expectedExampleOutput: E, expectedOutput: E? = null, block: () -> E): Part<E> {
        part1 = Part(expectedOutput, block)
        part1Example = Part(expectedExampleOutput, block)
        return part1
    }

    fun part2(expectedExampleOutput: E, expectedOutput: E? = null, block: () -> E): Part<E> {
        part2 = Part(expectedOutput, block)
        part2Example = Part(expectedExampleOutput, block)
        return part2
    }
}

class Part<E>(
    val expected: E?,
    private val block: () -> E
) {
    val output get() = block()
    override fun toString(): String {
        return "Part(output=$output)"
    }
}

data class Point(var x: Int, var y: Int, var value: Int = 0) {

    fun moveX(amount: Int) {
        x += amount
    }

    fun moveY (amount: Int) {
        y += amount
    }
}


fun <T, R> Flow<T>.concurrentMap(
    dispatcher: CoroutineDispatcher,
    concurrencyLevel: Int,
    transform: suspend (T) -> R
): Flow<R> {
    return flatMapMerge(concurrencyLevel) { value ->
        flow { emit(transform(value)) }
    }.flowOn(dispatcher)
}

fun lineSeparator() = "\n"

// Common
expect fun getMillis(): Long


inline fun <T> Iterable<T>.takeWhileInclusive(
    predicate: (T) -> Boolean
): List<T> {
    var shouldContinue = true
    return takeWhile {
        val result = shouldContinue
        shouldContinue = predicate(it)
        result
    }
}
