package pt.isel.ls.services.boards

import pt.isel.ls.database.AppDatabase
import pt.isel.ls.domain.Board

class BoardServices(private val database: AppDatabase) {
    /**
     * Creates a new board
     *
     * @param name project's name
     * @param description board's description, optional
     *
     * @return Board's unique identifier
     */
    fun createBoard(uid: Int, name: String, description: String?): Board {
        val desc = description ?: ""

        return database.createBoard(uid, desc, name)
    }

    /**
     * Add a user to the board
     *
     * @param uid user's unique id
     * @param bid board's unique id
     *
     * @return ?
     */
    fun addUserToBoard(uid: Int, bid: Int) = database.addUserToBoard(uid, bid)

    /**
     * Get the list with all user available boards
     *
     * @param uid user's unique id
     *
     * @return List of user's boards
     */

    fun getUserBoards(uid: Int): List<Board> = database.getBoardsFromUser(uid)

    /**
     * Get the detailed information of a board
     *
     * @param bid board's unique id
     *
     * @return board object
     */
    fun getBoard(bid: Int): Board = database.getBoardDetails(bid)
}
