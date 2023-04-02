package pt.isel.ls.unit.database.postgres

import org.postgresql.ds.PGSimpleDataSource
import pt.isel.ls.database.memory.CardNotFoundException
import pt.isel.ls.database.memory.ListNotFoundException
import pt.isel.ls.database.sql.TasksDataPostgres
import java.sql.Date
import java.sql.SQLException
import kotlin.test.*

class CardTests {
    private val dataSource = PGSimpleDataSource().apply {
        this.setUrl(System.getenv("JDBC_DATABASE_URL"))
    }

    @BeforeTest
    fun cleanAndAddData() {
        dropTableAndAddData(dataSource)
    }

    @Test
    fun `createCard with valid parameters should create a card and return it`() {
        val db = TasksDataPostgres()

        val lid = 1
        val name = "Unit tests"
        val description = "Implement unit tests"
        val dueDate = Date.valueOf("2023-04-02")

        val sut = db.createCard(
            lid = lid,
            name = name,
            description = description,
            dueDate = dueDate
        )

        assertEquals(lid, sut.lid)
        assertEquals(name, sut.name)
        assertEquals(description, sut.description)
        assertEquals(dueDate, sut.initDate)
    }

    @Test
    fun `createCard with invalid list ID should throw an ListNotFoundException`() {
        val tasksData = TasksDataPostgres()
        val listId = 1234
        val name = "Card name"
        val description = "Card description"
        val dueDate = Date.valueOf("2023-04-02")

        assertFailsWith<ListNotFoundException> {
            tasksData.createCard(listId, name, description, dueDate)
        }
    }

    @Test
    fun `getCardsFromList returns correct list of cards`() {
        val db = TasksDataPostgres()

        val board = db.createBoard(1,"TODO Test Para Chess App","something to do" )
        val list = db.createList(board.id, "My List")

        val card1 = db.createCard(list.id, "Card 1", "Description 1", Date.valueOf("2023-04-02"))
        val card2 = db.createCard(list.id, "Card 2", "Description 2", Date.valueOf("2023-04-03"))
        val card3 = db.createCard(list.id, "Card 3", "Description 3", Date.valueOf("2023-04-04"))

        // Get the list of cards from the db
        val sut = db.getCardsFromList(list.id)

        assertEquals(3, sut.size)
        assertEquals(listOf(card1, card2, card3), sut)
    }

    @Test
    fun `getCardsFromList returns emptyList if given a board without cards`() {
        val db = TasksDataPostgres()
        val list = db.createList(1, "Test List")

        val sut = db.getCardsFromList(list.id)

        assertEquals(emptyList(), sut)
    }

    @Test
    fun `getCardDetails returns correct card details`() {
        val db = TasksDataPostgres()

        val board = db.createBoard(1, "TODO Test Para Chess App", "something to do")
        val list = db.createList(board.id, "List 1")
        val card = db.createCard(list.id, "Card 1", "Description 1", Date.valueOf("2023-04-02"))

        // Get the card details from the db
        val sut = db.getCardDetails(card.id)

        assertEquals(card.id, sut.id)
        assertEquals(card.bid, sut.bid)
        assertEquals(card.lid, sut.lid)
        assertEquals(card.name, sut.name)
        assertEquals(card.description, sut.description)
        assertEquals(card.initDate, sut.initDate)
    }

    @Test
    fun `getCardDetails throws CardNotFoundException for non-existent card ID`() {
        val db = TasksDataPostgres()

        assertFailsWith<CardNotFoundException> {
            db.getCardDetails(-1)
        }
    }

    @Test
    fun `moveCard updates the card's list ID`() {
        val db = TasksDataPostgres()

        val board = db.createBoard(1, "TODO Test Para Chess App", "something to do")
        val list1 = db.createList(board.id, "List 1")
        val list2 = db.createList(board.id, "List 2")
        val card = db.createCard(list1.id, "Card 1", "Description 1", Date.valueOf("2023-04-02"))

        db.moveCard(card.id, list2.id)

        val sut = db.getCardDetails(card.id)

        assertEquals(list2.id, sut.lid)
    }

    @Test
    fun `moveCard throws SQLException if given wrong card ID or list ID`() {
        val db = TasksDataPostgres()

        val board = db.createBoard(1, "TODO Test Para Chess App", "something to do")
        val list1 = db.createList(board.id, "List 1")
        val list2 = db.createList(board.id, "List 2")
        db.createCard(list1.id, "Card 1", "Description 1", Date.valueOf("2023-04-02"))

        val msg = assertFailsWith<SQLException> {
            db.moveCard(1234, list2.id) }

        assertEquals("Updating card.lid failed, no rows affected.", msg.message)
    }

}
