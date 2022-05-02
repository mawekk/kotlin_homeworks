package homeworks.homework3

import java.util.Collections.max
import kotlin.math.pow

fun Int.pow(power: Int): Int = this.toDouble().pow(power).toInt()

class AVLTree<K : Comparable<K>, V> : MutableMap<K, V> {
    private var root: AVLNode<K, V>? = null
    private val nodeWorker = NodeWorker()

    override var size: Int = 0
        private set

    override val entries: MutableSet<MutableMap.MutableEntry<K, V>>
        get() = root?.traverse() ?: mutableSetOf()
    override val keys: MutableSet<K>
        get() = entries.map { it.key }.toMutableSet()
    override val values: MutableCollection<V>
        get() = entries.map { it.value }.toMutableList()

    override fun containsKey(key: K): Boolean = keys.contains(key)

    override fun containsValue(value: V): Boolean = values.contains(value)

    override fun get(key: K): V? = root?.findNode(key)?.value

    override fun isEmpty(): Boolean = size == 0

    override fun clear() {
        root = null
        size = 0
    }

    override fun put(key: K, value: V): V? {
        root = nodeWorker.put(root, key, value).balance()

        return root?.value
    }

    override fun putAll(from: Map<out K, V>) = from.forEach { put(it.key, it.value) }

    override fun remove(key: K): V? {
        root = nodeWorker.remove(root, key)

        return root?.value
    }

    override fun toString(): String = root?.let {
        StringSerializer.serialize(it)
    } ?: "empty tree"

    private inner class NodeWorker {
        fun remove(node: AVLNode<K, V>?, key: K): AVLNode<K, V>? {
            if (node != null) {
                when {
                    key > node.key -> node.rightChild = remove(node.rightChild, key)
                    key < node.key -> node.leftChild = remove(node.leftChild, key)
                    else -> {
                        size--

                        return if (node.rightChild == null) {
                            node.leftChild
                        } else {
                            val right = node.rightChild
                            val left = node.leftChild
                            val minimum = right?.minNode
                            minimum?.rightChild = right?.removeMinNode()
                            minimum?.leftChild = left
                            minimum?.balance()
                        }
                    }
                }
            }

            return node?.balance()
        }

        fun put(node: AVLNode<K, V>?, key: K, value: V): AVLNode<K, V> {
            if (node == null) {
                size++

                return AVLNode(key, value)
            }

            when {
                key > node.key -> node.rightChild = put(node.rightChild, key, value)
                key < node.key -> node.leftChild = put(node.leftChild, key, value)
                else -> node.setValue(value)
            }

            return node.balance()
        }
    }

    object StringSerializer {
        fun <K : Comparable<K>, V> serialize(root: AVLNode<K, V>): String {
            val resultBuffer = StringBuilder()

            val levels = MutableList(root.height) { mutableListOf<String>() }.apply { splitToLevels(root, 0, this) }
            val maxNodeLength = max(root.traverse().map { it.toString().length })

            var indentLeft = 2.pow(root.height - 1) - 1
            levels.forEachIndexed { i, level ->
                val indentBetween = indentLeft

                indentLeft = 2.pow(root.height - 1 - i) - 1
                if (indentLeft < 0) {
                    indentLeft = 1
                }

                resultBuffer.append(" ".repeat(indentLeft * maxNodeLength))
                for (node in level) {
                    resultBuffer.append(node.padEnd(maxNodeLength))
                    resultBuffer.append(" ".repeat(maxNodeLength * indentBetween))
                }

                resultBuffer.append("\n\n")
            }

            return resultBuffer.toString()
        }

        private fun <K : Comparable<K>, V> splitToLevels(
            node: AVLNode<K, V>?,
            level: Int,
            list: MutableList<MutableList<String>>
        ) {
            if (node != null) {
                list[level].add(node.toString())
                splitToLevels(node.leftChild, level + 1, list)
                splitToLevels(node.rightChild, level + 1, list)
            } else if (level < list.size) {
                list[level].add("")
            }
        }
    }
}
