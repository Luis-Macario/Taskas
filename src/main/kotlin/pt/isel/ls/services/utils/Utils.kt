package pt.isel.ls.services.utils

import pt.isel.ls.services.utils.exceptions.InvalidTokenException

const val MAX_DESCRIPTION_LENGTH = 1000
const val MIN_DESCRIPTION_LENGTH = 1

const val MAX_DATE = "9999-12-31"

const val TOKEN_REGEX = "[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[1-5][a-fA-F0-9]{3}-[89aAbB][a-fA-F0-9]{3}-[a-fA-F0-9]{12}$"

/**
 * Checks whether an id is valid or not
 *
 * @param id Int with id to check
 *
 * @return true if valid, false if not
 */
fun validId(id: Int): Boolean = id >= 0

/**
 * Checks whether descritpion is valid
 *
 * @param description String with description to check
 *
 * @return true if valid, false if not
 */
fun validDescription(description: String): Boolean =
    description.length in MIN_DESCRIPTION_LENGTH..MAX_DESCRIPTION_LENGTH

/**
 * Parses Token
 *
 * @param token String with the Token
 *
 * @throws InvalidTokenException if the token parameter isn't a valid Token
 */
fun checkToken(token: String) {
    if (!token.matches(TOKEN_REGEX.toRegex())) throw InvalidTokenException
}
