package pt.isel.ls.api.dto.user

import kotlinx.serialization.Serializable

@Serializable
data class UserDTO(val id: Int, val name: String, val email: String)
