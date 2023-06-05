package pt.isel.ls.unit.services

import org.junit.jupiter.api.Test
import pt.isel.ls.api.dto.card.MoveCardRequest
import pt.isel.ls.database.memory.TasksDataMem
import pt.isel.ls.domain.Card
import pt.isel.ls.services.boards.BoardServices
import pt.isel.ls.services.cards.CardServices
import pt.isel.ls.services.lists.ListServices
import pt.isel.ls.services.users.UserServices
import pt.isel.ls.services.utils.exceptions.IllegalCardAccessException
import pt.isel.ls.services.utils.exceptions.IllegalListAccessException
import pt.isel.ls.services.utils.exceptions.IllegalMoveCardRequestException
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CardServicesTests {
    private val database = TasksDataMem()
    private val uServices = UserServices(database)
    private val bServices = BoardServices(database)
    private val lServices = ListServices(database)
    private val cServices = CardServices(database)
    private val userA = uServices.createUser("Test User", "test_user@isel.pt", "teste12345")
    private val userB = uServices.createUser("New Test User", "new_test_user@isel.pt", "teste12345")

    // createCard
    @Test
    fun `Creating card with valid token should return the correct card`() {
        val user = userA
        val board = bServices.createBoard(user.token, "TestProject", "TestDescription")
        val list = lServices.createList(user.token, board.id, "TestList")
        val card = cServices.createCard(user.token, list.id, "TestCard", "TestDescription", "2023-04-23", "2023-05-23")

        assertEquals(
            database.cards[0]?.id,
            card
        )
    }

    @Test
    fun `Creating card with invalid token should throw IllegalListAccessException`() {
        val user = userA
        val userWithNoAccess = userB
        val board = bServices.createBoard(user.token, "TestProject", "TestDescription")
        val list = lServices.createList(user.token, board.id, "Test List 1234")

        assertFailsWith<IllegalListAccessException> {
            cServices.createCard(
                userWithNoAccess.token,
                list.id,
                "TestCard",
                "TestDescription",
                "2023-04-23",
                "2023-05-23"
            )
        }
    }

    // getCardDetails
    @Test
    fun `Getting a card with a valid token and card id should return the correct card object`() {
        val user = userA
        val board = bServices.createBoard(user.token, "TestProject", "TestDescription")
        val list = lServices.createList(user.token, board.id, "TestList")
        val card = cServices.createCard(user.token, list.id, "TestCard", "TestDescription","2023-04-23","2023-05-23")

        assertEquals(cServices.getCardDetails(user.token, card).id, card)
    }

    @Test
    fun `Calling getCardDetails with invalid token should throw IllegalCardAccessException`() {
        val user = userA
        val userWithNoAccess = userB
        val board = bServices.createBoard(user.token, "TestProject", "TestDescription")
        val list = lServices.createList(user.token, board.id, "TestList")
        val card = cServices.createCard(user.token, list.id, "TestCard", "TestDescription", "2023-04-23","2023-05-23")

        assertFailsWith<IllegalCardAccessException> {
            cServices.getCardDetails(
                userWithNoAccess.token,
                card
            )
        }
    }

    // moveCard
    @Test
    fun `Calling moveCard with valid token, card id and destination list should be successful`() {
        val user = userA
        val board = bServices.createBoard(user.token, "TestProject", "TestDescription")
        val list0 = lServices.createList(user.token, board.id, "TestList0")
        val list1 = lServices.createList(user.token, board.id, "TestList1")

        val card = cServices.createCard(user.token, list0.id, "TestList0", "TestList0", "2023-04-23","2023-05-23")

        assertEquals(cServices.getCardDetails(user.token, card), lServices.getCardsFromList(user.token, list0.id)[0])
        assertEquals(lServices.getCardsFromList(user.token, list1.id), emptyList<Card>())

        cServices.moveCard(user.token, card, MoveCardRequest(list1.id, 0))

        assertEquals(cServices.getCardDetails(user.token, card), lServices.getCardsFromList(user.token, list1.id)[0])
        assertEquals(lServices.getCardsFromList(user.token, list0.id), emptyList<Card>())
    }

    @Test
    fun `Calling moveCard with invalid token should throw IllegalCardAccessException`() {
        val user = userA
        val userWithNoAccess = userB
        val board = bServices.createBoard(user.token, "TestProject", "TestDescription")
        val list0 = lServices.createList(user.token, board.id, "TestList0")
        val list1 = lServices.createList(user.token, board.id, "TestList1")

        val card = cServices.createCard(user.token, list0.id, "TestList0", "TestList0", "2023-04-23","2023-05-23")

        assertFailsWith<IllegalCardAccessException> {
            cServices.moveCard(
                userWithNoAccess.token,
                card,
                MoveCardRequest(list1.id,0)
            )
        }
    }

    @Test
    fun `Calling moveCard with invalid list id should throw IllegalMoveCardRequestException`() {
        val user = userA
        val board0 = bServices.createBoard(user.token, "TestProject0", "TestDescription")
        val board1 = bServices.createBoard(user.token, "TestProject1", "TestDescription")
        val list0 = lServices.createList(user.token, board0.id, "TestList0")
        val list1 = lServices.createList(user.token, board1.id, "TestList0")

        val card = cServices.createCard(user.token, list0.id, "TestList0", "TestList0", "2023-04-23","2023-05-23")

        assertFailsWith<IllegalMoveCardRequestException> {
            cServices.moveCard(
                user.token,
                card,
                MoveCardRequest(list1.id, 0)
            )
        }
    }
}
