package pt.isel.ls.services.boards

import pt.isel.ls.database.AppDatabase
import pt.isel.ls.database.memory.BoardNameAlreadyExistsException
import pt.isel.ls.database.memory.BoardNotFoundException
import pt.isel.ls.database.memory.UserAlreadyExistsInBoardException
import pt.isel.ls.domain.Board
import pt.isel.ls.domain.SimpleList
import pt.isel.ls.domain.User
import pt.isel.ls.domain.checkBoardCredentials
import pt.isel.ls.services.utils.LIMIT_DEFAULT
import pt.isel.ls.services.utils.SKIP_DEFAULT
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

        return database.getUsersFromBoard(bid, skip ?: SKIP_DEFAULT, limit ?: LIMIT_DEFAULT)
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
        if (!database.checkBoardExists(bid)) throw BoardNotFoundException
        if (!database.checkUserTokenExistsInBoard(token, bid)) throw IllegalBoardAccessException

        return database.getListsFromBoard(bid, skip ?: SKIP_DEFAULT, limit ?: LIMIT_DEFAULT)
    }

    fun getAllUser(token: String, bid: Int): List<User> {
        checkToken(token)
        if (!database.checkBoardExists(bid)) throw BoardNotFoundException
        if (!database.checkUserTokenExistsInBoard(token, bid)) throw IllegalBoardAccessException // User Access
        val toFilterUsers = database.getUsersFromBoard(bid, SKIP_DEFAULT, LIMIT_DEFAULT)
        val allUsers = database.getAllAvailableUser()
        return allUsers.filter { it !in toFilterUsers }
    }
}
