package pt.isel.ls.database.memory

object UserNotFound : Exception() {
    const val code = 4000
    const val description = "A user with that email already exists"
}

object EmailAreadyExists : Exception() {
    const val code = 4001
    const val description = "The user with the id provided doesn't exist"
}

object BoardNotFound : Exception() {
    const val code = 4002
    const val description = "The board with the id provided doesn't exist"
}

object ListNotFound : Exception() {
    const val code = 4003
    const val description = "The list with the id provided doesn't exist"
}

object CardNotFound : Exception() {
    const val code = 4004
    const val description = "The card with the id provided doesn't exist"
}

object UsersBoardDoesNotExist : Exception() {
    const val code = 5001
    const val description = "A user has a board that doesn't exist."
}

object BoardsUserDoesNotExist : Exception() {
    const val code = 5002
    const val description = "A board has a user that doesn't exist."
}