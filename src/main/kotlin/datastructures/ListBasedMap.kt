package datastructures

class ListBasedMap<K, V> : CustomMutableMap<K, V> {
    private val list = CustomLinkedList<CustomMutableMap.Entry<K, V>>()

    override fun iterator(): MutableIterator<CustomMutableMap.Entry<K, V>> = list.iterator()

    override fun put(
        key: K,
        value: V,
    ): V? {
        var rtn: V? = null
        val iterator = list.iterator()
        while (iterator.hasNext()) {
            val node = iterator.next()
            if (node.key == key) {
                rtn = node.value
                iterator.remove()
                break
            }
        }
        list.addFirst(CustomMutableMap.Entry(key, value))
        return rtn
    }

    override fun remove(key: K): V? {
        var rtn: V? = null
        val iterator = list.iterator()
        while (iterator.hasNext()) {
            val node = iterator.next()
            if (node.key == key) {
                rtn = node.value
                iterator.remove()
                break
            }
        }
        return rtn
    }

    override fun get(key: K): V? {
        var rtn: V? = null
        val iterator = list.iterator()
        while (iterator.hasNext()) {
            val node = iterator.next()
            if (node.key == key) {
                rtn = node.value
                break
            }
        }
        return rtn
    }

    override fun contains(key: K): Boolean {
        val iterator = list.iterator()
        while (iterator.hasNext()) {
            val node = iterator.next()
            if (node.key == key) {
                return true
            }
        }
        return false
    }
}
