/*
 * Copyright (c) 2017 Kotlin Algorithm Club
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import kotlin.Comparator

actual class PriorityQueue<E> actual constructor(comparator: Comparator<in E>) : Collection<E> {
    private var _size: Int = 0
        private set
    actual override val size: Int
        get() = _size


    private var arr: Array<E?> = Array<Comparable<E>?>(size, { null }) as Array<E?>

    actual fun add(element: E): Boolean {
        if (_size + 1 == arr.size) {
            resize(1)
        }
        arr[++_size] = element
        swim(_size)
        return true
    }

    actual fun addAll(elements: Collection<E>): Boolean {
        if (_size + elements.size == arr.size) {
            resize(elements.size)
        }
        elements.forEachIndexed { index, t ->
            arr[_size + index] = t
        }
        swim(_size)
        return true
    }

    fun peek(): E {
        if (_size == 0) throw NoSuchElementException()
        return arr[1]!!
    }

    actual fun poll(): E {
        if (_size == 0) throw NoSuchElementException()
        val res = peek()
        arr.exch(1, _size--)
        sink(1)
        arr[_size + 1] = null
        if ((_size > 0) && (_size == (arr.size - 1) / 4)) {
            resize(1)
        }
        return res
    }

    private fun swim(n: Int) {
        swim(arr, n, null)
    }

    private fun sink(n: Int) {
        sink(arr, n, _size, null)
    }

    private fun resize(plus: Int = 1) {
        val old = arr
        arr = Array<Comparable<E>?>(_size * 2, { null }) as Array<E?>
//        arraycopy(old, 0, arr, 0, size + 1)
        old.copyOf(_size + plus)
    }

    actual override fun isEmpty(): Boolean {
        return _size == 0
    }

    override fun contains(element: E): Boolean {
        for (obj in this) {
            if (obj == element) return true
        }
        return false
    }

    override fun containsAll(elements: Collection<E>): Boolean {
        for (element in elements) {
            if (!contains(element)) return false
        }
        return true
    }

    override fun iterator(): Iterator<E> {
        return arr.copyOfRange(1, _size + 1).map { it!! }.iterator()
    }

    companion object {
        private fun <T> greater(arr: Array<T?>, i: Int, j: Int, comparator: Comparator<T>? = null): Boolean {
            if (comparator != null) {
                return comparator.compare(arr[i]!!, arr[j]!!) > 0
            } else {
                val left = arr[i] as Comparable<T?>
                return left > arr[j]!!
            }
        }

        fun <T> sink(arr: Array<T?>, a: Int, size: Int, comparator: Comparator<T>? = null) {
            var k = a
            while (2 * k <= size) {
                var j = 2 * k
                if (j < size && greater(arr, j, j + 1, comparator)) j++
                if (!greater(arr, k, j, comparator)) break
                arr.exch(k, j)
                k = j
            }
        }

        fun <T> swim(arr: Array<T?>, size: Int, comparator: Comparator<T>? = null) {
            var n = size
            while (n > 1 && greater(arr, n / 2, n, comparator)) {
                arr.exch(n, n / 2)
                n /= 2
            }
        }
    }

}

fun <T> Array<T>.exch(i: Int, j: Int) {
    val tmp = this[i]
    this[i] = this[j]
    this[j] = tmp
}
