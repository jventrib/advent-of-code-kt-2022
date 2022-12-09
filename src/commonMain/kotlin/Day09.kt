import kotlin.math.absoluteValue

val day09 = day(9) {
    part1(expectedExampleOutput = 13, expectedOutput = 5695) {
        val h = Point(0, 0)
        val t = Point(0, 0)
        val tPath = input.fold(listOf<Point>()) { acc, s ->
            val split = s.split(' ')
            val dir = split[0].first()
            val amount = split[1].toInt()

            val stepsPath = (0 until amount).fold(listOf<Point>()) { stepAcc, _ ->
                when (dir) {
                    'L' -> h.moveX(-1)
                    'R' -> h.moveX(1)
                    'U' -> h.moveY(-1)
                    'D' -> h.moveY(1)
                    else -> throw Exception("Unknown char")
                }
                val dX = h.x - t.x
                val dY = h.y - t.y
                when {
                    dX.absoluteValue + dY.absoluteValue > 2 -> {
                        t.moveX(dX.max1())
                        t.moveY(dY.max1())
                    }
                    dX.absoluteValue > 1 -> {
                        t.moveX(dX.max1())
                    }
                    dY.absoluteValue > 1 -> {
                        t.moveY(dY.max1())
                    }
                }
                stepAcc + t.copy()
            }
            val tPositions = (acc + stepsPath).distinct()
            tPositions
        }
            printPoints(tPath)
        tPath.size
    }

    part2(expectedExampleOutput = 0, expectedOutput = 0) {
        0
    }
}

fun printPoints(points: List<Point>) {
    val width = points.minOf { it.x }..points.maxOf { it.x }
    val height = points.minOf { it.y }..points.maxOf { it.y }

    height.forEach { y ->
        width.forEach { x ->
            if (points.any { it.x == x && it.y == y }) print("#") else print(".")
        }
        println()
    }
    println()
    println()
}

private fun Int.max1() = coerceIn(-1..1)
