val day10 = day(10) {
    part1(expectedExampleOutput = 13140, expectedOutput = 13820) {
        val cycles = input.fold(listOf(1)) { acc, s ->
            when (s) {
                "noop" -> acc + acc.last()
                else -> acc + acc.last() + (acc.last() + s.substringAfter("addx ").toInt())
            }
        }
        cycles.run {
            ss(20) + ss(60) + ss(100) + ss(140) + ss(180) + ss(220)
        }
    }

    part2(expectedExampleOutput = 0, expectedOutput = 0) {
        val cycles = input.fold(listOf(1)) { acc, s ->
            when (s) {
                "noop" -> acc + acc.last()
                else -> acc + acc.last() + (acc.last() + s.substringAfter("addx ").toInt())
            }
        }
        cycles.forEachIndexed { i, s ->
            val sprite = s.let { it - 1..it + 1 }
            when {
                sprite.contains(i % 40) -> print('#')
                else -> print('.')
            }
            if ((i + 1) % 40 == 0) println()
        }
        println()
        0
    }
}

private fun List<Int>.ss(cycle: Int) = this[cycle - 1] * cycle
