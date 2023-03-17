package pt.isel.ls
import java.sql.Date
import java.util.*

class DataMem {

    private var userId: Int = 0
    private var boardId: Int = 0
    private var listId: Int = 0
    private var cardId: Int = 0
    private var userBoardId: Int = 0

    val users:  MutableMap<Int, User> = mutableMapOf()
    val boards: MutableMap<Int, Board> = mutableMapOf()
    val userBoard: MutableMap<Int, UserBoard> = mutableMapOf()
    val taskLists:  MutableMap<Int, TaskList> = mutableMapOf()
    val cards:  MutableMap<Int, Card> = mutableMapOf()

    //Not sure about this Pair(token,id)
    fun createUser(name: String, email: String): Pair<String, Int>{
        val token = UUID.randomUUID().toString()
        val id = userId
        userId += 1

        users[id] = User(id, name, email, token)
        return Pair(token, id)
    }

    fun getUser(uId: Int) :User {
        val user = users[uId]
        requireNotNull(user) {"No User found with that Id"}

        return user
    }

    //Can a board be created without users??
    fun createBoard(name: String, description: String): Int{
        val id = boardId
        boardId += 1

        boards[id] = Board(id, name, description)
        return id
    }

    fun addUserToBoard(bId: Int, uId: Int){
        val id =  userBoardId
        userBoard[id] = UserBoard(uId, bId)
        userBoardId += 1

    }

    fun getBoard(bId: Int): Board{
        val board = boards[bId]
        requireNotNull(board)

        return board
    }

    fun getUserAvailableBoards(uId: Int) =
         userBoard.filter { it.value.uId == uId }.keys.toList()



    fun createTaskList(name: String ,bId: Int): Int{
        val id = listId
        taskLists[id] = TaskList(bId, id, name)
        listId += 1

        return id
    }

    fun getTaskListDetails(listId: Int): TaskList {
        val list = taskLists[listId]
        requireNotNull(list)

        return list
    }

    fun getTaskListsOfBoard(bId: Int) =
        taskLists.filter { it.value.bid == bId }.keys.toList()


    fun createCard(
        uid: Int,
        bid: Int,
        lid: Int,
        name: String,
        description: String,
        initDate: Date,
        finishDate: Date
    ): Int{
        val id = cardId
        cardId += 1

        cards[id] = Card(uid, bid, lid, id, name, description, initDate, finishDate)
        return id
    }

    fun getSetOfCards(lid: Int) =
        cards.filter { it.value.lid == lid }.keys.toList()


    fun getCardInformation(cId: Int): Card{
        val cardInfo = cards[cId]
        requireNotNull(cardInfo) { "Card not found"}

        return cardInfo
    }

    //Probably not the best solution
    fun moveCard(cId: Int, lIdTo: Int?){
        val card = getCardInformation(cId)
        cards[cId] = card.copy(lid = lIdTo)
    }

}