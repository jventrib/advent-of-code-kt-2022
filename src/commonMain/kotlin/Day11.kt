val day11 = day(11) {
    part1(expectedExampleOutput = 10605, expectedOutput = 64032) {
        doPart(3, 20)
    }

    part2(expectedExampleOutput = 2713310158, expectedOutput = 12729522272) {
        doPart(1, 10000)
    }
}

private fun Day<Long>.doPart(damage: Int, rounds: Int): Long {
    val monkeys = input.chunked(7).fold(listOf<Monkey>()) { acc, l ->
        val id = l[0].substringAfter("Monkey ").substringBefore(':').toInt()
        val items = l[1].substringAfter("Starting items: ").split(", ").map(String::toLong)
        val ope = l[2].substringAfter("Operation: new = old ").substringBefore(' ').first()
        val op2 = l[2].substringAfter("Operation: new = old ").substringAfterLast(' ').toLongOrNull()
        val operation = { old: Long -> old.let { if (ope == '*') it * (op2 ?: old) else it + (op2 ?: old) } }
        val div = l[3].substringAfter("Test: divisible by ").toInt()
        val mWhenTrue = l[4].substringAfter("If true: throw to monkey ").toInt()
        val mWhenFalse = l[5].substringAfter("If false: throw to monkey ").toInt()
        acc + Monkey(id, items.toMutableList(), operation, div, mWhenTrue, mWhenFalse)
    }

    val multiple = monkeys.map { it.divisor }.reduce { acc, i -> acc * i }

    val inspections = (1..rounds).fold(List(monkeys.size) { 0L }) { acc, round ->
        val map = monkeys.map { m ->
            val inspectedItems = m.items.count()
            m.items.forEach { i ->
                val worry = m.ope(i) / damage
                val divisible = worry % m.divisor == 0L
                val dest = (if (divisible) m.monkeyWhenTrue else m.monkeyWhenFalse).let { monkeys[it] }
                val newWorry = worry % multiple
                dest.items = dest.items + newWorry
                m.items = m.items - i
            }
            inspectedItems
        }
        val inspection = acc.zip(map) { a, b -> a + b }
        if (round == 20 || round == 1000 || round == 2000 || round == 3000 || round == 4000 || round == 5000
            || round == 6000 || round == 7000 || round == 8000 || round == 9000 || round == 10000
        ) {
            printMonkey(round, monkeys)
            printInspections(round, inspection)
        }
        inspection
    }
    return inspections.sortedDescending().take(2).reduce { acc, i -> acc * i }
}

private data class Monkey(
    val id: Int,
    var items: List<Long>,
    val ope: (Long) -> Long,
    val divisor: Int,
    val monkeyWhenTrue: Int,
    val monkeyWhenFalse: Int
)

private fun printMonkey(round: Int, monkeys: List<Monkey>) {
    println("Round $round")
    monkeys.forEach { m ->
        println("Monkey ${m.id}: ${m.items.joinToString(", ")}")
    }
    println()
}

private fun printInspections(round: Int, monkeys: List<Long>) {
    println("Round $round")
    monkeys.forEachIndexed { i, times ->
        println("Monkey $i inspected items $times times.")
    }
    println()
}

