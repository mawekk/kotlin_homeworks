package homeworks.homework1

enum class ActionType(val actionName: String, val nParameters: Int) {
    ADD("add", 1), APPEND("append", 1), MOVE("move", 2)
}

open class Action(val type: ActionType) {
    open fun doAction(list: MutableList<Int>) {}
    open fun undoAction(list: MutableList<Int>) {}
}

class AddAction(val parameters: List<Int>) : Action(ActionType.ADD) {
    override fun doAction(list: MutableList<Int>) {
        list.add(0, parameters[0])
    }

    override fun undoAction(list: MutableList<Int>) {
        list.removeFirst()
    }
}

class AppendAction(val parameters: List<Int>) : Action(ActionType.APPEND) {
    override fun doAction(list: MutableList<Int>) {
        list.add(parameters[0])
    }

    override fun undoAction(list: MutableList<Int>) {
        list.removeLast()
    }
}

class MoveAction(parameters: List<Int>) : Action(ActionType.MOVE) {
    private val first = parameters[0]
    private val second = parameters[1]
    override fun doAction(list: MutableList<Int>) {
        if ((second >= list.size) or (first >= list.size) or (first < 0) or (second < 0)) {
            throw IllegalArgumentException("this position does not exist")
        }
        val buffer = list.removeAt(first)
        list.add(second, buffer)
    }

    override fun undoAction(list: MutableList<Int>) {
        val buffer = list.removeAt(second)
        list.add(first, buffer)
    }
}

fun wrapAction(command: List<String>): Action {
    val name = command[0]
    val parameters = command.mapNotNull { it.toIntOrNull() }
    val type = ActionType.values().find { it.actionName == name }
    if ((type != null)) {
        require((type.nParameters == parameters.size)) { throw IllegalArgumentException("wrong argument") }
    }
    return when (type?.actionName) {
        "add" -> AddAction(parameters)
        "append" -> AppendAction(parameters)
        "move" -> MoveAction(parameters)
        else -> throw IllegalArgumentException("unknown command")
    }
}
