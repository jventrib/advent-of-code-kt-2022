val day12 = day(12) {
    part1(expectedExampleOutput = 31, expectedOutput = 0) {
        val points = input.mapIndexed { y, row -> row.mapIndexed { x, c -> Point(x, y, c.code - 96) } }

        val flatten = points.flatten()
        val start = flatten.first { it.value == -13 }
        val dest = flatten.first { it.value == -27 }


        var pos = start
        while (pos != dest) {
            val top = points.getOrNull(pos.y-1)?.get(pos.x)
            val left = points.get(pos.y).getOrNull(pos.x-1)
            val bottom = points.getOrNull(pos.y+1)?.get(pos.x)
            val right = points.get(pos.y).getOrNull(pos.x+1)
            println(top)
            pos = dest
        }
        0
    }

    part2(expectedExampleOutput = 0, expectedOutput = 0) {
        0
    }
}
