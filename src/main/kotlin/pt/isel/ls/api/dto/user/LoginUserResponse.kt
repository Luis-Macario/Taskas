package pt.isel.ls.api.dto.user
import pt.isel.ls.domain.User

data class LoginUserResponse(val user: User, val password: String)