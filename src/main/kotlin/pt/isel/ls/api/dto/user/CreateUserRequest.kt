package pt.isel.ls.api.dto.user

import kotlinx.serialization.Serializable

@Serializable
data class CreateUserRequest(val name: String, val email: String, val password: String)
