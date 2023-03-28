package pt.isel.ls.utils.exceptions

import pt.isel.ls.domain.TaskException

sealed class ServicesException : TaskException()

object IllegalUserAccessException : ServicesException() {
    override val code = 9000 // TODO: Figure out what code to use
    override val description = "User doesn't have access to this user"
}

object IllegalBoardAccessException : ServicesException() {
    override val code = 9000 // TODO: Figure out what code to use
    override val description = "User doesn't have access to this board"
}

object IllegalListAccessException : ServicesException() {
    override val code = 9000 // TODO: Figure out what code to use
    override val description = "User doesn't have access to this list"
}

object IllegalCardAccessException : ServicesException() {
    override val code = 9000 // TODO: Figure out what code to use
    override val description = "User doesn't have access to this card"
}

object InvalidBearerToken : ServicesException() {
    override val code = 9000 // TODO: Figure out what code to use
    override val description = "Authorization header is no valid"
}
