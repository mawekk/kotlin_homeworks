package tests.test1

fun main() {
    val greeting = """
        Hi!! Here are the commands that are available to you:
        • add x – add an element x 
        • contains x – check if trie contains x
        • remove x - remove x
        • size - return size of trie
        • prefix x – return count of strings start with prefix
        • exit – bye :("""
        .trimIndent()
    println(greeting)
    val trie = Trie()
    while (true) {
        print("Your command: ")
        val command: List<String> = readln().split(" ")
        when (command[0]) {
            "exit" -> break
            "size" -> println(trie.size())
            "add" -> println(trie.add(command[1]))
            "contains" -> println(trie.contains(command[1]))
            "remove" -> println(trie.remove(command[1]))
            "prefix" -> println(trie.howManyStartWithPrefix(command[1]))
            else -> println("unknown command")
        }
    }
}
