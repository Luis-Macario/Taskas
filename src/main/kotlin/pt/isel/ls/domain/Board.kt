package pt.isel.ls.domain

import pt.isel.ls.services.utils.validDescription
import pt.isel.ls.services.utils.validId

/**
 * Board representation
 *
 * @property id board's unique identifier
 * @property name project's name
 * @property description a small description of the project, empty string by default
 * @property lists List of the simpleLists belonging to the board
 *
 */
data class Board(
    val id: Int,
    val name: String,
    val description: String,
    var lists: List<SimpleList>
) {
    companion object {
        const val MAX_NAME_LENGTH = 100
        const val MIN_NAME_LENGTH = 5
        const val NAME_REGEX = "^[a-zA-Z0-9]+(?: [a-zA-Z0-9]+)*\$"

        /**
         * Checks whether a board name is valid or not
         *
         * @param name String with name of the user
         *
         * @return true if the name is valid, false if not
         */
        fun validName(name: String): Boolean =
            ((name.length in MIN_NAME_LENGTH..MAX_NAME_LENGTH) && name.matches(NAME_REGEX.toRegex()))
    }

    init {
        require(validName(name)) { "Invalid board name: $name" }
        require(validDescription(description)) { "Invalid board description: $description" }
        require(validId(id)) { "Invalid board id: $id" }
    }
}

fun checkBoardCredentials(name: String, description: String) {
    require(Board.validName(name)) { "Invalid board name: $name" }
    require(validDescription(description)) { "Invalid board description: $description" }
}
