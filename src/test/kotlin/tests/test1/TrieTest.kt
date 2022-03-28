package tests.test1

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class TrieTest {
    @Test
    fun `add test true`() {
        val trie = Trie()
        assertEquals(true, trie.add("sobaka"))
    }

    @Test
    fun `add test false`() {
        val trie = Trie()
        trie.add("sobaka")
        assertEquals(false, trie.add("sobaka"))
    }

    @Test
    fun `contains test true`() {
        val trie = Trie()
        trie.add("sobaka")
        assertEquals(true, trie.contains("sobaka"))
    }

    @Test
    fun `contains test false`() {
        val trie = Trie()
        assertEquals(false, trie.contains("sobaka"))
    }

    @Test
    fun `remove test false`() {
        val trie = Trie()
        assertEquals(false, trie.remove("sobaka"))
    }

    @Test
    fun `remove test true`() {
        val trie = Trie()
        trie.add("sobaka")
        assertEquals(true, trie.contains("sobaka"))
    }

    @Test
    fun `size test`() {
        val trie = Trie()
        trie.add("sobaka")
        trie.add("babaka")
        assertEquals(2, trie.size())
    }

    @Test
    fun `prefix test`() {
        val trie = Trie()
        trie.add("kot")
        trie.add("kotenok")
        trie.add("kotenka")
        trie.add("kotya")
        assertEquals(3, trie.howManyStartWithPrefix("kot"))
    }
}
