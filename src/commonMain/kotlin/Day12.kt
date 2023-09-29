val day12 = day(12) {
    val points = input.mapIndexed { y, row ->
        row.mapIndexed { x, c ->
            Point(x, y, (c.code - 96).let { if (it == -13) 0 else if (it == -27) 27 else it })
        }
    }

    val flatten = points.flatten()
    val width = 0..flatten.maxOf { it.x }
    val height = 0..flatten.maxOf { it.y }


    fun printPoints12(path: List<Point>) {

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

    fun findPath(points: List<List<Point>>, start: Point): Int? {
        val flatten = points.flatten()
        val dest = flatten.first { it.value == 27 }

        val delta = flatten.associateWith { Int.MAX_VALUE }.toMutableMap()
        delta[start] = 0
        val queue = PriorityQueue<Point>(compareBy { delta[it] })
        queue.add(start)

        var cur: Point? = null
        val chronoStart = getMillis()
        val prev = mutableMapOf<Point, Point>()
        var done = false
        while (!queue.isEmpty() && !done) {
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
                        if (n == dest)
                            done = true
                    }
                }
        }
        val chrono = getMillis() - chronoStart
        println("Dijkstra time: $chrono ms")
        delta.getValue(cur!!)

        if (!prev.containsKey(dest)) return null
        val path = prev.entries.reversed().fold(listOf(prev.values.last())) { acc, _ ->
            prev[acc.last()]?.let { acc + it } ?: acc
        }.reversed()

        printPoints12(path + dest)
        println("path size: ${path.size}")
        return path.size
    }


    part1(expectedExampleOutput = 31, expectedOutput = 352) {
        val start = flatten.first { it.value == 0 }
        findPath(points, start)
    }

    part2(expectedExampleOutput = 29, expectedOutput = 345) {
        val starts = flatten.filter { it.value <= 1 }
        starts.map { findPath(points, it) }.filterNotNull().min()
    }

}

