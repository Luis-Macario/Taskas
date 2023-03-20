package pt.isel.ls.api.routers.users

import kotlinx.serialization.Serializable

@Serializable
data class CreateUserRequest(val name: String, val email: String)


