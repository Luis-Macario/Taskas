package pt.isel.ls.database.memory

import pt.isel.ls.database.*
import java.sql.Date
import java.util.*

class TasksDataMem : AppDatabase {

    private var userId: Int = 0
    private var boardId: Int = 0
    private var listId: Int = 0
    private var cardId: Int = 0
    private var userBoardId: Int = 0

    private val users: MutableMap<Int, User> = mutableMapOf()
    private val boards: MutableMap<Int, Board> = mutableMapOf()
    private val userBoard: MutableMap<Int, UserBoard> = mutableMapOf()
    private val taskLists: MutableMap<Int, TaskList> = mutableMapOf()
    private val cards: MutableMap<Int, Card> = mutableMapOf()


    override fun createUser(name: String, email: String): DataUserCreated {
        val token = UUID.randomUUID().toString()
        val id = userId.also { userId += 1 }

        users[id] = User(id, name, email, token)
        return DataUserCreated(id, token)
    }

    override fun getUserDetails(uid: Int): DataUser {
        val u = users[uid] ?: throw UserNotFound
        val boards = getBoardsFromUser(uid).boards
        if (boards.isEmpty()) throw BoardsUserDoesNotExist

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
        val users = mutableListOf<DataSimpleUser>()

        //could be map
        userBoard.values.forEach {
            if (it.bId == bid) {
                val u = getUserDetails(it.uId)
                users.add(DataSimpleUser(u.id, u.name, u.email))
            }
        }
        return DataBoard(board.id, board.name, board.description, users, list)
    }

    override fun addUserToBoard(uid: Int, bid: Int): DataUserAdded {
        val id = userBoardId.also { userBoardId += 1 }
        userBoard[id] = UserBoard(uid, bid)

        return DataUserAdded
    }


    /*fun addUserToBoard(bId: Int, uId: Int) {
        val id = userBoardId
        userBoard[id] = UserBoard(uId, bId)
        userBoardId += 1
    }*/

    override fun getBoardsFromUser(uid: Int): DataUserBoards {
        val boards = mutableListOf<DataSimpleBoard>()

        //could be map
        userBoard.values.forEach {
            if (it.uId == uid) {
                val b = getBoardDetails(it.bId)
                boards.add(DataSimpleBoard(b.id, b.name, b.description))
            }
        }
        return DataUserBoards(boards.toList())
    }

    override fun createList(bid: Int, name: String): DataListCreated {
        val id = listId.also { listId += 1 }
        taskLists[id] = TaskList(bid, id, name)

        return DataListCreated(id)
    }

    override fun getListsFromBoard(bid: Int): DataBoardLists {
        val lists = mutableListOf<DataSimpleList>()

        // could be map
        taskLists.values.forEach {
            if (it.bid == bid) {
                val l = getListDetails(it.id)
                lists.add(DataSimpleList(l.id, l.name))
            }
        }
        return DataBoardLists(lists.toList())
    }


    override fun getListDetails(lid: Int): DataList {
        val list = taskLists[lid] ?: throw ListNotFound

        return DataList(list.id, list.name, listOf())
    }

    override fun createCard(bid: Int, lid: Int, name: String, description: String, dueDate: Date): DataCardCreated {
        val id = cardId.also { cardId += 1 }

        cards[id] = Card(bid, lid, id, name, description, dueDate)
        return DataCardCreated(id)
    }

    override fun getCardsFromList(lid: Int): DataListCards {
        val cardsList = mutableListOf<DataSimpleCard>()

        //could be map
        cards.values.forEach {
            if (it.lid == lid) {
                val c = getCardDetails(it.lid, it.id)
                cardsList.add(DataSimpleCard(c.id, c.name, c.description, c.dueDate))
            }
        }
        return DataListCards(cardsList.toList())
    }

    override fun getCardDetails(lid: Int, cid: Int): DataCard {
        val c = cards[cid] ?: throw CardNotFound
        val list = getListDetails(lid).name

        return DataCard(c.id, c.name, c.description, c.initDate, DataSimpleList(lid, list))
    }

    override fun moveCard(cid: Int, lid: Int): DataCardMoved {
        TODO("Not yet implemented")
    }

}