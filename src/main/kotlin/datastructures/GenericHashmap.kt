package datastructures

typealias BucketFactory<K, V> = () -> CustomMutableMap<K, V>

abstract class GenericHashmap<K, V>(
    private val bucketFactory: BucketFactory<K, V>,
) : CustomMutableMap<K, V> {
    protected abstract val buckets: CustomMutableMap<Int, CustomMutableMap<K, V>>
    private val maxLoad = 0.75
    private var entryNum = 0
    private var bucketSize = 32

    override fun get(key: K): V? {
        val k = key.hashCode() % bucketSize
        return buckets[k]?.get(key)
    }

    private fun resize() {
        val entries: MutableList<CustomMutableMap.Entry<K, V>> = mutableListOf()
        for (i in 0 until bucketSize) {
            if (buckets[i] != null) {
                entries.addAll(buckets[i]!!)
                buckets.remove(i)
            }
        }
        bucketSize *= 2
        entryNum = 0
        for (i in 0 until bucketSize) {
            buckets.put(i, bucketFactory())
        }
        for (entry in entries) {
            this.put(entry.key, entry.value)
        }
    }

    override fun put(
        key: K,
        value: V,
    ): V? {
        if ((entryNum + 1) / bucketSize > maxLoad) {
            resize()
        }
        val k = key.hashCode() % bucketSize
        if (buckets[k] == null) {
            buckets[k] = bucketFactory()
        }
        val rtn = buckets[k]!![key]
        buckets[k]!![key] = value
        return rtn
    }

    override fun contains(key: K): Boolean =
        buckets[key.hashCode() % bucketSize] != null &&
            buckets[key.hashCode() % bucketSize]!!.contains(key)

    override fun iterator(): Iterator<CustomMutableMap.Entry<K, V>> {
        val entries = CustomLinkedList<CustomMutableMap.Entry<K, V>>()
        for ((_, bucket) in buckets) {
            for (entry in bucket) {
                entries.addFirst(entry)
            }
        }
        return entries.iterator()
    }

    override fun remove(key: K): V? {
        val rtn = buckets[key.hashCode() % bucketSize]?.remove(key)
        if (rtn != null) {
            entryNum--
        }
        return rtn
    }
}

class HashmapBackedByLists<K, V> : GenericHashmap<K, V>({ ListBasedMap<K, V>() }) {
    override val buckets = ListBasedMap<Int, ListBasedMap<K, V>>() as CustomMutableMap<Int, CustomMutableMap<K, V>>
}
