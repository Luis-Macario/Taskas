package pt.isel.ls.domain

import pt.isel.ls.utils.validDescription
import pt.isel.ls.utils.validId


/**
 * Board representation
 *
 * @property id board's unique identifier
 * @property description a small description of the project, empty string by default
 * @property name project's name
 */
data class Board(
    val id: Int,
    val uid: Int,
    val name: String,
    val description: String = "",
) {
    companion object {
        private const val MAX_NAME_LENGTH = 100
        private const val MIN_NAME_LENGTH = 20

        /**
         * Checks whether a board name is valid or not
         *
         * @param name String with name of the user
         *
         * @return true if the name is valid, false if not
         */
        fun validName(name: String): Boolean = (name.length in MIN_NAME_LENGTH..MAX_NAME_LENGTH)
    }

    init {
        require(validName(name)) { "Invalid board username: $name" }
        require(validDescription(description)) { "Invalid board description: $description" }
        require(validId(id)) { "Invalid board id: $id" }
    }
}
