package pt.isel.ls.database

import pt.isel.ls.domain.Board
import pt.isel.ls.domain.Card
import pt.isel.ls.domain.TaskList
import pt.isel.ls.domain.User
import java.sql.Date

interface AppDatabase {
    fun createUser(name: String, email: String): User
    fun getUserDetails(uid: Int): User
    fun getUsersFromBoard(bid: Int): List<User>

    fun createBoard(uid: Int, name: String, description: String): Board
    fun getBoardDetails(bid: Int): Board
    fun addUserToBoard(uid: Int, bid: Int)
    fun getBoardsFromUser(uid: Int): List<Board>

    fun createList(bid: Int, name: String): TaskList
    fun getListsFromBoard(bid: Int): List<TaskList>
    fun getListDetails(lid: Int): TaskList

    fun createCard(lid: Int, name: String, description: String, dueDate: Date): Card
    fun getCardsFromList(lid: Int): List<Card>
    fun getCardDetails(cid: Int): Card
    fun moveCard(cid: Int, lid: Int)
}
