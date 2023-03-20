package pt.isel.ls.domain

import pt.isel.ls.utils.validId

/**
 * User representation
 *
 * @property id user's unique identifier
 * @property name name of the user
 * @property email email of the user
 * @property token bearer token of the user
 */
data class User(
    val id: Int,
    val name: String,
    val email: String,
    val token: String
) {
    companion object {
        private const val EMAIL_REGEX = "^(.+)@(.+)$"
        const val MAX_NAME_LENGTH = 50
        const val MIN_NAME_LENGTH = 3

        /**
         * Checks whether an email is valid or not
         *
         * @param email String with email to check
         *
         * @return true if valid, false if not
         */
        fun validEmail(email: String): Boolean = email.matches(EMAIL_REGEX.toRegex())

        /**
         * Checks whether a username is valid or not
         *
         * @param name String with name of the user
         *
         * @return true if the name is valid, false if not
         */
        fun validName(name: String): Boolean = (name.length in MIN_NAME_LENGTH..MAX_NAME_LENGTH)
    }

    init {
        require(validName(name)) { "Invalid username: $name" }
        require(validEmail(email)) { "Invalid email: $email" }
        require(validId(id)) { "Invalid user id: $id" }
    }
}
