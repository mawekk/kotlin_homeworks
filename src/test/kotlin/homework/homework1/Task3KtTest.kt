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
        assertEquals(expected, storage.newAction(add))
    }

    @ParameterizedTest(name = "case {index}: {2} {1}")
    @MethodSource("inputForAppend")
    fun `append test`(command: String, expected: List<Int>) {
        val storage = PerformedCommandStorage()
        val append = wrapAction(command.split(" "))
        assertEquals(expected, storage.newAction(append))
    }

    @ParameterizedTest(name = "case {index}: {2} {1}")
    @MethodSource("inputForMove")
    fun `move test`(command: String, expected: List<Int>) {
        val storage = PerformedCommandStorage()
        storage.newAction(Action(ActionType.ADD, listOf(3)))
        storage.newAction(Action(ActionType.ADD, listOf(2)))
        storage.newAction(Action(ActionType.ADD, listOf(1)))
        val move = wrapAction(command.split(" "))
        assertEquals(expected, storage.newAction(move))
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
        storage.newAction(Action(ActionType.ADD, listOf(3)))
        storage.newAction(Action(ActionType.ADD, listOf(2)))
        storage.newAction(Action(ActionType.ADD, listOf(1)))
        storage.newAction(wrapAction(lastAction.split(" ")))
        assertEquals(expected, storage.newAction(Action(ActionType.UNDO, emptyList<Int>())))
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
            storage.newAction(
                Action(
                    ActionType.MOVE, listOf(1, 2)
                )
            )
        }
        assertEquals("this position does not exist", exception.message)
    }

    @Test
    fun `undo exception`() {
        val storage = PerformedCommandStorage()
        val exception = assertThrows<IllegalArgumentException> {
            storage.newAction(
                Action(
                    ActionType.UNDO, emptyList<Int>()
                )
            )
        }
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
            Arguments.of("undo", ActionType.UNDO),
            Arguments.of("print", ActionType.PRINT),
        )

        @JvmStatic
        fun inputForUndo() = listOf(
            Arguments.of("add 1", listOf(1, 2, 3)),
            Arguments.of("append 2", listOf(1, 2, 3)),
            Arguments.of("move 0 1", listOf(1, 2, 3)),
            Arguments.of("undo", listOf(3)),
            Arguments.of("print", listOf(2, 3)),
        )
    }
}
