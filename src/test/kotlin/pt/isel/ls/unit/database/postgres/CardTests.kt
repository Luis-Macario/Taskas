package pt.isel.ls.unit.database.postgres

import org.postgresql.ds.PGSimpleDataSource
import pt.isel.ls.database.memory.CardNotFoundException
import pt.isel.ls.database.memory.ListNotFoundException
import pt.isel.ls.database.sql.TasksDataPostgres
import java.sql.Date
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CardTests {
    // TODO: FIX COMMENTED TESTS
    private val url = System.getenv("JDBC_DATABASE_URL")

    private val dataSource = PGSimpleDataSource().apply {
        this.setUrl(url)
    }

    @BeforeTest
    fun cleanAndAddData() {
        dropTableAndAddData(dataSource)
    }

    @Test
    fun `createCard with valid parameters should create a card and return it`() {
        val db = TasksDataPostgres(url)

        val lid = 1
        val name = "Unit tests"
        val description = "Implement unit tests"
        val initDate = Date.valueOf("2023-03-02")
        val dueDate = Date.valueOf("2023-04-02")

        val sut = db.createCard(
            lid = lid,
            name = name,
            description = description,
            initDate = initDate,
            dueDate = dueDate
        )

        assertEquals(lid, sut.lid)
        assertEquals(name, sut.name)
        assertEquals(description, sut.description)
        assertEquals(dueDate, sut.initDate)
    }

    @Test
    fun `createCard with invalid list ID should throw an ListNotFoundException`() {
        val tasksData = TasksDataPostgres(url)
        val listId = 1234
        val name = "Card name"
        val description = "Card description"
        val dueDate = Date.valueOf("2023-04-02")
        val initDate = Date.valueOf("2023-03-02")

        assertFailsWith<ListNotFoundException> {
            tasksData.createCard(listId, name, description, initDate, dueDate)
        }
    }

    /*
    @Test
    fun `getCardsFromList returns correct list of cards`() {
        val db = TasksDataPostgres(url)

        val board = db.createBoard(1, "TODO Test Para Chess App", "something to do")
        val list = db.createList(board.id, "My List")
        val initDate = Date.valueOf("2023-03-02")

        val card1 = db.createCard(list.id, "Card 1", "Description 1",initDate, Date.valueOf("2023-04-02"))
        val card2 = db.createCard(list.id, "Card 2", "Description 2",initDate, Date.valueOf("2023-04-03"))
        val card3 = db.createCard(list.id, "Card 3", "Description 3",initDate, Date.valueOf("2023-04-04"))

        // Get the list of cards from the db
        val sut = db.getCardsFromList(list.id, board.id)

        assertEquals(3, sut.size)
        assertEquals(listOf(card1, card2, card3), sut)
    }

    @Test
    fun `getCardsFromList returns emptyList if given a board without cards`() {
        val db = TasksDataPostgres(url)
        val list = db.createList(1, "Test List")

        val sut = db.getCardsFromList(list.id, 1)

        assertEquals(emptyList(), sut)
    }

    @Test
    fun `getCardDetails returns correct card details`() {
        val db = TasksDataPostgres(url)
        val initDate = Date.valueOf("2023-03-02")
        val board = db.createBoard(1, "TODO Test Para Chess App", "something to do")
        val list = db.createList(board.id, "List 1")
        val card = db.createCard(list.id, "Card 1", "Description 1",initDate, Date.valueOf("2023-04-02"))

        // Get the card details from the db
        val sut = db.getCardDetails(card.id)

        assertEquals(card.id, sut.id)
        assertEquals(card.bid, sut.bid)
        assertEquals(card.lid, sut.lid)
        assertEquals(card.name, sut.name)
        assertEquals(card.description, sut.description)
        assertEquals(card.initDate, sut.initDate)
    }
    */
    @Test
    fun `getCardDetails throws CardNotFoundException for non-existent card ID`() {
        val db = TasksDataPostgres(url)

        assertFailsWith<CardNotFoundException> {
            db.getCardDetails(-1)
        }
    }

    @Test
    fun `moveCard updates the card's list ID`() {
        val db = TasksDataPostgres(url)

        val cardDetails = db.getCardDetails(1)
        println(cardDetails)
        var oldList = db.getCardsFromList(cardDetails.lid, cardDetails.bid)
        var moveToList = db.getCardsFromList(2, 1)

        assertEquals(3, oldList.size)
        assertEquals(3, moveToList.size)

        db.moveCard(cardDetails.id, 2, 1)

        oldList = db.getCardsFromList(cardDetails.lid, cardDetails.bid)
        moveToList = db.getCardsFromList(2, 1)

        assertEquals(2, oldList.size)
        assertEquals(4, moveToList.size)
    }

    /*
    @Test
    fun `moveCard throws SQLException if given wrong card ID or list ID`() {
        val db = TasksDataPostgres(url)

        val board = db.createBoard(1, "TODO Test Para Chess App", "something to do")
        val list1 = db.createList(board.id, "List 1")
        val list2 = db.createList(board.id, "List 2")
        db.createCard(list1.id, "Card 1", "Description 1", Date.valueOf("2023-03-02"), Date.valueOf("2023-04-02"))

        // TODO("Find how to throw sql exception when using plpgsql")
        // val msg = assertFailsWith<SQLException> {
        //   db.moveCard(1234, list2.id, 0)
        // }

        // assertEquals("Updating card.lid failed, no rows affected.", msg.message)
    }

    @Test
    fun `deleteCard should delete the list given the id`() {
        val db = TasksDataPostgres(url)

        val board = db.createBoard(1, "TODO Test Para Chess App", "something to do")
        val list = db.createList(board.id, "List 1")
        val card = db.createCard(list.id, "Card 1", "Description 1",Date.valueOf("2023-03-02"), Date.valueOf("2023-04-02"))

        db.deleteCard(card.id)

        assertFailsWith<CardNotFoundException> {
            db.getCardDetails(card.id)
        }
    }
    */
    @Test
    fun `deleteCard should throw SQLException if given non-existent id`() {
        val db = TasksDataPostgres(url)
        // TODO("Find how to throw sql exception when using plpgsql")
        // assertFailsWith<SQLException> {
        // db.deleteCard(-1)
        // }
    }
}
