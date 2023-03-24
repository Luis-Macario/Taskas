package pt.isel.ls.database.memory

import pt.isel.ls.domain.TaskException

object UserNotFound : TaskException() {
    override val code = 4000
    override val description = "A user with that id does not exist"
}

object EmailAlreadyExists : TaskException() {
    override val code = 4001
    override val description = "A user with that email already exists"
}

object BoardNotFound : TaskException() {
    override val code = 4002
    override val description = "The board with the id provided doesn't exist"
}

object BoardNameAlreadyExists : TaskException() {
    override val code = 4003
    override val description = "A board with that name already exists"
}

object ListNotFound : TaskException() {
    override val code = 4004
    override val description = "The list with the id provided doesn't exist"
}

object CardNotFound : TaskException() {
    override val code = 4005
    override val description = "The card with the id provided doesn't exist"
}

object UserAlreadyExistsInBoard : TaskException() {
    override val code = 4006
    override val description = "A user already exists in that board"
}

object UsersBoardDoesNotExist : TaskException() {
    override val code = 5001
    override val description = "A user has a board that doesn't exist."
}

object BoardsUserDoesNotExist : TaskException() {
    override val code = 5002
    override val description = "A board has a user that doesn't exist."
}
