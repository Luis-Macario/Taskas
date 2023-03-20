package pt.isel.ls.database.memory

object UserNotFound : Throwable() {
    const val code = 4000
    const val description = "The user with the id provided doesn't exist"
}

object BoardNotFound : Throwable() {
    const val code = 4001
    const val description = "The board with the id provided doesn't exist"
}

object ListNotFound : Throwable() {
    const val code = 4002
    const val description = "The list with the id provided doesn't exist"
}

object CardNotFound : Throwable() {
    const val code = 4003
    const val description = "The card with the id provided doesn't exist"
}

object UsersBoardDoesNotExist : Throwable() {
    const val code = 5001
    const val description = "A user has a board that doesn't exist."
}

object BoardsUserDoesNotExist : Throwable() {
    const val code = 5002
    const val description = "A board has a user that doesn't exist."
}