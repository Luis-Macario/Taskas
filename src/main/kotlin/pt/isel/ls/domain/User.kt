package pt.isel.ls.domain

import pt.isel.ls.services.utils.validId
import java.nio.charset.StandardCharsets
import java.security.MessageDigest

/**
 * User representation
 *
 * @property id user's unique identifier
 * @property name name of the user
 * @property email email of the user
 * @property token bearer token of the user
 * @property password the users password (encoded to sha265)
 */
data class User(
    val id: Int,
    val name: String,
    val email: String,
    val token: String,
    val password: String
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

fun hashPassword(password: String): String {
    val messageDigest = MessageDigest.getInstance("SHA-256")
    val hashedBytes = messageDigest.digest(password.toByteArray(StandardCharsets.UTF_8))
    return bytesToHex(hashedBytes)
}

fun bytesToHex(bytes: ByteArray): String {
    val hexChars = "0123456789ABCDEF"
    val hex = StringBuilder(2 * bytes.size)
    for (i in bytes.indices) {
        val b = bytes[i].toInt() and 0xFF
        hex.append(hexChars[b.ushr(4)])
        hex.append(hexChars[b and 0x0F])
    }
    return hex.toString()
}

fun checkUserCredentials(name: String, email: String) {
    require(User.validName(name)) { "Invalid username: $name" }
    require(User.validEmail(email)) { "Invalid email: $email" }
}
