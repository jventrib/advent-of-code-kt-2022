import kotlin.test.assertEquals

fun <E> doPart1Example(d: Day<E>) {
    dayPartTest(d, { part1Example }, true, "Part1Example")
}

fun <E> doPart1(d: Day<E>) {
    dayPartTest(d, { part1 }, false, "Part1")
}

fun <E> doPart2Example(d: Day<E>) {
    dayPartTest(d, { part2Example }, true, "Part2Example")
}

fun <E> doPart2(d: Day<E>) {
    dayPartTest(d, { part2 }, false, "Part2")
}

private fun <E> dayPartTest(day: Day<E>, part: Day<E>.() -> Part<E>, example: Boolean, label: String) {
    val output = executeDayPart(day, part, example, label)

    assertEquals(day.part().expected, output)
}


fun <E> dayTest(d: Day<E>, withExample: Boolean = true) {
    if (withExample) doPart1Example(d)
    doPart1(d)
    if (withExample) doPart2Example(d)
    doPart2(d)
}

