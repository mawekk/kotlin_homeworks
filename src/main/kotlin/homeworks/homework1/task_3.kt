package homeworks.homework1

fun main() {
    val greeting = """
        Hi!! Here are the commands that are available to you:
        • add x – add an element x to the top of the list
        • append x – add an element x to the end of the list
        • swap i j  – move an element from i to j position
        • undo – cancel the last action 
        • print – print list
        • exit – bye :("""
        .trimIndent()
    println(greeting)
    val storage = PerformedCommandStorage()
    while (true) {
        print("Your command: ")
        val command: List<String> = readln().split(" ")
        when (command[0]) {
            "exit" -> break
            "print" -> println("Result: ${storage.list.joinToString(separator = " ")}")
            "undo" -> {
                try {
                    storage.undoAction()
                    println("Result: ${storage.list.joinToString(separator = " ")}")
                } catch (exception: IllegalArgumentException) {
                    println("Failed: ${exception.message}")
                }
            }
            else -> {
                try {
                    val action = wrapAction(command)
                    storage.applyAction(action)
                    println("Result: ${storage.list.joinToString(separator = " ")}")
                } catch (exception: IllegalArgumentException) {
                    println("Failed: ${exception.message}")
                }
            }
        }
    }
}
