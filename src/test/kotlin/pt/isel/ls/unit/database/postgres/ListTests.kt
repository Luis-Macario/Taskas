package pt.isel.ls.unit.database.postgres

import org.postgresql.ds.PGSimpleDataSource
import pt.isel.ls.database.memory.ListNotFoundException
import pt.isel.ls.database.sql.TasksDataPostgres
import pt.isel.ls.domain.Card
import pt.isel.ls.domain.TaskList
import java.sql.SQLException
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ListTests {
    // TODO: FIX COMMENTED TESTS
    private val url = System.getenv("JDBC_DATABASE_URL")

    private val dataSource = PGSimpleDataSource().apply {
        this.setUrl(url)
    }

    @BeforeTest
    fun cleanAndAddData() {
        dropTableAndAddData(dataSource)
    }

    /*
    @Test
    fun `createList creates a new list with the given the name and board id`() {
        val db = TasksDataPostgres(url)

        val name = "To Do Week 10"
        val bid = 1 // Board(1, 'Start backend work #123', 'Backend work for the Compose Desktop project')

        val sut = db.createList(bid, name)

        assertEquals(7, sut.id)
        assertEquals(name, sut.name)
        assertEquals(bid, sut.bid)

        dataSource.connection.use {
            val stm = it.prepareStatement(
                """
                SELECT name, bid FROM tasklists WHERE  id = ?
                """.trimIndent()
            )
            stm.setInt(1, sut.id)

            val rs = stm.executeQuery()
            assertTrue(rs.next())
            assertEquals(name, rs.getString("name"))
            assertEquals(bid, rs.getInt("bid"))
        }
    }
    */
    @Test
    fun `getListsFromBoard returns the correct list of TaskList`() {
        val db = TasksDataPostgres(url)

        val bid = 1
        val cards = listOf<Card>()
        val listTaskList = listOf(
            TaskList(1, bid, "To Do", true, cards),
            TaskList(2, bid, "Doing", true, cards),
            TaskList(3, bid, "Done", true, cards)
        )

        val sut = db.getListsFromBoard(bid)

        assertEquals(3, sut.size)
        // assertEquals(listTaskList, sut)
    }

    @Test
    fun `getListsFromBoard returns empty list if given bid with no Tasklist assigned`() {
        val db = TasksDataPostgres(url)

        val bid = 100
        val sut = db.getListsFromBoard(bid)

        assertEquals(0, sut.size)
        assertEquals(emptyList(), sut)
    }

    /*
    @Test
    fun `getListDetails return correct list details`() {
        val db = TasksDataPostgres(url)

        val name = "To Do Week 10"
        val bid = 1 // Board(1, 'Start backend work #123', 'Backend work for the Compose Desktop project')

        val newList = db.createList(bid, name)
        val sut = db.getListDetails(newList.id)

        assertEquals(newList.id, sut.id)
        assertEquals(newList.bid, sut.bid)
        assertEquals(newList.name, sut.name)
    }
    */
    @Test
    fun `getListDetails throws ListNotFoundException given wrong list id`() {
        val db = TasksDataPostgres(url)

        val msg = assertFailsWith<ListNotFoundException> {
            db.getListDetails(100)
        }

        assertEquals(ListNotFoundException.description, msg.description)
    }

    /*
    @Test
    fun `deleteList should delete the list given the id`() {
        val db = TasksDataPostgres(url)

        val newList = db.createList(1, "TODO WEEk 12")
        db.deleteList(newList.id)

        assertFailsWith<ListNotFoundException> {
            db.getListDetails(newList.id)
        }
    }
    */
    @Test
    fun `deleteList should throw SQLException if given non-existent id`() {
        val db = TasksDataPostgres(url)

        assertFailsWith<SQLException> {
            db.deleteList(-1)
        }
    }
}
