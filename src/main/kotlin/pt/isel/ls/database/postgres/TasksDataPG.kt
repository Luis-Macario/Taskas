package pt.isel.ls.database.postgres

import pt.isel.ls.database.*
import java.sql.Date

class TasksDataPG(url: String) : AppDatabase {
    override fun createUser(name: String, email: String): Pair<DataUserCreated?, DataError?> {
        TODO("Not yet implemented")
    }

    override fun getUserDetails(uid: Int): Pair<DataUser?, DataError?> {
        TODO("Not yet implemented")
    }

    override fun createBoard(uid: Int, name: String, description: String): Pair<DataBoardCreated?, DataError?> {
        TODO("Not yet implemented")
    }

    override fun getBoardDetails(bid: Int): Pair<DataBoard?, DataError?> {
        TODO("Not yet implemented")
    }

    override fun addUserToBoard(uid: Int, bid: Int): Pair<DataUserAdded?, DataError?> {
        TODO("Not yet implemented")
    }

    override fun getBoardsFromUser(uid: Int): Pair<DataUserBoards?, DataError?> {
        TODO("Not yet implemented")
    }

    override fun createList(bid: Int, name: String): Pair<DataListCreated?, DataError?> {
        TODO("Not yet implemented")
    }

    override fun getListsFromBoard(bid: Int): Pair<DataBoardLists?, DataError?> {
        TODO("Not yet implemented")
    }

    override fun getListDetails(lid: Int): Pair<DataList?, DataError?> {
        TODO("Not yet implemented")
    }

    override fun createCard(
        bid: Int,
        lid: Int,
        name: String,
        description: String,
        dueDate: Date?
    ): Pair<DataCardCreated?, DataError?> {
        TODO("Not yet implemented")
    }

    override fun getCardsFromList(lid: Int): Pair<DataListCards?, DataError?> {
        TODO("Not yet implemented")
    }

    override fun getCardDetails(bid: Int, cid: Int): Pair<DataCard?, DataError?> {
        TODO("Not yet implemented")
    }

    override fun moveCard(cid: Int, lid: Int): Pair<DataCardMoved?, DataError?> {
        TODO("Not yet implemented")
    }
}