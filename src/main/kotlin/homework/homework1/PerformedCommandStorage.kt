package homework.homework1

enum class ActionType(val actionName: String, val nParameters: Int) {
    ADD("add", 1), APPEND("append", 1), MOVE("move", 2),
    UNDO("undo", 0), PRINT("print", 0)
}

class Action(val type: ActionType, val parameters: List<Int>)

fun wrapAction(command: List<String>): Action {
    val name = command[0]
    val parameters = command.mapNotNull { it.toIntOrNull() }
    var type: ActionType? = null
    ActionType.values().forEach {
        if (it.actionName == name) {
            type = it
        }
    }
    if (type == null)
        throw IllegalArgumentException("unknown command")
    if (type?.nParameters != parameters.size)
        throw IllegalArgumentException("wrong argument")
    return Action(type!!, parameters)

}

class PerformedCommandStorage {
    private var storage: MutableList<Action> = mutableListOf()
    private var userList: MutableList<Int> = mutableListOf()

    fun newAction(action: Action): List<Int> {
        when (action.type.actionName) {
            "add" -> add(action.parameters[0])
            "append" -> append(action.parameters[0])
            "move" -> move(action.parameters[0], action.parameters[1])
            "undo" -> undo()
        }
        if ((action.type != ActionType.UNDO) and (action.type != ActionType.PRINT)) {
            storage.add(action)
        }
        return userList
    }

    private fun add(element: Int) {
        userList.add(0, element)
    }

    private fun append(element: Int) {
        userList.add(element)
    }

    private fun move(first: Int, second: Int) {
        if ((second >= userList.size) or (first >= userList.size) or (first < 0) or (second < 0)) {
            throw IllegalArgumentException("this position does not exist")
        }
        val buffer = userList.removeAt(first)
        userList.add(second, buffer)
    }

    private fun undo() {
        val lastAction = storage.lastOrNull() ?: throw IllegalArgumentException("nothing to undo")
        when (lastAction.type.actionName) {
            "add" -> userList.removeFirst()
            "append" -> userList.removeLast()
            "move" -> move(lastAction.parameters[0], lastAction.parameters[1])
        }
        storage.removeLast()
    }
}
