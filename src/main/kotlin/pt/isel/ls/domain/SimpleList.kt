package pt.isel.ls.domain

/**
 * Simple list representation
 *
 * @property id List's unique identifier
 * @property bid Id of the board containing this list
 * @property name List's name
 */
data class SimpleList(
    val id: Int,
    val bid: Int,
    val name: String
)
