package datastructures

interface Node<T> {
    var next: Node<T>?
}

class RootNode<T>(
    override var next: Node<T>? = null,
) : Node<T>

class ValueNode<T>(
    val value: T,
    override var next: Node<T>? = null,
) : Node<T>

class CustomLinkedList<T> : MutableIterable<T> {
    inner class CustomIterator : MutableIterator<T> {
        private var current: Node<T> = root
        private var next: ValueNode<T>? = root.next as ValueNode<T>?
        private var prev: Node<T>? = null

        override fun hasNext(): Boolean = next != null

        override fun next(): T {
            if (next == null) throw NoSuchElementException()
            val rtn = next!!.value
            prev = current
            current = next!!
            next = next!!.next as ValueNode<T>?
            return rtn
        }

        override fun remove() {
            if (prev == null) {
                throw IllegalStateException()
            }
            if (prev == root) {
                current = root
                root.next = next
            } else {
                current = prev!!
                prev!!.next = next
            }
            prev = null
        }
    }

    override fun iterator(): MutableIterator<T> = CustomIterator()

    private val root = RootNode<T>()
    val isEmpty
        get() = root.next == null

    fun addFirst(value: T) {
        val newnode = ValueNode<T>(value, root.next)
        root.next = newnode
    }

    fun removeFirst(): T? {
        if (this.isEmpty) {
            return null
        }
        val first: ValueNode<T> = root.next as ValueNode<T>
        root.next = first.next
        return first.value
    }

    fun peek(): T? {
        if (this.isEmpty) {
            return null
        }
        return (root.next as ValueNode<T>).value
    }

    fun contains(value: T): Boolean {
        if (this.isEmpty) {
            return false
        }
        var node: ValueNode<T> = root.next as ValueNode<T>
        while (true) {
            if (node.value == value) return true
            if (node.next == null) return false
            node = node.next as ValueNode<T>
        }
    }
}
