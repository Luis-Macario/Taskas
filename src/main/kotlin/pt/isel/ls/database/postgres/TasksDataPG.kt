package pt.isel.ls.database.postgres

import pt.isel.ls.database.*
import java.sql.Date

class TasksDataPG(private val url: String) : AppDatabase {
    override fun createUser(name: String, email: String): DataUserCreated {
        TODO("Not yet implemented")
    }

    override fun getUserDetails(uid: Int): DataUser {
        TODO("Not yet implemented")
    }

    override fun createBoard(uid: Int, name: String, description: String): DataBoardCreated {
        TODO("Not yet implemented")
    }

    override fun getBoardDetails(bid: Int): DataBoard {
        TODO("Not yet implemented")
    }

    override fun addUserToBoard(uid: Int, bid: Int): DataUserAdded {
        TODO("Not yet implemented")
    }

    override fun getBoardsFromUser(uid: Int): DataUserBoards {
        TODO("Not yet implemented")
    }

    override fun createList(bid: Int, name: String): DataListCreated {
        TODO("Not yet implemented")
    }

    override fun getListsFromBoard(bid: Int): DataBoardLists {
        TODO("Not yet implemented")
    }

    override fun getListDetails(lid: Int): DataList {
        TODO("Not yet implemented")
    }

    override fun createCard(bid: Int, lid: Int, name: String, description: String, dueDate: Date): DataCardCreated {
        TODO("Not yet implemented")
    }

    override fun getCardsFromList(lid: Int): DataListCards {
        TODO("Not yet implemented")
    }

    override fun getCardDetails(lid: Int, cid: Int): DataCard {
        TODO("Not yet implemented")
    }

    override fun moveCard(cid: Int, lid: Int): DataCardMoved {
        TODO("Not yet implemented")
    }

}