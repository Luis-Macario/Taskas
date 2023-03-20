package pt.isel.ls.services.boards

import pt.isel.ls.database.AppDatabase
import pt.isel.ls.database.DataBoard
import pt.isel.ls.database.DataUserBoards

class BoardServices(private val database : AppDatabase) {
    /**
     * Creates a new board
     *
     * @param uid unique id of the user creating the board
     * @param name project's name
     * @param description board's description, optional
     *
     * @return Board's unique identifier
     */
    fun createBoard(uid: Int, name: String, description: String?): Int {
        val desc = description ?: ""

        return database.createBoard(uid, desc, name).id
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

    fun getUserBoards(uid: Int): DataUserBoards? = database.getBoardsFromUser(uid)

    /**
     * Get the detailed information of a board
     *
     * @param bid board's unique id
     *
     * @return board object
     */
    fun getBoard(bid: Int): DataBoard? = database.getBoardDetails(bid)

}