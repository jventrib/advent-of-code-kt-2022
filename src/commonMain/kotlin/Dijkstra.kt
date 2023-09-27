private fun doDijkstra(
    points: List<List<Point>>,
    start: Point
): Int {
    val queue = PriorityQueue<Point>(points.flatten().size)
    queue.add(start)

    val delta = points.flatten().associateWith { Int.MAX_VALUE }.toMutableMap()

    var cur: Point? = null
    val chronoStart = getMillis()

    while (!queue.isEmpty()) {
        cur = queue.poll()
            val list = cur.neighborsIn(points)
            list
                .forEach { n ->
                    val newPath = delta.getValue(cur) + n.value
                    if (newPath < delta.getValue(n)) {
                        delta[n] = newPath
                        queue.add(n)
                    }
                }
    }
    val chrono = getMillis() - chronoStart
    println("Dijkstra time: $chrono ms")
    return delta.getValue(cur!!)
}

