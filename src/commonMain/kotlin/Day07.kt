import Node.*

val day07 = day(7) {
    part1(expectedExampleOutput = 95437, expectedOutput = 1297683) {
        dirs().map { it.getNodeSize() }.filter { it < 100000 }.sum()
    }

    part2(expectedExampleOutput = 24933642, expectedOutput = 5756764) {
        val dirs = dirs()
        val freeSize = 70000000 - dirs.first().getNodeSize()
        dirs.drop(1).filter { freeSize + it.getNodeSize() > 30000000 }.minOf { it.getNodeSize() }

    }
}

private fun Day<Int>.dirs(): List<Dir> {
    var node = Dir("/", mutableSetOf(), null)
    input.drop(1).forEach { l ->
        when {
            l.startsWith("$ cd ..") -> node.parent?.also { node = it }
            l.startsWith("$ cd ") -> node =
                node.children.filterIsInstance<Dir>().first { it.name == l.substringAfter("$ cd ") }

            l.startsWith("dir") -> node.children.add(Dir(l.substringAfter("dir "), mutableSetOf(), node))
            l.substringBefore(' ').isNumeric() -> node.children.add(
                l.split(' ').let { File(it[1], it[0].toInt(), node) })
        }
    }
    while (node.parent != null) {
        node = node.parent!!
    }
    return node.flattenDir()
}

private sealed class Node(val name: String, val parent: Dir?) {
    abstract fun getNodeSize(): Int

    class Dir(name: String, val children: MutableSet<Node>, parent: Dir?) : Node(name, parent) {
        override fun getNodeSize(): Int = children.sumOf { it.getNodeSize() }

        fun flattenDir(): List<Dir> {
            val filterIsInstance = this.children.filterIsInstance<Dir>()
            return listOf(this) + filterIsInstance.flatMap { it.flattenDir() }
        }
    }

    class File(name: String, val size: Int, parent: Dir?) : Node(name, parent) {
        override fun getNodeSize(): Int = size
    }
}
