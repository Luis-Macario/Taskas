package pt.isel.ls.database.sql

import org.postgresql.ds.PGSimpleDataSource
import pt.isel.ls.database.AppDatabase
import pt.isel.ls.domain.Board
import pt.isel.ls.domain.Card
import pt.isel.ls.domain.TaskList
import pt.isel.ls.domain.User
import java.sql.Date

class TasksDataPostgres : AppDatabase {
    private val dataSource = PGSimpleDataSource().apply {
        this.setUrl(System.getenv("JDBC_DATABASE_URL"))
    }
    override fun createUser(token: String, name: String, email: String): User {
        TODO("Not yet implemented")
    }
    override fun getUserDetails(uid: Int): User {
        TODO("Not yet implemented")
    }

    override fun getUsersFromBoard(bid: Int): List<User> {
        TODO("Not yet implemented")
    }

    override fun createBoard(uid: Int, name: String, description: String): Board {
        TODO("Not yet implemented")
    }

    override fun getBoardDetails(bid: Int): Board {
        TODO("Not yet implemented")
    }

    override fun addUserToBoard(uid: Int, bid: Int) {
        TODO("Not yet implemented")
    }

    override fun getBoardsFromUser(uid: Int): List<Board> {
        TODO("Not yet implemented")
    }

    override fun createList(bid: Int, name: String): TaskList {
        TODO("Not yet implemented")
    }

    override fun getListsFromBoard(bid: Int): List<TaskList> {
        TODO("Not yet implemented")
    }

    override fun getListDetails(lid: Int): TaskList {
        TODO("Not yet implemented")
    }

    override fun createCard(lid: Int, name: String, description: String, dueDate: Date): Card {
        TODO("Not yet implemented")
    }

    override fun getCardsFromList(lid: Int): List<Card> {
        TODO("Not yet implemented")
    }

    override fun getCardDetails(cid: Int): Card {
        TODO("Not yet implemented")
    }

    override fun moveCard(cid: Int, lid: Int) {
        TODO("Not yet implemented")
    }

    override fun tokenToId(bToken: String): Int {
        TODO("Not yet implemented")
    }
}
