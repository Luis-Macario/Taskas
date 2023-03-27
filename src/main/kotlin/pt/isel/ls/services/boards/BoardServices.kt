package pt.isel.ls.services.boards

import pt.isel.ls.api.dto.user.UserDTO
import pt.isel.ls.database.AppDatabase
import pt.isel.ls.database.memory.TasksDataMem
import pt.isel.ls.domain.Board
import pt.isel.ls.domain.TaskList
import pt.isel.ls.domain.User
import pt.isel.ls.utils.exceptions.IllegalBoardAccessException
import pt.isel.ls.utils.exceptions.IllegalListAccessException

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
    fun createBoard(token: String, name: String, description: String?): Board {
        val uid = database.tokenToId(token)
        val desc = description ?: "No description was provided"
        return database.createBoard(uid,name,desc)
    }

    /**
     * Add a user to the board
     *
     * @param token user's token
     * @param bid board's unique id
     *
     *@throws IllegalBoardAccessException if the user doesn't have access to the board
     */
    fun addUserToBoard(token: String, uid: Int, bid: Int) {
        val users = getUsersFromBoard(token, bid)
        if(!users.any{it.token == token}) throw IllegalBoardAccessException
        database.addUserToBoard(uid, bid)
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
    fun getBoardDetails(token: String, bid: Int): Board{
        val users = getUsersFromBoard(token, bid)
        if(!users.any{it.token == token}) throw IllegalBoardAccessException
        return database.getBoardDetails(bid)
    }

    /**
     * Get all the users in a board
     *
     * @param token token of the user requesting the board
     * @param bid board's unique id
     *
     * @throws IllegalBoardAccessException if user doesn't have permission to get board details
     *
     * @return List of User objects
     */
    fun getUsersFromBoard(token: String, bid: Int): List<User> {
        val users = getUsersFromBoard(token, bid)
        if (!users.any{it.token == token}) throw IllegalBoardAccessException
        return database.getUsersFromBoard(bid)
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
        val users = getUsersFromBoard(token, bid)
        if (!users.any{it.token == token}) throw IllegalListAccessException
        return database.getListsFromBoard(bid)
    }
}
