val day10 = day(10) {
    part1(expectedExampleOutput = 13140, expectedOutput = 13820) {
        val cycles = input.fold(listOf<Int>()) { acc, s ->
            when (s) {
                "noop" -> acc + acc.lastOr1()
                else -> acc + acc.lastOr1() + (acc.lastOr1() + s.substringAfter("addx ").toInt())
            }
        }
        cycles.run {
            ss(20) + ss(60) + ss(100) + ss(140) + ss(180) + ss(220)
        }
    }

    part2(expectedExampleOutput = 0, expectedOutput = 0) {
        0
    }
}

private fun List<Int>.lastOr1() = lastOrNull() ?: 1

private fun List<Int>.ss(cycle: Int) = this[cycle - 2] * cycle