package pt.isel.ls.database

import java.sql.Date

interface AppDatabase {
    fun createUser(name: String, email: String): Pair<DataUserCreated?, DataError?>
    fun getUserDetails(uid: Int): Pair<DataUser?, DataError?>

    fun createBoard(uid: Int, name: String, description: String): Pair<DataBoardCreated?, DataError?>
    fun getBoardDetails(bid: Int): Pair<DataBoard?, DataError?>
    fun addUserToBoard(uid: Int, bid: Int): Pair<DataUserAdded?, DataError?>
    fun getBoardsFromUser(uid: Int): Pair<DataUserBoards?, DataError?>

    fun createList(bid: Int, name: String): Pair<DataListCreated?, DataError?>
    fun getListsFromBoard(bid: Int): Pair<DataBoardLists?, DataError?>
    fun getListDetails(lid: Int): Pair<DataList?, DataError?>

    fun createCard(
        bid: Int,
        lid: Int,
        name: String,
        description: String,
        dueDate: Date?
    ): Pair<DataCardCreated?, DataError?>

    fun getCardsFromList(lid: Int): Pair<DataListCards?, DataError?>
    fun getCardDetails(bid: Int, cid: Int): Pair<DataCard?, DataError?>
    fun moveCard(cid: Int, lid: Int): Pair<DataCardMoved?, DataError?>
}