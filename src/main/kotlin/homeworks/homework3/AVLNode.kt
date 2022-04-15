package homeworks.homework3

import kotlin.math.max

class AVLNode<K : Comparable<K>, V>(key: K, value: V) : MutableMap.MutableEntry<K, V> {

    companion object {
        const val leftRotationCase = 2
        const val rightRotationCase = -2
    }

    var leftChild: AVLNode<K, V>? = null
    var rightChild: AVLNode<K, V>? = null
    var height = 0

    override var key: K = key
    override var value: V = value

    override fun setValue(newValue: V): V {
        val oldValue = value
        value = newValue
        return oldValue
    }

    fun getBalanceFactor(): Int {
        val leftHeight = leftChild?.height ?: 0
        val rightHeight = rightChild?.height ?: 0
        return rightHeight - leftHeight
    }

    fun updateHeight() {
        val leftHeight = leftChild?.height ?: 0
        val rightHeight = rightChild?.height ?: 0
        height = max(leftHeight, rightHeight) + 1
    }

    fun rotateRight(): AVLNode<K, V> {
        val node = leftChild ?: throw IllegalArgumentException("Impossible to make right rotation")
        leftChild = node.rightChild
        node.rightChild = this
        updateHeight()
        node.updateHeight()
        return node
    }

    fun rotateLeft(): AVLNode<K, V> {
        val node = rightChild ?: throw IllegalArgumentException("Impossible to make left rotation")
        rightChild = node.leftChild
        node.rightChild = this
        node.updateHeight()
        updateHeight()
        return node
    }

    fun balance(): AVLNode<K, V> {
        updateHeight()
        return when (getBalanceFactor()) {
            leftRotationCase -> {
                if (rightChild!!.getBalanceFactor() < 0)
                    rightChild = rightChild!!.rotateRight()
                rotateLeft()
            }
            rightRotationCase -> {
                if (leftChild!!.getBalanceFactor() > 0)
                    leftChild = leftChild!!.rotateLeft()
                rotateRight()
            }
            else -> this
        }
    }
}
