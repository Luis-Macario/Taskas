package pt.isel.ls.services

import kotlinx.serialization.Serializable
import org.slf4j.LoggerFactory
import pt.isel.ls.database.AppDatabase
import pt.isel.ls.database.memory.Board
import pt.isel.ls.database.memory.Card
import pt.isel.ls.database.memory.TaskList
import pt.isel.ls.database.memory.User
import java.sql.*

private val logger = LoggerFactory.getLogger("pt.isel.ls.http")

class TasksServices(private val database: AppDatabase) : AppServices {

    //User Management

    /**
     * Create a new user
     *
     * @param name user's name
     * @param email user's email
     *
     * @return user's token and id
     */
    fun createUser(name: String, email: String): CreateUserResponse {
        val token = name
        val id = 1

        return CreateUserResponse(token, id)
    }

    /**
     * Get the details of a user
     *
     * @param uid user's unique identifier
     *
     * @return user object
     */
    fun getUser(uid: Int)/*: User*/ = database.getUserDetails(uid)

    /**
     * Get all users
     *
     * @return List of user objects
     */
    fun getAllUsers(): List<User> = database.getAllUsers()


    //Board Management

    /**
     * Creates a new board
     *
     * @param uid unique id of the user creating the board
     * @param name project's name
     * @param description board's description, optional
     *
     * @return Board's unique identifier
     */
    fun createBoard(uid: Int, name: String, description: String?): Int = database.getBoard(uid, description, name)

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

    fun getUserBoards(uid: Int): List<Board> = database.getUserBoards(uid)

    /**
     * Get the detailed information of a board
     *
     * @param bid board's unique id
     *
     * @return board object
     */
    fun getBoard(bid: Int): Board = database.getUserBoards(bid)

    /**
     * Creates a new list on a board
     *
     * @param name list's name
     *
     * @return list unique identifier
     */
    fun getList(name: String): Int = database

    /**
     * Get the lists of a board.
     *
     * @param bid board's unique identifier
     *
     * @return List of BList objects
     */
    fun getBoardsLists(bid: Int): List<TaskList> = database

    /**
     * Get detailed information of a list.
     *
     * @param lid list's unique identifier
     *
     * @return BList object
     */
    fun getList(lid: Int): TaskList = database

    /**
     * Creates a new card in a list.
     *
     * @param name task's name
     * @param description  the task description
     * @param dueDate  the task's conclusion date, optional
     *
     * @return Card's unique identifier
     */
    fun createCard(name: String, description: String, dueDate: Date? = null, lid: Int): Int = database

    /**
     * Get the set of cards in a list.
     *
     * @param lid list's unique identifier
     *
     * @return List of Card objects
     */
    fun getTaskListCards(lid: Int): List<Card> = database

    /**
     * Get the detailed information of a card.
     *
     * @param cid card's unique identifier
     *
     * @return Card Object
     */
    fun getCard(cid: Int): Card = database

    /**
     * Moves a card, given the following parameter
     *
     * @param lid the destination list identifier
     *
     * @return ?
     */
    fun moveCard(lid: Int) = database

}

