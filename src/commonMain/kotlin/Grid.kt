class Grid {

    val points = listOf(listOf<Point>())
    val flatten = points.flatten()
    val height = 0..flatten.maxOf { it.y }
    val width = flatten.minOf { it.x }..flatten.maxOf { it.x }

}