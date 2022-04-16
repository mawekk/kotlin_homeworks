package homeworks.homework3

class AVLTree<K : Comparable<K>, V> : MutableMap<K, V> {
    override var size: Int = 0
        private set
    private var root: AVLNode<K, V>? = null

    override val entries: MutableSet<MutableMap.MutableEntry<K, V>> = iterator().asSequence().toMutableSet()
    override val keys: MutableSet<K> = iterator().asSequence().map { it.key }.toMutableSet()
    override val values: MutableCollection<V> = iterator().asSequence().map { it.value }.toMutableSet()

    override fun containsKey(key: K): Boolean = keys.contains(key)

    override fun containsValue(value: V): Boolean = values.contains(value)

    override fun get(key: K): V? = root?.findNode(key)?.value

    override fun isEmpty(): Boolean = root == null

    override fun clear() {
        root = null
    }

    private fun putNode(node: AVLNode<K, V>?, key: K, value: V): AVLNode<K, V> {
        return if (node == null) {
            AVLNode(key, value).balance()
        } else
            when (key.compareTo(node.key)) {
                1 -> putNode(node.rightChild, key, value).balance()
                -1 -> putNode(node.leftChild, key, value).balance()
                else -> node.balance()
            }
    }

    override fun put(key: K, value: V): V? {
        root = putNode(root, key, value)
        return root?.value
    }

    override fun putAll(from: Map<out K, V>) {
        from.forEach { (put(it.key, it.value)) }
    }

    override fun remove(key: K): V? {
        return root?.removeNode(key)?.value
    }
}
