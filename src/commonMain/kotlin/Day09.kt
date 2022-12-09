import kotlin.math.absoluteValue

val day09 = day(9) {
    part1(expectedExampleOutput = 13, expectedOutput = 5695) {
        doPart(2)
    }

    part2(expectedExampleOutput = 1, expectedOutput = 2434) {
        doPart(10)
    }
}

private fun Day<Int>.doPart(size: Int): Int {
    val rope = MutableList(size) { Point(0, 0) }
    val tPath = input.fold(listOf<Point>()) { acc, s ->
        val split = s.split(' ')
        val dir = split[0].first()
        val amount = split[1].toInt()

        val stepsPath = (0 until amount).fold(listOf<Point>()) { stepAcc, _ ->
            when (dir) {
                'L' -> rope.first().moveX(-1)
                'R' -> rope.first().moveX(1)
                'U' -> rope.first().moveY(-1)
                'D' -> rope.first().moveY(1)
                else -> throw Exception("Unknown char")
            }

            val knotPairs = rope.zipWithNext { prev, curr ->
                val dX = prev.x - curr.x
                val dY = prev.y - curr.y
                when {
                    dX.absoluteValue + dY.absoluteValue > 2 -> {
                        curr.moveX(dX.max1())
                        curr.moveY(dY.max1())
                    }

                    dX.absoluteValue > 1 -> curr.moveX(dX.max1())
                    dY.absoluteValue > 1 -> curr.moveY(dY.max1())
                }
                stepAcc + curr.copy()
            }
            knotPairs.last()
        }
        (acc + stepsPath).distinct()
    }
//    printPoints(tPath)
    return tPath.size
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
