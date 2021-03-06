package homeworks.homework1

class PerformedCommandStorage {
    private val storage: MutableList<Action> = mutableListOf()
    private val userList: MutableList<Int> = mutableListOf()
    val list: List<Int>
        get() = userList

    fun applyAction(action: Action) {
        action.doAction(userList)
        storage.add(action)
    }

    fun undoAction() {
        val lastAction = storage.lastOrNull() ?: throw IllegalArgumentException("nothing to undo")
        lastAction.undoAction(userList)
        storage.removeLast()
    }
}
