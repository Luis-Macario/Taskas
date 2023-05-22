package pt.isel.ls.api.dto.user
import pt.isel.ls.domain.User

data class LoginUserRequest(val email: String, val password: String)