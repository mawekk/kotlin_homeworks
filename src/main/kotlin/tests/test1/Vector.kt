package tests.test1

interface ArithmeticAvailable<T> {
    val zero: T
    val element: T
    fun isZero(): Boolean
    operator fun plus(other: T): T
    operator fun minus(other:T): T
    operator fun times(other: T): T

}

class IntVectorElements(override val element: Int) : ArithmeticAvailable<Int> {
    override val zero = 0
    override fun isZero(): Boolean = element == zero
    override fun plus(other: Int): Int = element + other
    override fun minus(other: Int): Int = element - other
    override fun times(other: Int): Int = element * other
}

class Vector<T : ArithmeticAvailable<T>>(val elements: List<T>) {
    val length: Int
        get() = elements.size

    operator fun plus(other: Vector<T>): Vector<T> {
        require(length == other.length) { "Vectors must have equal length." }
        val result = elements.mapIndexed { index, element -> element + other.elements[index] }
        return Vector(result)
    }

    operator fun minus(other: Vector<T>): Vector<T> {
        require(length == other.length) { "Vectors must have equal length." }
        val result = elements.mapIndexed { index, element -> element - other.elements[index] }
        return Vector(result)
    }

    operator fun times(other: Vector<T>): T {
        require(length == other.length) { "Vectors must have equal length." }
        val newVector = elements.mapIndexed { index, element -> element * other.elements[index] }
        var result = elements[0].zero
        newVector.forEach { result += it.element }
        return result
    }

    val isZeroVector: Boolean
        get() = elements.all { it.element.isZero()}

}


