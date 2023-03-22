package pt.isel.ls.database.memory

import pt.isel.ls.domain.TaskError

object UserNotFound : TaskError() {
    override val code = 4000
    override val description = "A user with that id does not exist"
}

object EmailAreadyExists : TaskError() {
    override val code = 4001
    override val description = "A user with that email already exists"
}

object BoardNotFound : TaskError() {
    override val code = 4002
    override val description = "The board with the id provided doesn't exist"
}

object ListNotFound : TaskError() {
    override val code = 4003
    override val description = "The list with the id provided doesn't exist"
}

object CardNotFound : TaskError() {
    override val code = 4004
    override val description = "The card with the id provided doesn't exist"
}

object UsersBoardDoesNotExist : TaskError() {
    override val code = 5001
    override val description = "A user has a board that doesn't exist."
}

object BoardsUserDoesNotExist : TaskError() {
    override val code = 5002
    override val description = "A board has a user that doesn't exist."
}
