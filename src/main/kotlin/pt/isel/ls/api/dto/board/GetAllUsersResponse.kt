package pt.isel.ls.api.dto.board

import kotlinx.serialization.Serializable
import pt.isel.ls.api.dto.user.UserDTO

@Serializable
data class GetAllUsersResponse(val users: List<UserDTO>)
