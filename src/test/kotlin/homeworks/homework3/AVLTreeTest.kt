package homeworks.homework3

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class AVLTreeTest {
    @ParameterizedTest
    @MethodSource("putTestData")
    fun <K : Comparable<K>, V> `put test`(
        tree: AVLTree<K, V>,
        key: K,
        value: V,
        expected: MutableSet<MutableMap.MutableEntry<K, V>>
    ) {
        tree[key] = value
        assertEquals(expected, tree.entries)
    }

    @ParameterizedTest
    @MethodSource("putAllTestData")
    fun <K : Comparable<K>, V> `putAll test`(
        tree: AVLTree<K, V>,
        map: Map<K, V>,
        expected: MutableSet<MutableMap.MutableEntry<K, V>>
    ) {
        tree.putAll(map)
        assertEquals(expected, tree.entries)
    }

    @ParameterizedTest
    @MethodSource("containsTestData")
    fun <K : Comparable<K>, V> `contains test`(
        tree: AVLTree<K, V>,
        key: K,
        value: V,
        expectedKey: Boolean,
        expectedValue: Boolean
    ) {
        assertEquals(expectedKey, tree.containsKey(key))
        assertEquals(expectedValue, tree.containsValue(value))
    }

    @ParameterizedTest
    @MethodSource("getTestData")
    fun <K : Comparable<K>, V> `get test`(tree: AVLTree<K, V>, key: K, expected: V?) =
        assertEquals(expected, tree[key])

    @ParameterizedTest
    @MethodSource("isEmptyTestData")
    fun <K : Comparable<K>, V> `isEmpty test`(tree: AVLTree<K, V>, expected: Boolean) =
        assertEquals(expected, tree.isEmpty())

    @ParameterizedTest
    @MethodSource("clearTestData")
    fun <K : Comparable<K>, V> `clear test`(tree: AVLTree<K, V>) {
        tree.clear()
        assertEquals(emptyMap<Int, Int>().entries, tree.entries)
    }

    @ParameterizedTest
    @MethodSource("removeTestData")
    fun <K : Comparable<K>, V> `remove test`(
        tree: AVLTree<K, V>,
        key: K,
        expected: MutableSet<MutableMap.MutableEntry<K, V>>
    ) {
        tree.remove(key)
        assertEquals(expected, tree.entries)
    }

    @ParameterizedTest
    @MethodSource("keysTestData")
    fun <K : Comparable<K>, V> `keys test`(
        tree: AVLTree<K, V>,
        expected: MutableSet<K>
    ) = assertEquals(expected, tree.keys)

    @ParameterizedTest
    @MethodSource("valuesTestData")
    fun <K : Comparable<K>, V> `values test`(
        tree: AVLTree<K, V>,
        expected: MutableCollection<V>
    ) = assertEquals(expected, tree.values)

    @ParameterizedTest
    @MethodSource("sizeTestData")
    fun <K : Comparable<K>, V> `size test`(
        tree: AVLTree<K, V>,
        expected: Int
    ) = assertEquals(expected, tree.size)

    companion object {
        @JvmStatic
        fun putTestData() = listOf(
            Arguments.of(avlTreeOf<Int, Int>(), 1, 2, mutableMapOf(1 to 2).entries),
            Arguments.of(avlTreeOf<Int, String>(), 1, "meow", mutableMapOf(1 to "meow").entries),
            Arguments.of(avlTreeOf<String, String>(), "kitten", "puppy", mutableMapOf("kitten" to "puppy").entries)
        )

        @JvmStatic
        fun putAllTestData() = listOf(
            Arguments.of(
                avlTreeOf<Int, Int>(),
                mapOf(1 to 2, 2 to 3, 3 to 4),
                mutableMapOf(1 to 2, 2 to 3, 3 to 4).entries
            ),
            Arguments.of(
                avlTreeOf<Int, String>(),
                mapOf(1 to "hi", 12 to "hello", 23 to "sup"),
                mutableMapOf(1 to "hi", 12 to "hello", 23 to "sup").entries
            ),
            Arguments.of(
                avlTreeOf<String, String>(),
                mapOf("every" to "night", "in" to "my", "dreams" to "i", "see" to "you"),
                mutableMapOf("every" to "night", "in" to "my", "dreams" to "i", "see" to "you").entries
            )
        )

        @JvmStatic
        fun containsTestData() = listOf(
            Arguments.of(avlTreeOf(Pair(1, 2), Pair(2, 3), Pair(3, 4)), 2, 4, true, true),
            Arguments.of(avlTreeOf(Pair(1, 2), Pair(2, 3), Pair(3, 4)), 100, 3, false, true),
            Arguments.of(avlTreeOf(Pair("a", 2), Pair("b", 3), Pair("c", 4)), "c", 10, true, false),
            Arguments.of(avlTreeOf(Pair("a", 2), Pair("b", 3), Pair("c", 4)), "d", 11, false, false)
        )

        @JvmStatic
        fun getTestData() = listOf(
            Arguments.of(avlTreeOf(Pair(1, 2)), 1, 2),
            Arguments.of(avlTreeOf(Pair(1, "meow")), 1, "meow"),
            Arguments.of(avlTreeOf(Pair("kitten", "puppy")), "kitten", "puppy")
        )

        @JvmStatic
        fun isEmptyTestData() = listOf(
            Arguments.of(avlTreeOf(Pair(1, 2)), false),
            Arguments.of(avlTreeOf<Int, Any>(), true)
        )

        @JvmStatic
        fun clearTestData() = listOf(
            Arguments.of(avlTreeOf(Pair(1, 2))),
            Arguments.of(avlTreeOf<Int, Any>())
        )

        @JvmStatic
        fun removeTestData() = listOf(
            Arguments.of(avlTreeOf(Pair(1, 2), Pair(2, 3), Pair(3, 4)), 2, mapOf(1 to 2, 3 to 4).entries),
            Arguments.of(avlTreeOf(Pair(1, 2), Pair(2, 3), Pair(3, 4)), 10, mapOf(1 to 2, 2 to 3, 3 to 4).entries),
            Arguments.of(avlTreeOf(Pair("a", 2), Pair("b", 3), Pair("c", 4)), "c", mapOf("a" to 2, "b" to 3).entries),
            Arguments.of(
                avlTreeOf(Pair("a", 2), Pair("b", 3), Pair("c", 4)), "d", mapOf("a" to 2, "b" to 3, "c" to 4).entries
            )
        )

        @JvmStatic
        fun keysTestData() = listOf(
            Arguments.of(avlTreeOf(Pair(1, 2), Pair(2, 3), Pair(3, 4)), mapOf(1 to 2, 2 to 3, 3 to 4).keys),
            Arguments.of(avlTreeOf(Pair(1, 2), Pair(2, 3), Pair(3, 4)), mapOf(1 to 2, 2 to 3, 3 to 4).keys),
            Arguments.of(
                avlTreeOf(Pair("a", 2), Pair("b", 3), Pair("c", 4)), mapOf("a" to 2, "b" to 3, "c" to 4).keys
            ),
            Arguments.of(
                avlTreeOf(Pair("a", 2), Pair("b", 3), Pair("c", 4)), mapOf("a" to 2, "b" to 3, "c" to 4).keys
            )
        )

        @JvmStatic
        fun valuesTestData() = listOf(
            Arguments.of(avlTreeOf(Pair(1, 2), Pair(2, 3), Pair(3, 4)), mapOf(1 to 2, 2 to 3, 3 to 4).values.toList()),
            Arguments.of(avlTreeOf(Pair(1, 2), Pair(2, 3), Pair(3, 4)), mapOf(1 to 2, 2 to 3, 3 to 4).values.toList()),
            Arguments.of(
                avlTreeOf(Pair("a", 2), Pair("b", 3), Pair("c", 4)),
                mapOf("a" to 2, "b" to 3, "c" to 4).values.toList()
            ),
            Arguments.of(
                avlTreeOf(Pair("a", 2), Pair("b", 3), Pair("c", 4)), mapOf("a" to 2, "b" to 3, "c" to 4).values.toList()
            )
        )

        @JvmStatic
        fun sizeTestData() = listOf(
            Arguments.of(avlTreeOf(Pair(1, 2), Pair(2, 3), Pair(3, 4)), 3),
            Arguments.of(avlTreeOf(Pair("a", 2), Pair("b", 3)), 2)
        )
    }
}
