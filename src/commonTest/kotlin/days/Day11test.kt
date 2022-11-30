package days

import day11
import doPart1
import doPart1Example
import doPart2
import doPart2Example
import kotlin.test.Test

class Day11test {

    private val d = day11

    @Test
    fun part1Example() {
        doPart1Example(d)
    }

    @Test
    fun part1() {
        doPart1(d)
    }

    @Test
    fun part2Example() {
        doPart2Example(d)
    }

    @Test
    fun part2() {
        doPart2(d)
    }

}