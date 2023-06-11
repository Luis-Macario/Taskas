package pt.isel.ls.services.utils.exceptions

import pt.isel.ls.domain.TaskException

sealed class ServicesException : TaskException()

object IllegalUserAccessException : ServicesException() {
    override val code = 4000
    override val description = "User doesn't have access to this user"
}

object IllegalBoardAccessException : ServicesException() {
    override val code = 4001
    override val description = "User doesn't have access to this board"
}

object IllegalListAccessException : ServicesException() {
    override val code = 4002
    override val description = "User doesn't have access to this list"
}

object IllegalCardAccessException : ServicesException() {
    override val code = 4003
    override val description = "User doesn't have access to this card"
}

object IllegalMoveCardRequestException : ServicesException() {
    override val code = 4004
    override val description = "Original list and destination list aren't in the same board"
}

object NoSuchBoardException : ServicesException() {
    override val code = 4005
    override val description = "There's no board with that id"
}

object InvalidTokenException : ServicesException() {
    override val code = 4006
    override val description = "User token is not valid"
}

object InvalidUserCredentialsException : ServicesException() {
    override val code = 4007
    override val description = "Invalid login credentials, either email or password doesn't match"
}
