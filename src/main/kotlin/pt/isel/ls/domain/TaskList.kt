package pt.isel.ls.domain

import pt.isel.ls.utils.validId

/**
 * List representation
 *
 * @property id List's unique identifier
 * @property bid id of the Board that created the list
 * @property name List's unique identifier
 */
data class TaskList(
    val id: Int,
    val bid: Int,
    val name: String
) {
    companion object {
        const val MAX_NAME_LENGTH = 100
        const val MIN_NAME_LENGTH = 4

        /**
         * Checks whether a task name is valid or not
         *
         * @param name String with name of the user
         *
         * @return true if the name is valid, false if not
         */
        fun validName(name: String): Boolean = (name.length in MIN_NAME_LENGTH..MAX_NAME_LENGTH)
    }

    init {
        require(validId(id)) { "Invalid task id: $id" }
        require(validId(bid)) { "Invalid board id: $bid" }
        require(validName(name)) { "Invalid task name: $name" }
    }
}
