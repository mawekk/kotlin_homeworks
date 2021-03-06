package homeworks.homework1

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class Task2Test {
    @ParameterizedTest(name = "case {index}: {2} {1}")
    @MethodSource("inputTestData")
    fun `standard data`(number: Int, expected: List<Int>) {
        assertEquals(expected, sieveOfEratosthenes(number))
    }

    companion object {
        @JvmStatic
        fun inputTestData() = listOf(
            Arguments.of(10, listOf(2, 3, 5, 7)),
            Arguments.of(25, listOf(2, 3, 5, 7, 11, 13, 17, 19, 23)),
            Arguments.of(2, listOf(2)),
            Arguments.of(1, emptyList<Int>())
        )
    }

    @Test
    fun `negative bound`() {
        val exception = assertThrows<IllegalArgumentException> { sieveOfEratosthenes(-1) }
        assertEquals("Bound must be non-negative", exception.message)
    }
}
