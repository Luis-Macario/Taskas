package pt.isel.ls.services.boards

import pt.isel.ls.database.AppDatabase
import pt.isel.ls.database.memory.BoardNotFoundException
import pt.isel.ls.domain.Board
import pt.isel.ls.domain.TaskList
import pt.isel.ls.domain.User
import pt.isel.ls.services.utils.checkToken
import pt.isel.ls.services.utils.exceptions.IllegalBoardAccessException
import pt.isel.ls.services.utils.exceptions.IllegalListAccessException
import pt.isel.ls.services.utils.exceptions.NoSuchBoardException

class BoardServices(private val database: AppDatabase) {
    /**
     * Creates a new board
     *
     * @param token token of the user creating the board
     * @param name project's name
     * @param description board's description, optional
     *
     * @return Board's unique identifier
     */
    fun createBoard(token: String, name: String, description: String): Board {
        checkToken(token)
        val uid = database.tokenToId(token)
        return database.createBoard(uid, name, description)
    }

    /**
     * Add a user to the board
     *
     * @param token user's token
     * @param uid id of the user to add
     * @param bid board's unique id
     *
     *@throws IllegalBoardAccessException if the user doesn't have access to the board
     *@throws NoSuchBoardException if board id is never found
     */
    fun addUserToBoard(token: String, uid: Int, bid: Int) {
        checkToken(token)
        try {
            val users = getUsersFromBoard(token, bid)
            if (!users.any { it.token == token }) throw IllegalBoardAccessException

            database.addUserToBoard(uid, bid)
        } catch (e: BoardNotFoundException) {
            throw NoSuchBoardException
        }
    }

    /**
     * Get the detailed information of a board
     *
     * @param token token of the user requesting the board
     * @param bid board's unique id
     *
     * @throws IllegalBoardAccessException if user doesn't have permission to get board details
     *
     * @return Board object
     */
    fun getBoardDetails(token: String, bid: Int): Board {
        checkToken(token)
        val users = getUsersFromBoard(token, bid)
        if (!users.any { it.token == token }) throw IllegalBoardAccessException

        return database.getBoardDetails(bid)
    }

    /**
     * Get all the users in a board
     *
     * @param token token of the user requesting the board
     * @param bid board's unique id
     *
     * @throws IllegalBoardAccessException if user doesn't have permission to get board details
     * @throws NoSuchBoardException if board id is never found
     *
     * @return List of User objects
     */
    fun getUsersFromBoard(token: String, bid: Int): List<User> {
        checkToken(token)
        database.getBoardDetails(bid) // check if board exists
        try {
            val users = database.getUsersFromBoard(bid)
            if (!users.any { it.token == token }) throw IllegalBoardAccessException

            return users
        } catch (e: BoardNotFoundException) {
            throw NoSuchBoardException
        }
    }

    /**
     * Get all the users in a board
     *
     * @param token token of the user requesting the board
     * @param bid board's unique id
     *
     * @throws IllegalListAccessException if user doesn't have permission to get board details
     *
     * @return List of TaskList objects
     */
    fun getListsFromBoard(token: String, bid: Int): List<TaskList> {
        checkToken(token)
        val users = getUsersFromBoard(token, bid)
        if (!users.any { it.token == token }) throw IllegalListAccessException
        return database.getListsFromBoard(bid)
    }
}
