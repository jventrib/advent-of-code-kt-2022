val day03 = day(3) {
    part1(expectedExampleOutput = 157, expectedOutput = 7917) {
        input.sumOf { line ->
            val middle = line.length / 2
            val compartment1 = line.substring(startIndex = 0, endIndex = middle)
            val compartment2 = line.substring(startIndex = middle)
            val common = compartment1.toSet().intersect(compartment2.toSet()).single()
            getWeight(common)
        }
    }

    part2(expectedExampleOutput = 70, expectedOutput = 2585) {
        input.chunked(3).sumOf { group ->
            val common = group[0].toSet().intersect(group[1].toSet()).intersect(group[2].toSet()).single()
            getWeight(common)
        }
    }

}
private fun getWeight(common: Char) = if (common.isUpperCase()) common.code - 38 else common.code - 96
