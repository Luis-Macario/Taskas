package pt.isel.ls.domain

import pt.isel.ls.services.utils.validId

/**
 * User - Board representation
 *
 * @property uId id of the User
 * @property bId id of the Board
 */
data class UserBoard(
    val uId: Int,
    val bId: Int
) {
    init {
        require(validId(uId)) { "Invalid userid: $uId" }
        require(validId(uId)) { "Invalid board id: $bId" }
    }
}
