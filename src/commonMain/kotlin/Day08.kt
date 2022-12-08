val day08 = day(8) {
    part1(expectedExampleOutput = 0, expectedOutput = 0) {
        val fromLeft = input.drop(1).dropLast(1).map { row ->
            row.dropLast(1).foldIndexed(listOf<Pair<Int, Int>>()) { i, acc, c ->
                val digitToInt = c.digitToInt()
                if (acc.isEmpty() || digitToInt > acc.maxOf { it.first }) {
                    acc + (digitToInt to i)
                } else {
                    acc
                }
            }.drop(1)
        }
        val fromRight = input.drop(1).dropLast(1).map { row ->
            row.drop(1).foldRightIndexed(listOf<Pair<Int, Int>>()) { i, c, acc ->
                val digitToInt = c.digitToInt()
                if (acc.isEmpty() || digitToInt > acc.maxOf { it.first }) {
                    acc + (digitToInt to i+1)
                } else {
                    acc
                }
            }.drop(1)
        }
        
        
        
        0
    }

    part2(expectedExampleOutput = 0, expectedOutput = 0) {
        0
    }
}
