val day04 = day(4) {
    val pairList = input.map { line ->
        val r1 = line.substringBefore(',').let { range ->
            range.split('-').let { IntRange(it[0].toInt(), it[1].toInt()) }
        }
        val r2 = line.substringAfter(',').let { range ->
            range.split('-').let { IntRange(it[0].toInt(), it[1].toInt()) }
        }
        Pair(r1, r2)
    }

    part1(expectedExampleOutput = 2, expectedOutput = 588) {
        pairList.count { (r1, r2) -> r1.toList().containsAll(r2.toList()) || r2.toList().containsAll(r1.toList()) }
    }

    part2(expectedExampleOutput = 4, expectedOutput = 911) {
        pairList.count { (r1, r2) -> r1.intersect(r2).isNotEmpty() || r2.intersect(r1).isNotEmpty() }
    }
}
