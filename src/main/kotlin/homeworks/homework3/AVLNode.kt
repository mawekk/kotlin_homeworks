package homeworks.homework3

import kotlin.math.max

@Suppress("TooManyFunctions")
class AVLNode<K : Comparable<K>, V>(override var key: K, override var value: V) : MutableMap.MutableEntry<K, V> {

    companion object {
        const val leftRotationCase = 2
        const val rightRotationCase = -2
    }

    var leftChild: AVLNode<K, V>? = null
    var rightChild: AVLNode<K, V>? = null
    var height = 1

    override fun setValue(newValue: V): V {
        val oldValue = value

        value = newValue

        return oldValue
    }

    private fun getBalanceFactor(): Int {
        val leftHeight = leftChild?.height ?: 0
        val rightHeight = rightChild?.height ?: 0

        return rightHeight - leftHeight
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

    fun balance(): AVLNode<K, V> {
        updateHeight()
        return when (getBalanceFactor()) {
            leftRotationCase -> {
                val balanceFactor = rightChild?.getBalanceFactor()
                if (balanceFactor != null && balanceFactor < 0)
                    rightChild = rightChild?.rotateRight()
                rotateLeft()
            }
            rightRotationCase -> {
                val balanceFactor = leftChild?.getBalanceFactor()
                if (balanceFactor != null && balanceFactor > 0)
                    leftChild = leftChild?.rotateLeft()
                rotateRight()
            }
            else -> this
        }
    }

    fun findNode(key: K): AVLNode<K, V>? {
        return when {
            key > this.key -> rightChild?.findNode(key)
            key < this.key -> leftChild?.findNode(key)
            else -> this
        }
    }

    private fun findMinNode(): AVLNode<K, V>? {
        return this.leftChild?.findMinNode()
    }

    private fun removeMinNode(): AVLNode<K, V>? {
        if (leftChild == null)
            return rightChild
        leftChild = leftChild?.removeMinNode()
        return balance()
    }

    fun removeNode(key: K): AVLNode<K, V>? {
        when {
            key > this.key -> rightChild?.findNode(key)
            key < this.key -> leftChild?.findNode(key)
            else -> {
                return if (rightChild == null)
                    leftChild
                else {
                    val minimum = rightChild?.findMinNode()
                    minimum?.rightChild = rightChild?.removeMinNode()
                    minimum?.leftChild = leftChild
                    minimum?.balance()
                }
            }
        }
        return balance()
    }

    override fun toString(): String {
        return key.toString() + " -> " + value.toString()
    }
}
