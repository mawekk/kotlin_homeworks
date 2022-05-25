package tests.test1

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class VectorTest {
    @Test
    fun `plus test`() {
        val first = Vector(
            listOf(IntVectorElements(1), IntVectorElements(2), IntVectorElements(3))
        )
        val second = Vector(
            listOf(IntVectorElements(3), IntVectorElements(2), IntVectorElements(1))
        )
        val expected =
            Vector(
                listOf(IntVectorElements(4), IntVectorElements(4), IntVectorElements(4))
            )
        assertEquals(expected.toString(), (first + second).toString())
    }

    @Test
    fun `minus test`() {
        val first = Vector(
            listOf(IntVectorElements(1), IntVectorElements(2), IntVectorElements(3))
        )
        val second = Vector(
            listOf(IntVectorElements(3), IntVectorElements(2), IntVectorElements(1))
        )
        val expected =
            Vector(
                listOf(IntVectorElements(-2), IntVectorElements(0), IntVectorElements(2))
            )
        assertEquals(expected.toString(), (first - second).toString())
    }

    @Test
    fun `times test`() {
        val first = Vector(
            listOf(IntVectorElements(1), IntVectorElements(2), IntVectorElements(3))
        )
        val second = Vector(
            listOf(IntVectorElements(3), IntVectorElements(2), IntVectorElements(1))
        )
        val expected = "10"
        assertEquals(expected, (first * second).toString())
    }

    @ParameterizedTest
    @MethodSource("zeroTestData")
    fun `zero test`(vector: Vector<IntVectorElements>, expected: Boolean) {
        assertEquals(expected, vector.isZeroVector)
    }

    @Test
    fun `length test`() {
        val first = Vector(
            listOf(IntVectorElements(1), IntVectorElements(2), IntVectorElements(3))
        )
        val second = Vector(
            listOf(IntVectorElements(3), IntVectorElements(1))
        )
        val exception = assertThrows<IllegalArgumentException> { first + second }
        assertEquals("Vectors must have equal length.", exception.message)
    }

    companion object {
        @JvmStatic
        fun zeroTestData() = listOf(
            Arguments.of(
                Vector(listOf(IntVectorElements(1), IntVectorElements(2), IntVectorElements(3))),
                false
            ),
            Arguments.of(
                Vector(listOf(IntVectorElements(0), IntVectorElements(0), IntVectorElements(0))),
                true
            )
        )
    }
}
