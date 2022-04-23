package homeworks.homework3

import kotlin.math.pow

@Suppress("TooManyFunctions")
class AVLTree<K : Comparable<K>, V> : MutableMap<K, V> {
    private var root: AVLNode<K, V>? = null

    override var entries: MutableSet<MutableMap.MutableEntry<K, V>> = mutableSetOf()
    override val keys: MutableSet<K> = mutableSetOf()
    override val values: MutableCollection<V> = mutableSetOf()
    override var size: Int = 0
        private set

    override fun containsKey(key: K): Boolean {
        return root?.findNode(key) != null
    }

    override fun containsValue(value: V): Boolean {
        entries = traverse(root)
        return entries.find { it.value == value } != null
    }

    override fun get(key: K): V? = root?.findNode(key)?.value

    override fun isEmpty(): Boolean = root == null

    override fun clear() {
        root = null
    }

    private fun putNode(node: AVLNode<K, V>?, key: K, value: V): AVLNode<K, V> {
        if (node == null) {
            size++
            return AVLNode(key, value)
        } else
            when {
                key > node.key -> node.rightChild = putNode(node.rightChild, key, value)
                key < node.key -> node.leftChild = putNode(node.leftChild, key, value)
                else -> node.value = value
            }
        return node.balance()
    }

    override fun put(key: K, value: V): V? {
        root = putNode(root, key, value).balance()

        return root?.value
    }

    override fun putAll(from: Map<out K, V>) {
        from.forEach { (put(it.key, it.value)) }
    }

    override fun remove(key: K): V? {
        size--

        return root?.removeNode(key)?.value
    }

    private fun traverse(node: AVLNode<K, V>?): MutableSet<MutableMap.MutableEntry<K, V>> {
        val set = mutableSetOf<MutableMap.MutableEntry<K, V>>()

        return if (node != null) {
            val left = node.leftChild
            val right = node.rightChild

            if (left != null)
                set.addAll(traverse(left))
            set.add(node)
            if (right != null)
                set.addAll(traverse(right))
            set
        } else mutableSetOf()
    }

    private fun splitIntoStrings(
        node: AVLNode<K, V>?,
        level: Int,
        list: MutableList<MutableList<String>>
    ): MutableList<MutableList<String>> {
        if (node != null) {
            list[level].add(node.toString())
            splitIntoStrings(node.leftChild, level + 1, list)
            splitIntoStrings(node.rightChild, level + 1, list)
        } else
            list[level].add("_")
        return list
    }

    fun printTree() {
        val list = mutableListOf<MutableList<String>>()
        val height = root?.height ?: throw IllegalArgumentException("nothing to print: tree is empty")
        for (i in 0..height)
            list.add(mutableListOf())
        val strings = splitIntoStrings(root, 0, list)
        strings.removeLast()
        var length = 0

        entries = traverse(root)
        entries.forEach {
            val node = AVLNode(it.key, it.value)
            if (node.toString().length > length)
                length = node.toString().length
        }

        var indentLeft = (2.0.pow(height - 1) - 1).toInt()
        for (i in 0 until height) {
            val indentBetween = indentLeft
            indentLeft = (2.0.pow(height - 1 - i) - 1).toInt()
            if (indentLeft < 0)
                indentLeft = 0
            var indent = " ".repeat(indentLeft * length)
            print(indent)
            for (string in strings[i]) {
                if (indentLeft == 0)
                    indentLeft = 1
                indent = " ".repeat(length * indentBetween)
                print(string.padEnd(length).replace("_", "") + indent)
            }
            println()
            println()
        }
    }
}
