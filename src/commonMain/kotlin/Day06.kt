val day06 = day(6) {
    part1(expectedExampleOutput = 7, expectedOutput = 1651) {
        doPart(4)
    }

    part2(expectedExampleOutput = 19, expectedOutput = 3837) {
        doPart(14)
    }
}

private fun Day<Int>.doPart(size: Int) = input.first().asSequence().windowed(size)
    .indexOfFirst { chars -> chars.groupingBy { it }.eachCount().values.max() < 2 } + size
