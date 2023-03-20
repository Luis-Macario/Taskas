package pt.isel.ls.utils

const val MAX_DESCRIPTION_LENGTH = 1000
const val MIN_DESCRIPTION_LENGTH = 0

const val MAX_DATE = "9999-12-31"

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
