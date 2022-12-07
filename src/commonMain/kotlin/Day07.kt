val day07 = day(7) {
    part1(expectedExampleOutput = 95437, expectedOutput = 0) {

        val fold = input.fold(Node.Dir("/", setOf(), null)) { node: Node.Dir, l ->
            when {
                l.startsWith('$') -> {
                    when {
                        l.startsWith("$ cd") -> {
                            when (val newDir = l.substringAfter("$ cd")) {
                                "/" -> {
                                    var root = node
                                    while (node.parent != null) {
                                        root = root.parent!!
                                    }
                                    root
                                }
                                ".." -> node.parent!!
                                else -> Node.Dir(newDir, setOf(), node.parent)
                            }
                        }
                        l == "$ ls" -> node
                        else -> throw Exception("Unrecognized line")
                    }
                }

                l.startsWith("dir") -> {
                    node
                    Node.Dir(node.name, node.children+ Node.Dir(), )

                }

                else -> {
                    node
                }
            }

        }
        fold
    }

    part2(expectedExampleOutput = 0, expectedOutput = 0) {
        0
    }
}

private sealed class Node(val name: String, val parent: Dir?) {
    abstract fun getSize(): Int

    class Dir(name: String, val children: Set<Node>, parent: Dir?) : Node(name, parent) {
        override fun getSize(): Int = children.sumOf { getSize() }
    }

    class File(name: String, val size: Int, parent: Dir?) : Node(name, parent) {
        override fun getSize(): Int = size
    }

    override fun toString(): String {
        return "Node(name='$name', size='${getSize()}')"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as Node

        if (name != other.name) return false
        if (parent != other.parent) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + (parent?.hashCode() ?: 0)
        return result
    }

}
