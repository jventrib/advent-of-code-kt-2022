val day08 = day(8) {
    val points = input.mapIndexed { y, row -> row.mapIndexed { x, c -> Point(x, y, c.digitToInt()) } }
    val transposedPoints = points.flatten().groupBy { it.x }.values.toList()
    part1(expectedExampleOutput = 21, expectedOutput = 1814) {
        val fromLeft = getFromLeft(points)
        val fromRight = getFromRight(points)
        val fromTop = getFromLeft(transposedPoints)
        val fromBottom = getFromRight(transposedPoints)
        (fromLeft + fromRight + fromTop + fromBottom).distinctBy { it }.count()
    }

    part2(expectedExampleOutput = 8, expectedOutput = 330786) {
        val width = points.maxOf { it.size }
        val height = points.size
        points.flatten().maxOfOrNull { p ->
            val left = (p.x - 1 downTo 0).takeWhileInclusive { points[p.y][it].value < p.value }.count()
            val right = (p.x + 1 until width).takeWhileInclusive { points[p.y][it].value < p.value }.count()
            val top = (p.y - 1 downTo 0).takeWhileInclusive { points[it][p.x].value < p.value }.count()
            val bottom = (p.y + 1 until height).takeWhileInclusive { points[it][p.x].value < p.value }.count()
            right * left * top * bottom
        }
    }
}

private fun getFromLeft(m: List<List<Point>>) =
    m.map { row -> row.fold(listOf<Point>()) { acc, c -> if (acc.isEmpty() || c.value > acc.maxOf { it.value }) acc + c else acc } }
        .flatten()

private fun getFromRight(m: List<List<Point>>) =
    m.map { row -> row.foldRight(listOf<Point>()) { c, acc -> if (acc.isEmpty() || c.value > acc.maxOf { it.value }) acc + c else acc } }
        .flatten()
