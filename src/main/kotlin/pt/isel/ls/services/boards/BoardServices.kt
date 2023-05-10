package pt.isel.ls.services.boards

import pt.isel.ls.database.AppDatabase
import pt.isel.ls.database.memory.BoardNameAlreadyExistsException
import pt.isel.ls.database.memory.BoardNotFoundException
import pt.isel.ls.database.memory.UserAlreadyExistsInBoardException
import pt.isel.ls.domain.Board
import pt.isel.ls.domain.SimpleList
import pt.isel.ls.domain.User
import pt.isel.ls.domain.checkBoardCredentials
import pt.isel.ls.services.utils.checkToken
import pt.isel.ls.services.utils.exceptions.IllegalBoardAccessException

class BoardServices(private val database: AppDatabase) {
    /**
     * Creates a new board and adds the user to the board
     *
     * @param token token of the user creating the board
     * @param name project's name
     * @param description board's description, optional
     *
     * @return Board's unique identifier
     */
    fun createBoard(token: String, name: String, description: String): Board {
        checkToken(token)
        checkBoardCredentials(name, description)
        if (database.checkBoardNameAlreadyExists(name)) throw BoardNameAlreadyExistsException

        val uid = database.tokenToId(token)
        val id = database.createBoard(uid, name, description)

        return Board(id, name, description, listOf())
    }

    /**
     * Add a user to the board
     *
     * @param token user's token
     * @param uid id of the user to add
     * @param bid board's unique id
     *
     *@throws IllegalBoardAccessException if the user doesn't have access to the board
     */
    fun addUserToBoard(token: String, uid: Int, bid: Int) {
        checkToken(token)
        if (!database.checkBoardExists(bid)) throw BoardNotFoundException
        if (!database.checkUserTokenExistsInBoard(token, bid)) throw IllegalBoardAccessException // User Access
        if (database.checkUserAlreadyExistsInBoard(
                uid,
                bid
            )
        ) {
            throw UserAlreadyExistsInBoardException // Check if User is already on the board
        }

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
    fun getBoardDetails(token: String, bid: Int): Board {
        checkToken(token)

        if (!database.checkBoardExists(bid)) throw BoardNotFoundException
        if (!database.checkUserTokenExistsInBoard(token, bid)) throw IllegalBoardAccessException

        val lists = getListsFromBoard(token, bid)
        val simpleBoard = database.getBoardDetails(bid)

        return Board(simpleBoard.id, simpleBoard.name, simpleBoard.description, lists)
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
    fun getUsersFromBoard(token: String, bid: Int, skip: Int? = null, limit: Int? = null): List<User> {
        checkToken(token)
        if (!database.checkBoardExists(bid)) throw BoardNotFoundException
        if (!database.checkUserTokenExistsInBoard(token, bid)) throw IllegalBoardAccessException

        val users = database.getUsersFromBoard(bid)
        val droppedUsers = if (skip != null) users.drop(skip) else users
        return if (limit != null) droppedUsers.take(limit) else droppedUsers
    }

    /**
     * Get all the users in a board
     *
     * @param token token of the user requesting the board
     * @param bid board's unique id
     *
     * @throws IllegalBoardAccessException if user doesn't have permission to get board details
     *
     * @return List of TaskList objects
     */
    fun getListsFromBoard(token: String, bid: Int, skip: Int? = null, limit: Int? = null): List<SimpleList> {
        checkToken(token)
        //TODO: SE A BOARD NAO EXISTIR TEM DE DAR THROW A BoardDoesNotExistException, E NAO IllegalBoardAccessException
        if (!database.checkUserTokenExistsInBoard(token, bid)) throw IllegalBoardAccessException

        val lists = database.getListsFromBoard(bid)
        val droppedLists = if (skip != null) lists.drop(skip) else lists
        return if (limit != null) droppedLists.take(limit) else droppedLists
    }
}
