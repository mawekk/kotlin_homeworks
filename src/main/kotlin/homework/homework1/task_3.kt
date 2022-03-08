package homework.homework1

fun main() {
    val greeting = """
        Hi!! Here are the commands that are available to you:
        • add x – add an element x to the top of the list
        • append x – add an element x to the end of the list
        • swap i j  – move an element from i to j position
        • undo – cancel the last action 
        • exit – bye :("""
    println(greeting)
    var command: List<String>
    val storage = PerformedCommandStorage()
    while (true) {
        print("Your command: ")
        command = readln().split(" ")
        if (command[0] == "exit") break
        val name = command[0]
        val first = command.elementAtOrElse(1) { "" }.toIntOrNull()
        val second = command.elementAtOrElse(2) { "" }.toIntOrNull()
        try {
            println("Result: ${storage.newAction(Action(name, first, second)).joinToString(separator = " ")}")
        } catch (exception: IllegalArgumentException) {
            println("Failed: ${exception.message}")
        }
    }
}
