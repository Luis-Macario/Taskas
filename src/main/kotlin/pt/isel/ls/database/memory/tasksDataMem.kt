package pt.isel.ls.database.memory

import pt.isel.ls.database.*
import pt.isel.ls.domain.*
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


    override fun createUser(name: String, email: String): DataUserCreated {
        val token = UUID.randomUUID().toString()
        val id = userId.also { userId += 1 }

        users[id] = User(id, name, email, token)
        return DataUserCreated(id, token)
    }

    override fun getUserDetails(uid: Int): DataUser {
        val u = users[uid] ?: throw UserNotFound
        val boards = getBoardsFromUser(uid).boards

        return DataUser(u.id, u.name, u.email, boards)
    }

    override fun createBoard(uid: Int, name: String, description: String): DataBoardCreated {
        val id = boardId.also { boardId += 1 }
        boards[id] = Board(id, uid, name, description)
        addUserToBoard(uid, id)

        return DataBoardCreated(id)
    }

    override fun getBoardDetails(bid: Int): DataBoard {
        val board = boards[bid] ?: throw BoardNotFound
        val list = getListsFromBoard(bid).lists

        val users = userBoard.values
            .filter { it.bId == bid }
            .map {
                val userDetails = getUserDetails(it.uId)
                DataSimpleUser(userDetails.id, userDetails.name, userDetails.email)
            }

        return DataBoard(board.id, board.name, board.description, users, list)
    }

    override fun addUserToBoard(uid: Int, bid: Int): DataUserAdded {
        val id = userBoardId.also { userBoardId += 1 }
        userBoard[id] = UserBoard(uid, bid)

        return DataUserAdded
    }

    override fun getBoardsFromUser(uid: Int): DataUserBoards {
        val boards : List<DataSimpleBoard> =
            userBoard.values
                .filter { it.uId == uid }
                .map {
                    val b = getBoardDetails(it.bId)
                    DataSimpleBoard(b.id, b.name, b.description)
                }

        return DataUserBoards(boards)
    }

    override fun createList(bid: Int, name: String): DataListCreated {
        val id = listId.also { listId += 1 }
        taskLists[id] = TaskList(bid, id, name)

        return DataListCreated(id)
    }

    override fun getListsFromBoard(bid: Int): DataBoardLists {
        val lists : List<DataSimpleList> =
        taskLists.values
            .filter { it.bid == bid }
            .map {
                val l = getListDetails(it.id)
               DataSimpleList(l.id, l.name)
            }

        return DataBoardLists(lists)
    }


    override fun getListDetails(lid: Int): DataList {
        val list = taskLists[lid] ?: throw ListNotFound
        //TODO()
        return DataList(list.id, list.name, listOf())
    }

    override fun createCard(bid: Int, lid: Int, name: String, description: String, dueDate: Date): DataCardCreated {
        val id = cardId.also { cardId += 1 }

        cards[id] = Card(bid, lid, id, name, description, dueDate)
        return DataCardCreated(id)
    }

    override fun getCardsFromList(lid: Int): DataListCards {
        val cardsList : List<DataSimpleCard> =
        cards.values
            .filter{ it.lid == lid}
            .map{
                val c = getCardDetails(lid, it.id)
                DataSimpleCard(c.id, c.name, c.description, c.dueDate)
            }

        return DataListCards(cardsList)
    }

    override fun getCardDetails(lid: Int, cid: Int): DataCard {
        val c = cards[cid] ?: throw CardNotFound
        val list = getListDetails(lid).name

        return DataCard(c.id, c.name, c.description, c.initDate, DataSimpleList(lid, list))
    }

    override fun moveCard(cid: Int, lid: Int): DataCardMoved {
        val c = getCardDetails(cid, lid)
        cards[cid] = Card(c.id, lid, cid, c.name, c.description, c.dueDate)

        return DataCardMoved
    }

}