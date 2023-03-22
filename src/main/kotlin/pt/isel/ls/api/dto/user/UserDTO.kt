package pt.isel.ls.api.dto.user

import kotlinx.serialization.Serializable
import pt.isel.ls.domain.User

@Serializable
data class UserDTO(val id: Int, val name: String, val email: String)

fun User.toDTO() = UserDTO(id, name, email)
