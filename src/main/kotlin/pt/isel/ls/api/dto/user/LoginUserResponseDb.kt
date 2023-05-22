package pt.isel.ls.api.dto.user

import pt.isel.ls.domain.User

data class LoginUserResponseDb(val user: User, val password: String)
