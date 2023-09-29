val day12 = day(12) {
    part1(expectedExampleOutput = 31, expectedOutput = 360) {
        val points = input.mapIndexed { y, row ->
            row.mapIndexed { x, c ->
                Point(x, y, (c.code - 96).let { if (it == -13) 0 else if (it == -27) 27 else it })
            }
        }

        val flatten = points.flatten()
        val start = flatten.first { it.value == 0 }
        val dest = flatten.first { it.value == 27 }

        val delta = points.flatten().associateWith { Int.MAX_VALUE }.toMutableMap()
        delta[start] = 0
        val queue = PriorityQueue<Point>(compareBy { delta[it] })
        queue.add(start)

        var cur: Point? = null
        val chronoStart = getMillis()
        val prev = mutableMapOf<Point, Point>()
        while (!queue.isEmpty()) {
            cur = queue.poll()
            cur.neighborsIn(points)
                .filterNot { it in prev.keys }
                .filter { it.value - cur.value <= 1 }
                .forEach { n ->
                    val newPath = delta.getValue(cur) + 1
                    if (newPath < delta.getValue(n)) {
                        delta[n] = newPath
                        queue.add(n)
                        prev[n] = cur
                    }
                }
        }
        val chrono = getMillis() - chronoStart
        println("Dijkstra time: $chrono ms")
        delta.getValue(cur!!)

        val path = prev.entries.reversed().fold(listOf(prev.values.last())) { acc, _ ->
            prev[acc.last()]?.let { acc + it } ?: acc
        }.reversed()
        printPoints12(path + dest)
        path.size
    }

    part2(expectedExampleOutput = 0, expectedOutput = 0) {
        0
    }

}

private fun printPoints12(path: List<Point>) {
    val width = path.minOf { it.x }..path.maxOf { it.x }
    val height = path.minOf { it.y }..path.maxOf { it.y }

    val c = path.zipWithNext { a: Point, b: Point ->
        when {
            b.x - a.x > 0 -> a to '>'
            b.x - a.x < 0 -> a to '<'
            b.y - a.y > 0 -> a to 'v'
            b.y - a.y < 0 -> a to '^'
            else -> a to '.'
        }
    }

    height.forEach { y ->
        width.forEach { x ->
            if (path.last().x == x && path.last().y == y) print('E') else {
                c.firstOrNull { it.first.x == x && it.first.y == y }?.let {
                    print(it.second)
                } ?: print('.')
            }
        }
        println()
    }
    println()
    println()
}

