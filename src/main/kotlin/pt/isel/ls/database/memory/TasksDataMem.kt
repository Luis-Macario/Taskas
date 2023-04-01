package pt.isel.ls.database.memory

import pt.isel.ls.database.AppDatabase
import pt.isel.ls.domain.Board
import pt.isel.ls.domain.Card
import pt.isel.ls.domain.TaskList
import pt.isel.ls.domain.User
import pt.isel.ls.domain.UserBoard
import java.sql.Date

class TasksDataMem : AppDatabase {

    private var userId: Int = 0
    private var boardId: Int = 0
    private var listId: Int = 0
    private var cardId: Int = 0
    private var userBoardId: Int = 0

    val users: MutableMap<Int, User> = mutableMapOf()
    val boards: MutableMap<Int, Board> = mutableMapOf()
    val userBoard: MutableMap<Int, UserBoard> = mutableMapOf()
    val taskLists: MutableMap<Int, TaskList> = mutableMapOf()
    val cards: MutableMap<Int, Card> = mutableMapOf()

    /**
     * Creates a new user
     *
     * @param name user's name
     * @param email user's email, must be unique
     *
     * @throws EmailAlreadyExistsException if the email already exists
     * @return the created User()
     */

    override fun createUser(token: String, name: String, email: String): User {
        val id = userId.also { userId += 1 }

        if (users.values.any { it.email == email }) throw EmailAlreadyExistsException

        val newUser = User(id, name, email, token)
        users[id] = newUser
        return newUser
    }

    /**
     * Get user details
     *
     * @param uid user's unique identifier
     *
     * @throws UserNotFoundException if the User was not found
     * @return the User() details
     */
    override fun getUserDetails(uid: Int): User = users[uid] ?: throw UserNotFoundException

    /**
     * Get the user boards
     *
     * @param bid board's unique identifier
     *
     * @return the list of users from that specific board
     */
    override fun getUsersFromBoard(bid: Int): List<User> =
        userBoard.values
            .filter { it.bId == bid }
            .map { getUserDetails(it.uId) }

    override fun checkEmailAlreadyExists(email: String) = users.values.any { it.email == email }

    /**
     * Creates a new board
     *
     * @param uid user's unique identifier
     * @param name board's unique name
     * @param description board's descritpion
     *
     * @throws UserNotFoundException if the user was not found
     * @throws BoardNameAlreadyExistsException if the board  name already exists
     * @return the created Board()
     */
    override fun createBoard(uid: Int, name: String, description: String): Board {
        val id = boardId.also { boardId += 1 }
        // val userId = tokenToId(uid)
        if (!users.values.any { it.id == uid }) throw UserNotFoundException
        if (boards.values.any { it.name == name }) throw BoardNameAlreadyExistsException

        val newBoard = Board(id, name, description)
        boards[id] = newBoard
        addUserToBoard(uid, id)

        return newBoard
    }

    /**
     * Get user details
     *
     * @param bid board's unique identifier
     * @throws BoardNotFoundException if the board was not found
     * @return the Board() details
     */
    override fun getBoardDetails(bid: Int): Board = boards[bid] ?: throw BoardNotFoundException

    /**
     * Add a User to a Board
     *
     * @param uid user's unique identifier
     * @param bid board's unique identifier
     *
     * @throws UserNotFoundException if the user was not found
     * @throws BoardNotFoundException if the board was not found
     * @throws UserAlreadyExistsInBoardException if a board already contains a User with that id
     */
    override fun addUserToBoard(uid: Int, bid: Int) {
        val id = userBoardId.also { userBoardId += 1 }
        if (!users.values.any { it.id == uid }) throw UserNotFoundException
        if (!boards.values.any { it.id == bid }) throw BoardNotFoundException
        if (userBoard.values.any { it.uId == uid && it.bId == bid }) throw UserAlreadyExistsInBoardException

        userBoard[id] = UserBoard(uid, bid)
    }

    /**
     * Get the boards from a User
     *
     * @param uid user's unique identifier
     *
     * @return list of boards from that User
     */
    // TODO("Should we throw the UsersBoardDoesNotExist ??")
    override fun getBoardsFromUser(uid: Int): List<Board> =
        userBoard.values
            .filter { it.uId == uid }
            .map { board ->
                getBoardDetails(board.bId)
            }

    /**
     * Creates a new List
     *
     * @param bid board's unique identifier
     * @param name list's name
     *
     *  @throws BoardNotFoundException if the board was not found
     * @return the created TaskList()
     */
    override fun createList(bid: Int, name: String): TaskList {
        val id = listId.also { listId += 1 }
        if (!boards.values.any { it.id == bid }) throw BoardNotFoundException
        if (taskLists.values.any { it.bid == bid && it.name == name }) throw TaskListAlreadyExistsInBoardException

        val newList = TaskList(id, bid, name)
        taskLists[id] = newList

        return newList
    }

    /**
     * Get the lists from a Board
     *
     * @param bid board's unique identifier
     *
     * @throws BoardNotFoundException if the board was not found
     * @return the list of TaskList from that Board
     */
    override fun getListsFromBoard(bid: Int): List<TaskList> {
        if (!boards.values.any { it.id == bid }) throw BoardNotFoundException
        return taskLists.values
            .filter { it.bid == bid }
            .map {
                getListDetails(it.id)
            }
    }

    /**
     * Get a TaskList details
     *
     * @param lid taskList's unique identifier
     *
     * @throws ListNotFoundException if the list was not found
     * @return the TaskList() details
     */
    override fun getListDetails(lid: Int): TaskList = taskLists[lid] ?: throw ListNotFoundException

    override fun checkListsFromSameBoard(l1: Int, l2: Int): Boolean {
        TODO("Not yet implemented")
    }

    /**
     * Creates a new Card
     *
     * @param lid taskList's unique identifier
     *
     * @return the created Card()
     */
    override fun createCard(lid: Int, name: String, description: String, dueDate: Date): Card {
        if (cards.values.filter { it.lid == lid }.any { it.name == name }) throw CardNameAlreadyExistsException
        val id = cardId.also { cardId += 1 }
        val initDate = Date(System.currentTimeMillis())
        val newCard = Card(id, getListDetails(lid).bid, lid, name, description, initDate, dueDate)

        cards[id] = newCard
        return newCard
    }

    /**
     * Get the cards from a TaskList
     *
     * @param lid taskList's unique identifier
     *
     * @throws ListNotFoundException if the list was not found
     * @return list of cards from a TaskList
     */
    override fun getCardsFromList(lid: Int): List<Card> {
        if (!taskLists.values.any { it.id == lid }) throw ListNotFoundException
        return cards.values
            .filter { it.lid == lid }
            .map {
                getCardDetails(it.id)
            }
    }

    /**
     * Get card details
     *
     * @param cid card's unique identifier
     *
     * @throws CardNotFoundException if the card was not found
     * @return the Card() details
     */
    override fun getCardDetails(cid: Int): Card = cards[cid] ?: throw CardNotFoundException

    /**
     * Move a card to another TaskList
     *
     * @param cid card's unique identifier
     * @param lid taskList's unique identifier
     *
     * @throws ListNotFoundException if the list was not found
     */
    override fun moveCard(cid: Int, lid: Int) {
        val c = getCardDetails(cid)
        if (!taskLists.values.any { it.id == lid }) throw ListNotFoundException
        cards[cid] = c.copy(lid = lid)
    }

    /**
     * Get the User id given the bearer token
     *
     * @param bToken user's bearer token
     *
     * @throws UserNotFoundException if the user was not found
     * @return user unique identifier
     */
    override fun tokenToId(bToken: String): Int {
        return users.values.firstOrNull { it.token == bToken }?.id ?: throw UserNotFoundException
    }
}
