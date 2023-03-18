package pt.isel.ls.services

import kotlinx.serialization.Serializable

@Serializable
data class CreateUserResponse(val token: String, val id: Int)
