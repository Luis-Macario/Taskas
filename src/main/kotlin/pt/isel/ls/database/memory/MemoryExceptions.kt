package pt.isel.ls.database.memory

import pt.isel.ls.domain.TaskException

sealed class MemoryException : TaskException()

object UserNotFoundException : MemoryException() {
    override val code = 1000
    override val description = "A user with that id does not exist"
}

object EmailAlreadyExistsException : MemoryException() {
    override val code = 1001
    override val description = "A user with that email already exists"
}

object BoardNotFoundException : MemoryException() {
    override val code = 1002
    override val description = "The board with the id provided doesn't exist"
}

object BoardNameAlreadyExistsException : MemoryException() {
    override val code = 1003
    override val description = "A board with that name already exists"
}

object ListNotFoundException : MemoryException() {
    override val code = 1004
    override val description = "The list with the id provided doesn't exist"
}

object CardNotFoundException : MemoryException() {
    override val code = 1005
    override val description = "The card with the id provided doesn't exist"
}

object UserAlreadyExistsInBoardException : MemoryException() {
    override val code = 1006
    override val description = "The user already exists in that board"
}

object TaskListAlreadyExistsInBoardException : MemoryException() {
    override val code = 1007
    override val description = "A list with that name already exists in that board"
}

object CardNameAlreadyExistsException : MemoryException() {
    override val code = 1008
    override val description = "A board with that name already exists"
}

object UsersBoardDoesNotExistException : MemoryException() {
    override val code = 1009
    override val description = "A user has a board that doesn't exist."
}

object BoardsUserDoesNotExistException : MemoryException() {
    override val code = 1010
    override val description = "A board has a user that doesn't exist."
}
