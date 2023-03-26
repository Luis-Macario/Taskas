package pt.isel.ls.database.memory

import pt.isel.ls.domain.TaskException

object UserNotFoundException : TaskException() {
    override val code = 4000
    override val description = "A user with that id does not exist"
}

object EmailAlreadyExistsException : TaskException() {
    override val code = 4001
    override val description = "A user with that email already exists"
}

object BoardNotFoundException : TaskException() {
    override val code = 4002
    override val description = "The board with the id provided doesn't exist"
}

object BoardNameAlreadyExistsException : TaskException() {
    override val code = 4003
    override val description = "A board with that name already exists"
}

object ListNotFoundException : TaskException() {
    override val code = 4004
    override val description = "The list with the id provided doesn't exist"
}

object CardNotFoundException : TaskException() {
    override val code = 4005
    override val description = "The card with the id provided doesn't exist"
}

object UserAlreadyExistsInBoardException : TaskException() {
    override val code = 4006
    override val description = "A user already exists in that board"
}

object UsersBoardDoesNotExistException : TaskException() {
    override val code = 5001
    override val description = "A user has a board that doesn't exist."
}

object BoardsUserDoesNotExistException : TaskException() {
    override val code = 5002
    override val description = "A board has a user that doesn't exist."
}
