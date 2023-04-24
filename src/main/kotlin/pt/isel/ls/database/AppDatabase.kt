package pt.isel.ls.database

import pt.isel.ls.domain.Board
import pt.isel.ls.domain.Card
import pt.isel.ls.domain.SimpleBoard
import pt.isel.ls.domain.SimpleList
import pt.isel.ls.domain.User
import java.sql.Date

interface AppDatabase {
    fun getNextId() : Int
    fun createUser(user : User)
    fun getUserDetails(uid: Int): User
    fun getUsersFromBoard(bid: Int): List<User>
    fun checkEmailAlreadyExists(email: String): Boolean

    fun createBoard(uid: Int, name: String, description: String): Board
    fun getBoardDetails(bid: Int): SimpleBoard
    fun addUserToBoard(uid: Int, bid: Int)
    fun getBoardsFromUser(uid: Int): List<SimpleBoard>

    fun createList(bid: Int, name: String): SimpleList
    fun getListsFromBoard(bid: Int): List<SimpleList>
    fun getListDetails(lid: Int): SimpleList
    fun checkListsFromSameBoard(l1: Int, l2: Int): Boolean
    fun deleteList(lid: Int)

    fun createCard(lid: Int, name: String, description: String, initDate: Date, dueDate: Date): Card
    fun getCardsFromList(lid: Int, bid: Int): List<Card>
    fun getCardDetails(cid: Int): Card
    fun moveCard(cid: Int, lid: Int, cix: Int)
    fun deleteCard(cid: Int)
    fun tokenToId(bToken: String): Int
}
