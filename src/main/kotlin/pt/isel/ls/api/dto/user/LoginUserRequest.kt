package pt.isel.ls.api.dto.user

import kotlinx.serialization.Serializable

@Serializable
data class LoginUserRequest(val email: String, val password: String)
