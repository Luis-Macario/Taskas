package pt.isel.ls.unit.database.postgres

import org.postgresql.ds.PGSimpleDataSource
import pt.isel.ls.database.memory.CardNotFoundException
import pt.isel.ls.database.sql.TasksDataPostgres
import pt.isel.ls.services.utils.LIMIT_DEFAULT
import pt.isel.ls.services.utils.SKIP_DEFAULT
import java.sql.Date
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CardTests {
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
    fun `getCardDetails returns correct card details`() {
        val user: Int = db.createUser(tokenA, "Teste RFL", "a12346@alunos.isel.pt", passwordA)
        val board: Int = db.createBoard(user, "To Do testes".repeat(4), "ISEL project")
        val list: Int = db.createList(board, "Some work Testes Card")
        val cardName = "Card 1"
        val cardDescritpion = "Description 1"
        val initDate = Date.valueOf("2023-03-02")

        val card = db.createCard(list, cardName, cardDescritpion, initDate, Date.valueOf("2023-04-02"))

        // Get the card details from the db
        val sut = db.getCardDetails(card)

        assertEquals(card, sut.id)
        assertEquals(board, sut.bid)
        assertEquals(list, sut.lid)
        assertEquals(cardName, sut.name)
        assertEquals(cardDescritpion, sut.description)
        assertEquals(initDate, sut.initDate)
    }

    @Test
    fun `getCardsFromList returns correct list of cards`() {
        val user: Int = db.createUser(tokenA, "Teste RFL", "a12346@alunos.isel.pt", passwordA)
        val board: Int = db.createBoard(user, "To Do testes".repeat(4), "ISEL project")
        val list: Int = db.createList(board, "Some work Testes Card")
        val initDate = Date.valueOf("2023-03-02")

        val card1 = db.createCard(list, "Card 1", "Description 1", initDate, Date.valueOf("2023-04-02"))
        val card2 = db.createCard(list, "Card 2", "Description 2", initDate, Date.valueOf("2023-04-03"))
        val card3 = db.createCard(list, "Card 3", "Description 3", initDate, Date.valueOf("2023-04-04"))

        // Get the list of cards from the db
        val sut = db.getCardsFromList(list, board, SKIP_DEFAULT, LIMIT_DEFAULT)

        assertEquals(3, sut.size)
        assertEquals(
            listOf(
                db.getCardDetails(card1),
                db.getCardDetails(card2),
                db.getCardDetails(card3)
            ),
            sut
        )
    }

    @Test
    fun `getCardsFromList returns emptyList if given a board without cards`() {
        val user: Int = db.createUser(tokenA, "Teste RFL", "a12346@alunos.isel.pt", passwordA)
        val board: Int = db.createBoard(user, "To Do testes".repeat(4), "ISEL project")
        val list: Int = db.createList(board, "Some work Testes Card")
        val sut = db.getCardsFromList(list, board, SKIP_DEFAULT, LIMIT_DEFAULT)

        assertEquals(emptyList(), sut)
    }

    @Test
    fun `moveCard updates the card's list ID`() {
        val db = TasksDataPostgres(url)

        val cardDetails = db.getCardDetails(1)
        var oldList = db.getCardsFromList(cardDetails.lid, cardDetails.bid, SKIP_DEFAULT, LIMIT_DEFAULT)
        var moveToList = db.getCardsFromList(2, 1, SKIP_DEFAULT, LIMIT_DEFAULT)

        assertEquals(3, oldList.size)
        assertEquals(3, moveToList.size)

        db.moveCard(cardDetails.id, 2, 1)

        oldList = db.getCardsFromList(cardDetails.lid, cardDetails.bid, SKIP_DEFAULT, LIMIT_DEFAULT)
        moveToList = db.getCardsFromList(2, 1, SKIP_DEFAULT, LIMIT_DEFAULT)

        assertEquals(2, oldList.size)
        assertEquals(4, moveToList.size)
    }

    @Test
    fun `deleteCard should delete the list given the id`() {
        val user: Int = db.createUser(tokenA, "Teste RFL", "a12346@alunos.isel.pt", passwordA)
        val board: Int = db.createBoard(user, "To Do testes".repeat(4), "ISEL project")
        val list: Int = db.createList(board, "Some work Testes Card")
        val card =
            db.createCard(list, "Card 1", "Description 1", Date.valueOf("2023-03-02"), Date.valueOf("2023-04-02"))

        db.deleteCard(card)
        assertFailsWith<CardNotFoundException> {
            db.getCardDetails(card)
        }
    }
}
