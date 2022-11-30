import kotlin.test.Test
import kotlin.test.assertEquals

class Tests {

    private val day = day03

    @Test
    fun todayPart1Example() {
        doPart1Example(day)
    }

    @Test
    fun todayPart1() {
        doPart1(day)
    }

    @Test
    fun todayPart2Example() {
        doPart2Example(day)
    }

    @Test
    fun todayPart2() {
        doPart2(day)
    }

    @Test
    fun oneDayTest() = dayTest(day)

    //
//    @Suppress("UselessCallOnCollection")
//    @TestFactory
//    fun allTests() = days
//        .filterNotNull()
//        .flatMap { d -> dayTest(d) }
//
//    @Suppress("UselessCallOnCollection")
//    @TestFactory
//    fun allTestsWithoutExample() = days
//        .filterNotNull()
//        .flatMap { d -> dayTest(d, false) }
//
//
}

fun <E> doPart1Example(d: Day<E>) {
    dayPartTest(d, true, { part1Example }, "Part1Example")
}

fun <E> doPart1(d: Day<E>) {
    dayPartTest(d, false, { part1 }, "Part1")
}

fun <E> doPart2Example(d: Day<E>) {
    dayPartTest(d, true, { part2Example }, "Part2Example")
}

fun <E> doPart2(d: Day<E>) {
    dayPartTest(d, false, { part2 }, "Part2")
}

private fun <E> dayPartTest(d: Day<E>, example: Boolean, part: Day<E>.() -> Part<E>, label: String) {
    println("Day ${d.dayNumber} - $label")

    d.input = readInput(d.dayNumber, if (example) "input_example.txt" else "input.txt")
    println("input: ${d.input}")
    val start = getMillis()
    d.block(d)
    val output = d.part().output
    val elapsed = getMillis() - start
    println("output: $output")
    println("time: ${elapsed}ms")

    assertEquals(d.part().expected, output)
}

fun <E> dayTest(d: Day<E>, withExample: Boolean = true) {
    if (withExample) doPart1Example(d)
    doPart1(d)
    if (withExample) doPart2Example(d)
    doPart2(d)
}

