expect class PriorityQueue<E>(size: Int) {
    fun add(element: E) : Boolean
    fun poll(): E
    fun isEmpty(): Boolean
    fun addAll(elements: Collection<E>) : Boolean
    val size: Int
}