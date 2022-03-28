package tests.test1

class Vertex(
    val symbol: Char?,
    var isTerminal: Boolean,
    var next: MutableList<Vertex?> = mutableListOf()
)

class Trie {
    private var root: Vertex = Vertex(null, false)
    private var numberOfStrings = 0

    fun add(element: String): Boolean {
        var lastVertex: Vertex = root
        var i = 0

        while (i < element.length) {
            val vertex = lastVertex.next.find { it?.symbol == element[i] }
            if (vertex != null) {
                lastVertex = vertex
                if (i == element.length - 1 && lastVertex.isTerminal) return false
                if (i == element.length - 1) lastVertex.isTerminal = true
            } else {
                val newVertex = Vertex(element[i], i == (element.length - 1))
                lastVertex.next.add(newVertex)
                lastVertex = newVertex
            }
            i++
        }
        numberOfStrings++
        return true
    }

    private fun find(element: String): Vertex? {
        var current = root
        var i = 0

        while (i < element.length) {
            val vertex = current.next.find { it?.symbol == element[i] }
            if (vertex != null) {
                current = vertex
                i++
            } else return null
        }

        return current
    }

    fun contains(element: String): Boolean {
        if (find(element) == null)
            return false
        return true
    }

    fun remove(element: String): Boolean {
        val vertex = find(element) ?: return false
        vertex.isTerminal = false
        numberOfStrings--
        return true
    }

    fun size(): Int {
        return numberOfStrings
    }

    private fun countTerminal(start: Vertex, number: Int): Int {
        var count = number
        for (current in start.next) {
            if (current != null) {
                if (current?.isTerminal == true)
                    count++
                count = countTerminal(current, count)
            }
        }
        return count
    }

    fun howManyStartWithPrefix(prefix: String): Int {
        val vertex = find(prefix) ?: return 0
        return countTerminal(vertex, 0)
    }
}
