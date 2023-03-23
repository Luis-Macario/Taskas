package pt.isel.ls.api.dto.board

import kotlinx.serialization.Serializable

@Serializable
data class AddUserRequest(
    val userID: Int
)
