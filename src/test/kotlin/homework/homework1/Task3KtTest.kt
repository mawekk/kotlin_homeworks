package homework.homework1

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class Task3KtTest {
    @ParameterizedTest(name = "case {index}: {2} {1}")
    @MethodSource("inputForAdd")
    fun `add test`(command: String, expected: List<Int>) {
        val storage = PerformedCommandStorage()
        val add = wrapAction(command.split(" "))
        storage.applyAction(add)
        assertEquals(expected, storage.returnList())
    }

    @ParameterizedTest(name = "case {index}: {2} {1}")
    @MethodSource("inputForAppend")
    fun `append test`(command: String, expected: List<Int>) {
        val storage = PerformedCommandStorage()
        val append = wrapAction(command.split(" "))
        storage.applyAction(append)
        assertEquals(expected, storage.returnList())
    }

    @ParameterizedTest(name = "case {index}: {2} {1}")
    @MethodSource("inputForMove")
    fun `move test`(command: String, expected: List<Int>) {
        val storage = PerformedCommandStorage()
        storage.applyAction(AddAction(listOf(3)))
        storage.applyAction(AddAction(listOf(2)))
        storage.applyAction(AddAction(listOf(1)))
        val move = wrapAction(command.split(" "))
        storage.applyAction(move)
        assertEquals(expected, storage.returnList())
    }

    @ParameterizedTest(name = "case {index}: {2} {1}")
    @MethodSource("inputForWrap")
    fun `wrap test`(command: String, expected: ActionType) {
        val action = wrapAction(command.split(" "))
        assertEquals(expected, action.type)
    }

    @ParameterizedTest(name = "case {index}: {2} {1}")
    @MethodSource("inputForUndo")
    fun `undo test`(lastAction: String, expected: List<Int>) {
        val storage = PerformedCommandStorage()
        storage.applyAction(AddAction(listOf(3)))
        storage.applyAction(AddAction(listOf(2)))
        storage.applyAction(AddAction(listOf(1)))
        storage.applyAction(wrapAction(lastAction.split(" ")))
        storage.undoAction()
        assertEquals(expected, storage.returnList())
    }

    @Test
    fun `unknown command`() {
        val exception = assertThrows<IllegalArgumentException> { wrapAction(listOf("meow")) }
        assertEquals("unknown command", exception.message)
    }

    @Test
    fun `wrong argument`() {
        val exception = assertThrows<IllegalArgumentException> { wrapAction(listOf("add", "number")) }
        assertEquals("wrong argument", exception.message)
    }

    @Test
    fun `move exception`() {
        val storage = PerformedCommandStorage()
        val exception = assertThrows<IllegalArgumentException> {
            storage.applyAction(
                (MoveAction(listOf(1, 2)))
            )
        }
        assertEquals("this position does not exist", exception.message)
    }

    @Test
    fun `undo exception`() {
        val storage = PerformedCommandStorage()
        val exception = assertThrows<IllegalArgumentException> { storage.undoAction() }
        assertEquals("nothing to undo", exception.message)
    }

    companion object {
        @JvmStatic
        fun inputForAdd() = listOf(
            Arguments.of("add 3", listOf(3)),
            Arguments.of("add 2", listOf(2)),
            Arguments.of("add 1", listOf(1)),
        )

        @JvmStatic
        fun inputForAppend() = listOf(
            Arguments.of("append 3", listOf(3)),
            Arguments.of("append 2", listOf(2)),
            Arguments.of("append 1", listOf(1)),
        )

        @JvmStatic
        fun inputForMove() = listOf(
            Arguments.of("move 0 1", listOf(2, 1, 3)),
            Arguments.of("move 1 2", listOf(1, 3, 2)),
            Arguments.of("move 0 2", listOf(2, 3, 1)),
        )

        @JvmStatic
        fun inputForWrap() = listOf(
            Arguments.of("add 1", ActionType.ADD),
            Arguments.of("append 2", ActionType.APPEND),
            Arguments.of("move 3 4", ActionType.MOVE),
        )

        @JvmStatic
        fun inputForUndo() = listOf(
            Arguments.of("add 1", listOf(1, 2, 3)),
            Arguments.of("append 2", listOf(1, 2, 3)),
            Arguments.of("move 0 1", listOf(1, 2, 3)),
        )
    }
}
