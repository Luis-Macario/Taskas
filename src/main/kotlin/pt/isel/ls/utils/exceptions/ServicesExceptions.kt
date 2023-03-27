package pt.isel.ls.utils.exceptions

import pt.isel.ls.domain.TaskException

object IllegalUserAccessException : TaskException() {
    override val code = 9000 //TODO: Figure out what code to use
    override val description = "User doesn't have access to this user"
}

object IllegalBoardAccessException : TaskException() {
    override val code = 9000 //TODO: Figure out what code to use
    override val description = "User doesn't have access to this board"
}


object IllegalListAccessException : TaskException() {
    override val code = 9000 //TODO: Figure out what code to use
    override val description = "User doesn't have access to this list"
}

object IllegalCarddAccessException : TaskException() {
    override val code = 9000 //TODO: Figure out what code to use
    override val description = "User doesn't have access to this card"
}