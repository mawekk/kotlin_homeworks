package homework.homework1

class Action(val name: String, val firstParameter: Int?, val secondParameter: Int?)

class PerformedCommandStorage {
    private var storage: MutableList<Action> = mutableListOf()
    private var userList: MutableList<Int> = mutableListOf()

    fun newAction(action: Action): List<Int> {
        when (action.name) {
            "add" -> add(action.firstParameter)
            "append" -> append(action.firstParameter)
            "swap" -> swap(action.firstParameter, action.secondParameter)
            "undo" -> undo()
            else -> throw IllegalArgumentException("unknown command")
        }
        if (action.name != "undo") {
            storage.add(action)
        }
        return userList
    }

    private fun add(element: Int?) {
        if (element == null) {
            throw IllegalArgumentException("wrong argument")
        }
        userList.add(0, element)
    }

    private fun append(element: Int?) {
        if (element == null) {
            throw IllegalArgumentException("wrong argument")
        }
        userList.add(element)
    }

    private fun swap(first: Int?, second: Int?) {
        if ((first == null) or (second == null)) {
            throw IllegalArgumentException("wrong argument")
        }
        if ((second!! >= userList.size) or (first!! < 0) or (second < 0)) {
            throw IllegalArgumentException("this position does not exist")
        }
        val buffer = userList[first]
        userList[first] = userList[second]
        userList[second] = buffer
    }

    private fun undo() {
        val lastAction = storage.lastOrNull() ?: throw IllegalArgumentException("nothing to undo")
        when (lastAction.name) {
            "add" -> userList.removeFirst()
            "append" -> userList.removeLast()
            "swap" -> swap(lastAction.firstParameter, lastAction.secondParameter)
        }
        storage.removeLast()
    }
}
