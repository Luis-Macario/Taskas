package pt.isel.ls
import java.sql.Date
import java.util.*

class DataMem {

    var userId: Int = 0
    var boardId: Int = 0
    var listId: Int = 0
    var cardId: Int = 0

    val users:  MutableMap<Int, User> = mutableMapOf()
    val boards: MutableMap<Int, Board> = mutableMapOf()
    val lists:  MutableMap<Int, List> = mutableMapOf()
    val cards:  MutableMap<Int, Card> = mutableMapOf()

    //Not sure about this Pair(token,id)
    fun createUser(name: String, email: String): Pair<String, Int>{
        val token = UUID.randomUUID().toString()
        val id = userId
        val boards = listOf<Int>()
        userId += 1

        users[id] = User(id, boards, name, email, token)
        return Pair(token, id)
    }

    fun getUser(uId: Int) :User {
        val user = users[uId]
        requireNotNull(user) {"No User found with that Id"}

        return user
    }

    //Can a board be created without users??
    fun createBoard(name: String, description: String, uId: Int): Int{
        val id = boardId
        val user = listOf<Int>(uId)
        val lists = listOf<Int>()
        boardId += 1

        boards[id] = Board(user, id, lists, name, description)
        return id
    }

    fun addUserToBoard(bId: Int, uId: Int){
        val newUsersList = (boards[bId]?.uid)?.plus(uId)
        requireNotNull(newUsersList) {"Board not found"}

        boards[bId]?.uid = newUsersList
    }

    fun getBoard(bId: Int): Board{
        val board = boards[bId]
        requireNotNull(board)

        return board
    }

    fun getUserAvailableBoards(uId: Int): kotlin.collections.List<Int> {
        val userBoards = mutableListOf<Int>()

        users[uId]?.bId?.forEach {
            userBoards += it
        }
        return userBoards.toList()
    }


    fun createList(name: String ,bId: Int): Int{
        val id = listId
        lists[id] = List(listOf(bId), id, name)
        listId += 1

        return id
    }

    fun getListDetails(listId: Int): List {
        val list = lists[listId]
        requireNotNull(list)

        return list
    }

    fun getBoardLists(bId: Int): kotlin.collections.List<List>{
        val boardLists = mutableListOf<List>()

        boards[bId]?.lId?.forEach{
            boardLists += getListDetails(it)

        }

        return boardLists.toList()
    }

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

    fun getSetOfCards(lid: Int) : kotlin.collections.List<Int>{
        val listCards = mutableListOf<Int>()
        cards.values.forEach {
            if(it.lid == lid) listCards += it.id
        }

        return listCards.toList()
    }

    fun getCardInformation(cId: Int): Card{
        val cardInfo = cards[cId]
        requireNotNull(cardInfo) { "Card not found"}

        return cardInfo
    }

    //Probably not the best solution
    fun moveCard(cId: Int, lIdTo: Int){
        val card = getCardInformation(cId)
        cards[cId] = card.copy(lid = lIdTo)
    }

}