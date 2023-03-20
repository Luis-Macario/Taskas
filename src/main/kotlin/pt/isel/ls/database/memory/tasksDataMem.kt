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

    val users: MutableMap<Int, User> = mutableMapOf()
    val boards: MutableMap<Int, Board> = mutableMapOf()
    val userBoard: MutableMap<Int, UserBoard> = mutableMapOf()
    val taskLists: MutableMap<Int, TaskList> = mutableMapOf()
    val cards: MutableMap<Int, Card> = mutableMapOf()

    //Not sure about this Pair(token,id)
    override fun createUser(name: String, email: String): Pair<DataUserCreated?, DataError?> {
        val token = UUID.randomUUID().toString()
        val id = userId.also { userId += 1 }

        users[id] = User(id, name, email, token)
        return Pair(DataUserCreated(id, token), null)
    }

    override fun getUserDetails(uid: Int): Pair<DataUser?, DataError?> {
        val u = users[uid] ?: return Pair(null, MemoryError.USER_NOT_FOUND)
        val boards = getBoardsFromUser(uid).first?.boards
            ?: return Pair(null, MemoryError.BOARDS_USER_DOES_NOT_EXIST)

        return Pair(DataUser(u.id, u.name, u.email, boards), null)
    }

    override fun createBoard(uid: Int, name: String, description: String): Pair<DataBoardCreated?, DataError?> {
        val id = boardId.also { boardId += 1 }
        boards[id] = Board(id, uid, name, description)
        addUserToBoard(uid, id)

        return Pair(DataBoardCreated(id), null)
    }

    override fun getBoardDetails(bid: Int): Pair<DataBoard?, DataError?> {
        val board = boards[bid] ?: return Pair(null, MemoryError.BOARD_NOT_FOUND)
        val list = getListsFromBoard(bid).first?.lists ?: listOf()
        val users = mutableListOf<DataSimpleUser>()
        userBoard.values.forEach {
            if (it.bId == bid) {
                val u = getUserDetails(it.uId).first
                if (u != null) {
                    users.add(DataSimpleUser(u.id, u.name, u.email))
                }
            }
        }
        return Pair(DataBoard(board.id, board.name, board.description, users, list), null)
    }

    override fun addUserToBoard(uid: Int, bid: Int): Pair<DataUserAdded?, DataError?> {
        val id = userBoardId.also { userBoardId += 1 }
        userBoard[id] = UserBoard(uid, bid)

        return Pair(DataUserAdded, null)
    }


    /*fun addUserToBoard(bId: Int, uId: Int) {
        val id = userBoardId
        userBoard[id] = UserBoard(uId, bId)
        userBoardId += 1
    }*/

    override fun getBoardsFromUser(uid: Int): Pair<DataUserBoards?, DataError?> {
        val boards = mutableListOf<DataSimpleBoard>()
        userBoard.values.forEach {
            if (it.uId == uid) {
                val b = getBoardDetails(it.bId).first
                if (b != null)
                    boards.add(DataSimpleBoard(b.id, b.name, b.description))
                else
                    return Pair(null, MemoryError.USERS_BOARD_DOES_NOT_EXIST)
            }
        }
        return Pair(DataUserBoards(boards.toList()), null)
    }

    override fun createList(bid: Int, name: String): Pair<DataListCreated?, DataError?> {
        val id = listId.also { listId += 1 }
        taskLists[id] = TaskList(bid, id, name)

        return Pair(DataListCreated(id), null)
    }

    override fun getListsFromBoard(bid: Int): Pair<DataBoardLists?, DataError?> {
        val lists = mutableListOf<DataSimpleList>()

        taskLists.values.forEach {
            if (it.bid == bid) {
                val l = getListDetails(it.id).first
                if (l != null)
                    lists.add(DataSimpleList(l.id, l.name))
            }
        }
        return Pair(DataBoardLists(lists.toList()), null)
    }


    override fun getListDetails(lid: Int): Pair<DataList?, DataError?> {
        val list = taskLists[lid] ?: return Pair(null, MemoryError.LIST_NOT_FOUND)

        return Pair(DataList(list.id, list.name, listOf()), null)
    }

    override fun createCard(
        bid: Int,
        lid: Int,
        name: String,
        description: String,
        dueDate: Date
    ): Pair<DataCardCreated?, DataError?> {
        val id = cardId.also { cardId += 1 }

        cards[id] = Card(bid, lid, id, name, description, dueDate)
        return Pair(DataCardCreated(id), null)
    }

    override fun getCardsFromList(lid: Int): Pair<DataListCards?, DataError?> {
        val cardsList = mutableListOf<DataSimpleCard>()

        cards.values.forEach {
            if (it.lid == lid) {
                val c = getCardDetails(it.lid, it.id).first
                if (c != null)
                    cardsList.add(DataSimpleCard(c.id, c.name, c.description, c.dueDate))
            }
        }
        return Pair(DataListCards(cardsList.toList()), null)
    }

    override fun getCardDetails(lid: Int, cid: Int): Pair<DataCard?, DataError?> {
        val c = cards[cid] ?: return Pair(null, MemoryError.CARD_NOT_FOUND)
        val list = getListDetails(lid).first?.name!!

        return Pair(
            DataCard(c.id, c.name, c.description, c.initDate, DataSimpleList(lid, list)),
            null
        )
    }

    override fun moveCard(cid: Int, lid: Int): Pair<DataCardMoved?, DataError?> {
        TODO("Not yet implemented")
    }

}