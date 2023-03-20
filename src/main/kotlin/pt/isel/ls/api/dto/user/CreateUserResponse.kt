package pt.isel.ls.api.dto.user

import kotlinx.serialization.Serializable

@Serializable
data class CreateUserResponse(val id: Int, val token: String)
