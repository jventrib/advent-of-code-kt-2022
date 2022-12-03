val day01 = day(1) {
    part1(expectedExampleOutput = 24000, expectedOutput = 67450) {
        caloriesList().max()
    }

    part2(expectedExampleOutput = 45000, expectedOutput = 199357) {
        caloriesList().sortedDescending().take(3).sum()
    }
}

private fun Day<Int>.caloriesList(): List<Int> =
    input.fold(listOf(0)) { acc, s ->
        if (s.isNotEmpty()) acc.dropLast(1) + (acc.last() + s.toInt()) else acc + 0
    }
