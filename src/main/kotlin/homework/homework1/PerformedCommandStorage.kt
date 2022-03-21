package homework.homework1

class PerformedCommandStorage {
    private val storage: MutableList<Action> = mutableListOf()
    private val userList: MutableList<Int> = mutableListOf()

    fun newAction(action: Action) {
        if (action.type != ActionType.UNDO) {
            action.doAction(userList)
            storage.add(action)
        } else {
            val lastAction = storage.lastOrNull() ?: throw IllegalArgumentException("nothing to undo")
            lastAction.undoAction(userList)
            storage.removeLast()
        }
    }

    fun returnList(): List<Int> {
        return userList
    }
}
