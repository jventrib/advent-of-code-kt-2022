val day05 = day<String>(5) {
    part1(expectedExampleOutput = "CMZ", expectedOutput = "CNSZFDVLJ") {
        doPart(true)
    }

    part2(expectedExampleOutput = "MCD", expectedOutput = "QNDWLMGNS") {
        doPart(false)
    }
}

private fun Day<String>.doPart(reverse: Boolean): String {
    val regex = """\[(.)]""".toRegex()
    val pairs = input.filter { it.contains('[') }.flatMap { line ->
        regex.findAll(line).map {
            Pair(it.groups[1]!!.value, it.range.first)
        }
    }
    val columns = pairs.groupBy { it.second }.entries.sortedBy { it.key }.map { col -> col.value.map { it.first } }

    val procedureRegex = "move (.*) from (.) to (.)".toRegex()
    val procedure = input.filter { it.contains("move") }.map { line ->
        val gv = procedureRegex.find(line)!!.groupValues
        Move(gv[1].toInt(), gv[2].toInt(), gv[3].toInt())
    }

    val movedCrates = procedure.fold(columns) { acc, m ->
        acc.mapIndexed { index, crates ->
            when (index + 1) {
                m.from -> crates.drop(m.amount)
                m.to -> acc[m.from - 1].take(m.amount).let { if (reverse) it.reversed() else it } + crates
                else -> crates
            }
        }
    }

    return movedCrates.joinToString("") { it.first() }
}

private data class Move(val amount: Int, val from: Int, val to: Int)
