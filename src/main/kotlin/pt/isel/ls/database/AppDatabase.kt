package pt.isel.ls.database

import java.sql.Date

interface AppDatabase {
    fun createUser(name: String, email: String): DataUserCreated
    fun getUserDetails(uid: Int): DataUser

    fun createBoard(uid: Int, name: String, description: String): DataBoardCreated
    fun getBoardDetails(bid: Int): DataBoard
    fun addUserToBoard(uid: Int, bid: Int): DataUserAdded
    fun getBoardsFromUser(uid: Int): DataUserBoards

    fun createList(bid: Int, name: String): DataListCreated
    fun getListsFromBoard(bid: Int): DataBoardLists
    fun getListDetails(lid: Int): DataList

    fun createCard(bid: Int, lid: Int, name: String, description: String, dueDate: Date): DataCardCreated
    fun getCardsFromList(lid: Int): DataListCards
    fun getCardDetails(cid: Int): DataCard
    fun moveCard(cid: Int, lid: Int): DataCardMoved
}