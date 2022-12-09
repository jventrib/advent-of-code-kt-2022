val day09 = day(9) {
    part1(expectedExampleOutput = 13, expectedOutput = 0) {

        var h = Point(0, 0)
        var oldH = Point(0, 0)
        val t = Point(0, 0)
        input.forEach { s ->
            val split = s.split(' ')
            val dir = split[0].first()
            val amount = split[1].toInt()

            for (it in 0 until amount) {

                when (dir) {
                    'L' -> h.left(1)
                    'R' -> h.right(1)
                    'U' -> h.up(1)
                    'D' -> h.down(1)
                    else -> throw Exception("Unknown char")
                }
//                if ((h.x - t.x) + (h.y -t.y) > 2) {

                when {
                    h.x - t.x > 1 -> t.right(1)
                    h.x - t.x < -1 -> t.left(1)
                    h.y - t.y < -1 -> t.up(1)
                    h.y - t.y > 1 -> t.down(1)
                }

                println(t)

            }

        }

        0
    }

    part2(expectedExampleOutput = 0, expectedOutput = 0) {
        0
    }
}
