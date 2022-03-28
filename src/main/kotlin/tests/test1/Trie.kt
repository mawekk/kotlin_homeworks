package tests.test1

class Vertex(
    val symbol: Char?,
    var next: Vertex?,
    var isTerminal: Boolean
)

class Trie {
    var root: Vertex = Vertex(null, null, false)

    fun add(element: String) {
        var lastVertex: Vertex = root
        var i: Int = 0
        while (i < element.length) {
            if (lastVertex.symbol == element[i]){
                lastVertex.isTerminal = (i == element.length - 1)
                lastVertex = lastVertex.next ?: break
                i++
            }
        }
        while (i < element.length) {
            val newVertex = Vertex(element[i], null, i == (element.length - 1))
            lastVertex.next = newVertex
            lastVertex = newVertex
            i++
        }
    }
}