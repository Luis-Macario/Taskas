package pt.isel.ls.services.utils.exceptions

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

object IllegalMoveCardRequestException : ServicesException() {
    override val code = 9000 // TODO: Figure out what code to use
    override val description = "Original list and destination list aren't in the same board"
}

object NoSuchBoardException : ServicesException() {
    override val code = 9000 // TODO: Figure out what code to use
    override val description = "There's no board with that id"
}

object InvalidBearerToken : ServicesException() {
    override val code = 9000 // TODO: Figure out what code to use
    override val description = "Authorization header is not valid"
}
