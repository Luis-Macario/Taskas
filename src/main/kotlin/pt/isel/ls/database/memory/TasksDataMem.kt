package pt.isel.ls.database.memory

import pt.isel.ls.database.AppDatabase
import pt.isel.ls.domain.Board
import pt.isel.ls.domain.Card
import pt.isel.ls.domain.TaskList
import pt.isel.ls.domain.User
import pt.isel.ls.domain.UserBoard
import java.sql.Date
import java.util.*

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

    override fun createUser(name: String, email: String): User {
        val token = UUID.randomUUID().toString()
        val id = userId.also { userId += 1 }

        if (users.values.any { it.email == email }) throw EmailAreadyExists

        val newUser = User(id, name, email, token)
        users[id] = newUser
        return newUser
    }

    override fun getUserDetails(uid: Int): User = users[uid] ?: throw UserNotFound

    override fun getUsersFromBoard(bid: Int): List<User> =
        userBoard.values
            .filter { it.bId == bid }
            .map { getUserDetails(it.uId) }


    override fun createBoard(uid: Int, name: String, description: String): Board {
        val id = boardId.also { boardId += 1 }
        if (boards.values.any { it.name == name }) throw BoardNameAlreadyExists

        val newBoard = Board(id, name, description)
        boards[id] = newBoard
        addUserToBoard(uid, id)

        return newBoard
    }

    override fun getBoardDetails(bid: Int): Board = boards[bid] ?: throw BoardNotFound

    override fun addUserToBoard(uid: Int, bid: Int) {
        val id = userBoardId.also { userBoardId += 1 }
        if(userBoard.values.any { it.uId == uid && it.bId == bid }) throw UserAlreadyExistsInBoard

        userBoard[id] = UserBoard(uid, bid)
    }

    //TODO("Should we throw the UsersBoardDoesNotExist ??")
    override fun getBoardsFromUser(uid: Int): List<Board> =
        userBoard.values
            .filter { it.uId == uid }
            .map { board ->
                getBoardDetails(board.bId)
            }

    override fun createList(bid: Int, name: String): TaskList {
        val id = listId.also { listId += 1 }
        val newList = TaskList(id, bid, name)
        taskLists[id] = newList

        return newList
    }

    override fun getListsFromBoard(bid: Int): List<TaskList> =
        taskLists.values
            .filter { it.bid == bid }
            .map {
                getListDetails(it.id)
            }

    override fun getListDetails(lid: Int): TaskList = taskLists[lid] ?: throw ListNotFound

    override fun createCard(lid: Int, name: String, description: String, dueDate: Date): Card {
        val id = cardId.also { cardId += 1 }
        val newCard = Card(id, getListDetails(lid).bid, lid, name, description, dueDate)

        cards[id] = newCard
        return newCard
    }

    override fun getCardsFromList(lid: Int): List<Card> =
        cards.values
            .filter { it.lid == lid }
            .map {
                getCardDetails(it.id)
            }

    override fun getCardDetails(cid: Int): Card = cards[cid] ?: throw CardNotFound

    override fun moveCard(cid: Int, lid: Int) {
        val c = getCardDetails(cid)
        if(!taskLists.values.any{it.id == lid}) throw ListNotFound
        cards[cid] = Card(c.id, c.bid,  lid, c.name, c.description, c.initDate, c.finishDate)
    }
}
