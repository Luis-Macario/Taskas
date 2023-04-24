package pt.isel.ls.database.memory

import pt.isel.ls.domain.TaskException

sealed class MemoryException : TaskException()

object UserNotFoundException : MemoryException() {
    override val code = 4000
    override val description = "A user with that id does not exist"
}

object EmailAlreadyExistsException : MemoryException() {
    override val code = 4001
    override val description = "A user with that email already exists"
}

object BoardNotFoundException : MemoryException() {
    override val code = 4002
    override val description = "The board with the id provided doesn't exist"
}

object BoardNameAlreadyExistsException : MemoryException() {
    override val code = 4003
    override val description = "A board with that name already exists"
}

object ListNotFoundException : MemoryException() {
    override val code = 4004
    override val description = "The list with the id provided doesn't exist"
}

object CardNotFoundException : MemoryException() {
    override val code = 4005
    override val description = "The card with the id provided doesn't exist"
}

object UserAlreadyExistsInBoardException : MemoryException() {
    override val code = 4006
    override val description = "The user already exists in that board"
}

object TaskListAlreadyExistsInBoardException : MemoryException() {
    override val code = 4007
    override val description = "A list with that name already exists in that board"
}

object CardNameAlreadyExistsException : MemoryException() {
    override val code = 4008
    override val description = "A board with that name already exists"
}

object UsersBoardDoesNotExistException : MemoryException() {
    override val code = 5001
    override val description = "A user has a board that doesn't exist."
}

object BoardsUserDoesNotExistException : MemoryException() {
    override val code = 5002
    override val description = "A board has a user that doesn't exist."
}
