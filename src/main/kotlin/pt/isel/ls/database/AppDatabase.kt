package pt.isel.ls.database

import pt.isel.ls.domain.Card
import pt.isel.ls.domain.SimpleBoard
import pt.isel.ls.domain.SimpleList
import pt.isel.ls.domain.User
import java.sql.Date

interface AppDatabase {
    fun createUser(token: String, name: String, email: String, password: String): Int
    fun loginUser(email: String): User
    fun getUserDetails(uid: Int): User
    fun getUsersFromBoard(bid: Int, skip: Int, limit: Int): List<User>
    fun checkEmailAlreadyExists(email: String): Boolean
    fun getAllAvailableUser(): List<User>

    fun createBoard(uid: Int, name: String, description: String): Int
    fun getBoardDetails(bid: Int): SimpleBoard
    fun addUserToBoard(uid: Int, bid: Int)
    fun getBoardsFromUser(uid: Int, skip: Int, limit: Int): List<SimpleBoard>
    fun checkUserAlreadyExistsInBoard(uid: Int, bid: Int): Boolean
    fun checkUserTokenExistsInBoard(token: String, bid: Int): Boolean
    fun checkBoardExists(bid: Int): Boolean
    fun checkBoardNameAlreadyExists(name: String): Boolean
    fun searchBoardsFromUser(uid: Int, skip: Int, limit: Int, searchQuery: String): List<SimpleBoard>

    fun createList(bid: Int, name: String): Int
    fun getListsFromBoard(bid: Int, skip: Int, limit: Int): List<SimpleList>
    fun getListDetails(lid: Int): SimpleList
    fun deleteList(lid: Int)
    fun checkListAlreadyExistsInBoard(name: String, bid: Int): Boolean

    fun createCard(lid: Int, name: String, description: String, initDate: Date, dueDate: Date): Int
    fun getCardsFromList(lid: Int, bid: Int, skip: Int, limit: Int): List<Card>
    fun getCardDetails(cid: Int): Card
    fun moveCard(cid: Int, lid: Int, cix: Int)
    fun deleteCard(cid: Int)
    fun tokenToId(bToken: String): Int
}
