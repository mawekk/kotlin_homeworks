package tests.test1

interface ArithmeticAvailable<T : ArithmeticAvailable<T>> {
    fun isZero(): Boolean
    operator fun plus(other: T): T
    operator fun minus(other: T): T
    operator fun times(other: T): T
}

class IntVectorElements(val element: Int) : ArithmeticAvailable<IntVectorElements> {
    override fun isZero(): Boolean = element == 0
    override fun plus(other: IntVectorElements) = IntVectorElements(element + other.element)
    override fun minus(other: IntVectorElements) = IntVectorElements(element - other.element)
    override fun times(other: IntVectorElements) = IntVectorElements(element * other.element)
    override fun toString() = "$element"
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
        return newVector.reduce { acc, element -> acc + element }
    }

    val isZeroVector: Boolean
        get() = elements.all { it.element.isZero()}

}
