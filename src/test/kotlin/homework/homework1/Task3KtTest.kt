package homework.homework1

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class Task3KtTest {
    @Test
    fun `correct commands`() {
        val storage = PerformedCommandStorage()
        val commands = listOf("add 10", "append 5", "swap 0 1", "undo")
        val results = listOf(listOf(10), listOf(10, 5), listOf(5, 10), listOf(10, 5))
        for (i in 0..3) {
            val command = commands[i].split(" ")
            val name = command[0]
            val first = command.elementAtOrElse(1) { "" }.toIntOrNull()
            val second = command.elementAtOrElse(2) { "" }.toIntOrNull()
            assertEquals(results[i], storage.newAction(Action(name, first, second)))
        }
    }

    @Test
    fun `incorrect commands`() {
        val storage = PerformedCommandStorage()
        val commands = listOf("undo", "append ", "swap 0 1", "add", "hi")
        val exceptions = listOf(
            "nothing to undo", "wrong argument", "this position does not exist", "wrong argument",
            "unknown command"
        )
        for (i in 0..4) {
            val command = commands[i].split(" ")
            val name = command[0]
            val first = command.elementAtOrElse(1) { "" }.toIntOrNull()
            val second = command.elementAtOrElse(2) { "" }.toIntOrNull()
            val exception = assertThrows<IllegalArgumentException> { storage.newAction(Action(name, first, second)) }
            assertEquals(exceptions[i], exception.message)
        }
    }
}
