package pt.isel.ls.database.postgres

import pt.isel.ls.database.DataError

enum class PGError(val code: Int, val description: String) : DataError {
    USER_NOT_FOUND(4000, "The user with the id provided doesn't exist"),
    BOARD_NOT_FOUND(4001, "The board with the id provided doesn't exist"),
    USERS_BOARD_DOES_NOT_EXIST(5001, "A user has a board that doesn't exist."),
    BOARDS_USER_DOES_NOT_EXIST(5002, "A board has a user that doesn't exist."),
}