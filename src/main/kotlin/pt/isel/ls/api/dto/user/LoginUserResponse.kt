package pt.isel.ls.api.dto.user

import kotlinx.serialization.Serializable

@Serializable
data class LoginUserResponse(val id: Int, val token: String, val name: String)
