package pt.isel.ls.unit.database.postgres

import org.postgresql.ds.PGSimpleDataSource
import pt.isel.ls.database.memory.ListNotFoundException
import pt.isel.ls.database.sql.TasksDataPostgres
import pt.isel.ls.domain.SimpleList
import java.sql.SQLException
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class ListTests {
    // TODO: FIX COMMENTED TESTS
    private val url = System.getenv("JDBC_DATABASE_URL")
    private val dataSource = PGSimpleDataSource().apply {
        this.setUrl(url)
    }
    private val db = TasksDataPostgres(url)
    private val tokenA = "7d444840-9dc0-11d1-b245-5ffdce74fad1"
    private val passwordA = "132513E5601D28F9DBDEBD2590514E171FEFEC9A6BE60417D79B8D626077C3FB"

    @BeforeTest
    fun cleanAndAddData() {
        dropTableAndAddData(dataSource)
    }


    @Test
    fun `createList creates a new list with the given the name and board id`() {
        val name = "To Do Week 10"
        val user: Int = db.createUser(tokenA, "Teste RFL", "a12346@alunos.isel.pt", passwordA)
        val board: Int = db.createBoard(user, "To Do testes".repeat(4), "ISEL project")

        val sut = db.createList(board, name)

        assertEquals(7, sut)
        assertEquals(name, db.getListDetails(sut).name)
        assertEquals(board, db.getListDetails(sut).bid)

        dataSource.connection.use {
            val stm = it.prepareStatement(
                """
                SELECT name, bid FROM tasklists WHERE  id = ?
                """.trimIndent()
            )
            stm.setInt(1, sut)

            val rs = stm.executeQuery()
            assertTrue(rs.next())
            assertEquals(name, rs.getString("name"))
            assertEquals(board, rs.getInt("bid"))
        }
    }

    @Test
    fun `getListsFromBoard returns the correct list of TaskList`() {
        val listName = "teste 123"
        val db = TasksDataPostgres(url)
        val user: Int = db.createUser(tokenA, "Teste RFL", "a12346@alunos.isel.pt", passwordA)
        val board: Int = db.createBoard(user, "To Do testes".repeat(4), "ISEL project")
        val newList = db.createList(board, listName)
        val newList2 = db.createList(board, listName)
        val newList3 = db.createList(board, listName)

        val listTaskList = listOf(
            SimpleList(newList, board, listName),
            SimpleList(newList2, board, listName),
            SimpleList(newList3, board, listName)
        )

        val sut = db.getListsFromBoard(board)

        assertEquals(3, sut.size)
        assertEquals(listTaskList, sut)
    }

    @Test
    fun `getListsFromBoard returns empty list if given bid with no Tasklist assigned`() {
        val db = TasksDataPostgres(url)

        val bid = 100
        val sut = db.getListsFromBoard(bid)

        assertEquals(0, sut.size)
        assertEquals(emptyList(), sut)
    }


    @Test
    fun `getListDetails return correct list details`() {
        val user: Int = db.createUser(tokenA, "Teste RFL", "a12346@alunos.isel.pt", passwordA)
        val board: Int = db.createBoard(user, "To Do testes".repeat(4), "ISEL project")
        val name = "To Do Week 10"

        val newList = db.createList(board, name)
        val sut = db.getListDetails(newList)

        assertEquals(newList, sut.id)
        assertEquals(board, sut.bid)
        assertEquals(name, sut.name)
    }

    @Test
    fun `getListDetails throws ListNotFoundException given wrong list id`() {
        val db = TasksDataPostgres(url)

        val msg = assertFailsWith<ListNotFoundException> {
            db.getListDetails(100)
        }

        assertEquals(ListNotFoundException.description, msg.description)
    }



    @Test
    fun `deleteList should throw SQLException if given non-existent id`() {
        val db = TasksDataPostgres(url)
        assertFailsWith<SQLException> {
            db.deleteList(-1)
        }
    }
}

