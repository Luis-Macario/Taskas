package pt.isel.ls.services

import org.junit.Test
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
import java.util.UUID
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CardServicesTests {
    private val database = TasksDataMem()
    private val uServices = UserServices(database)
    private val bServices = BoardServices(database)
    private val lServices = ListServices(database)
    private val cServices = CardServices(database)

    // createCard
    @Test
    fun `Creating card with valid token should return the correct card`() {
        val user = uServices.createUser("Test User", "test_user@isel.pt")
        val board = bServices.createBoard(user.token, "TestProject", "TestDescription")
        val list = lServices.createList(user.token, board.id, "TestList")
        val card = cServices.createCard(user.token, list.id, "TestCard", "TestDescription")

        assertEquals(
            database.cards[0],
            card
        )
    }

    @Test
    fun `Creating card with invalid token should throw IllegalListAccessException`() {
        val user = uServices.createUser("Test User", "test_user@isel.pt")
        val board = bServices.createBoard(user.token, "TestProject", "TestDescription")
        val list = lServices.createList(user.token, board.id, "TestList")

        assertFailsWith<IllegalListAccessException> {
            cServices.createCard(
                UUID.randomUUID().toString(),
                list.id,
                "TestCard",
                "TestDescription"
            )
        }
    }

    // getCardDetails
    @Test
    fun `Getting a card with a valid token and card id should return the correct card object`() {
        val user = uServices.createUser("Test User", "test_user@isel.pt")
        val board = bServices.createBoard(user.token, "TestProject", "TestDescription")
        val list = lServices.createList(user.token, board.id, "TestList")
        val card = cServices.createCard(user.token, list.id, "TestCard", "TestDescription")

        assertEquals(cServices.getCardDetails(user.token, card.id), card)
    }

    @Test
    fun `Calling getCardDetails with invalid token should throw IllegalCardAccessException`() {
        val user = uServices.createUser("Test User", "test_user@isel.pt")
        val board = bServices.createBoard(user.token, "TestProject", "TestDescription")
        val list = lServices.createList(user.token, board.id, "TestList")
        val card = cServices.createCard(user.token, list.id, "TestCard", "TestDescription")

        assertFailsWith<IllegalCardAccessException> {
            cServices.getCardDetails(
                UUID.randomUUID().toString(),
                card.id
            )
        }
    }

    // moveCard
    @Test
    fun `Calling moveCard with valid token, card id and destination list should be successful`() {
        val user = uServices.createUser("Test User", "test_user@isel.pt")
        val board = bServices.createBoard(user.token, "TestProject", "TestDescription")
        val list0 = lServices.createList(user.token, board.id, "TestList0")
        val list1 = lServices.createList(user.token, board.id, "TestList1")

        val card = cServices.createCard(user.token, list0.id, "TestList0", "TestList0")

        assertEquals(cServices.getCardDetails(user.token, card.id), lServices.getCardsFromList(user.token, list0.id)[0])
        assertEquals(lServices.getCardsFromList(user.token, list1.id), emptyList<Card>())

        cServices.moveCard(user.token, card.id, MoveCardRequest(list1.id))

        assertEquals(cServices.getCardDetails(user.token, card.id), lServices.getCardsFromList(user.token, list1.id)[0])
        assertEquals(lServices.getCardsFromList(user.token, list0.id), emptyList<Card>())
    }

    @Test
    fun `Calling moveCard with invalid token should throw IllegalCardAccessException`() {
        val user = uServices.createUser("Test User", "test_user@isel.pt")
        val board = bServices.createBoard(user.token, "TestProject", "TestDescription")
        val list0 = lServices.createList(user.token, board.id, "TestList0")
        val list1 = lServices.createList(user.token, board.id, "TestList1")

        val card = cServices.createCard(user.token, list0.id, "TestList0", "TestList0")

        assertFailsWith<IllegalCardAccessException> {
            cServices.moveCard(
                UUID.randomUUID().toString(),
                card.id,
                MoveCardRequest(list1.id)
            )
        }
    }

    @Test
    fun `Calling moveCard with invalid list id should throw IllegalMoveCardRequestException`() {
        val user = uServices.createUser("Test User", "test_user@isel.pt")
        val board0 = bServices.createBoard(user.token, "TestProject0", "TestDescription")
        val board1 = bServices.createBoard(user.token, "TestProject1", "TestDescription")
        val list0 = lServices.createList(user.token, board0.id, "TestList0")
        val list1 = lServices.createList(user.token, board1.id, "TestList0")

        val card = cServices.createCard(user.token, list0.id, "TestList0", "TestList0")

        assertFailsWith<IllegalMoveCardRequestException> {
            cServices.moveCard(
                user.token,
                card.id,
                MoveCardRequest(list1.id)
            )
        }
    }
}
