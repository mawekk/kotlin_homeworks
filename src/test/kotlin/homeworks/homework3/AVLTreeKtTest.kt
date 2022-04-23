package homeworks.homework3

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class AVLTreeKtTest {
    @Test
    fun `put test`() {
        val tree = AVLTree<Int, Int>()
        tree.put(1, 2)
        assertEquals(mutableMapOf(1 to 2).entries, tree.entries)
    }

    @Test
    fun `putAll test`() {
        val tree = AVLTree<Int, Int>()
        val map = mapOf(1 to 10, 2 to 20, 3 to 30)
        tree.putAll(map)
        assertEquals(mutableMapOf(1 to 10, 2 to 20, 3 to 30).entries, tree.entries)
    }

    @Test
    fun `contains test`() {
        val tree = AVLTree<Int, Int>()
        tree.put(1, 2)
        assertEquals(true, tree.containsKey(1))
        assertEquals(true, tree.containsValue(2))
        assertEquals(false, tree.containsKey(2))
        assertEquals(false, tree.containsValue(1))
    }

    @Test
    fun `get test`() {
        val tree = AVLTree<Int, Int>()
        tree.put(1, 2)
        assertEquals(2, tree.get(1))
        assertEquals(null, tree.get(2))
    }

    @Test
    fun `isEmpty test`() {
        val tree = AVLTree<Int, Int>()
        assertEquals(true, tree.isEmpty())
        tree.put(1, 2)
        assertEquals(false, tree.isEmpty())
    }

    @Test
    fun `clear test`() {
        val tree = AVLTree<Int, Int>()
        tree.put(1, 2)
        assertEquals(mutableMapOf(1 to 2).entries, tree.entries)
        tree.clear()
        assertEquals(emptyMap<Int, Int>().entries, tree.entries)
    }

    @Test
    fun `remove test`() {
        val tree = AVLTree<Int, Int>()
        val map = mapOf(1 to 10, 2 to 20, 3 to 30)
        tree.putAll(map)
        tree.remove(2)
        assertEquals(mutableMapOf(1 to 10, 3 to 30).entries, tree.entries)
    }
}
