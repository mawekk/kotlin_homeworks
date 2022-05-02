package homeworks.homework3

import kotlin.math.max

class AVLNode<K : Comparable<K>, V>(override var key: K, override var value: V) : MutableMap.MutableEntry<K, V> {
    var height = 1
        private set

    var leftChild: AVLNode<K, V>? = null
    var rightChild: AVLNode<K, V>? = null

    private val leftChildHeight: Int
        get() = leftChild?.height ?: 0
    private val rightChildHeight: Int
        get() = rightChild?.height ?: 0
    private val balanceFactor: Int
        get() = rightChildHeight - leftChildHeight

    val minNode: AVLNode<K, V>
        get() = leftChild?.minNode ?: this

    fun balance(): AVLNode<K, V> {
        updateHeight()

        return when (balanceFactor) {
            LEFT_ROTATION_CASE -> {
                val balanceFactor = rightChild?.balanceFactor
                if (balanceFactor != null && balanceFactor < 0) {
                    rightChild = rightChild?.rotateRight()
                }
                rotateLeft()
            }
            RIGHT_ROTATION_CASE -> {
                val balanceFactor = leftChild?.balanceFactor
                if (balanceFactor != null && balanceFactor > 0) {
                    leftChild = leftChild?.rotateLeft()
                }
                rotateRight()
            }
            else -> this
        }
    }

    fun findNode(key: K): AVLNode<K, V>? = when {
        key > this.key -> rightChild?.findNode(key)
        key < this.key -> leftChild?.findNode(key)
        else -> this
    }

    fun removeMinNode(): AVLNode<K, V>? {
        if (leftChild == null) {
            return rightChild
        }

        leftChild = leftChild?.removeMinNode()

        return balance()
    }

    fun traverse(): MutableSet<MutableMap.MutableEntry<K, V>> {
        val result = mutableSetOf<MutableMap.MutableEntry<K, V>>()

        leftChild?.let { result.addAll(it.traverse()) }
        result.add(this)
        rightChild?.let { result.addAll(it.traverse()) }

        return result
    }

    override fun setValue(newValue: V): V {
        val oldValue = value

        value = newValue

        return oldValue
    }

    override fun toString(): String {
        return key.toString() + " -> " + value.toString()
    }

    private fun updateHeight() {
        val leftHeight = leftChild?.height ?: 0
        val rightHeight = rightChild?.height ?: 0

        height = max(leftHeight, rightHeight) + 1
    }

    private fun rotateRight(): AVLNode<K, V> {
        val node = leftChild ?: throw IllegalArgumentException("impossible to make right rotation")

        leftChild = node.rightChild
        node.rightChild = this
        updateHeight()
        node.updateHeight()

        return node
    }

    private fun rotateLeft(): AVLNode<K, V> {
        val node = rightChild ?: throw IllegalArgumentException("impossible to make left rotation")

        rightChild = node.leftChild
        node.leftChild = this
        updateHeight()
        node.updateHeight()

        return node
    }

    companion object {
        const val LEFT_ROTATION_CASE = 2
        const val RIGHT_ROTATION_CASE = -2
    }
}
