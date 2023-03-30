package pt.isel.ls.services

import org.junit.Test
import pt.isel.ls.api.dto.card.MoveCardRequest
import pt.isel.ls.database.memory.TasksDataMem
import pt.isel.ls.domain.Card
import pt.isel.ls.services.boards.BoardServices
import pt.isel.ls.services.cards.CardServices
import pt.isel.ls.services.lists.ListServices
import pt.isel.ls.services.users.UserServices
import kotlin.test.assertEquals

class CardServicesTests {
    private val database = TasksDataMem()
    private val uServices = UserServices(database)
    private val bServices = BoardServices(database)
    private val lServices = ListServices(database)
    private val cServices = CardServices(database)

    // createCard
    @Test
    fun `Creating card with valid token should return the board`() {
        val user = uServices.createUser("Test User", "test_user@isel.pt")
        val board = bServices.createBoard(user.token, "TestProject", "TestDescription")
        val list = lServices.createList(user.token, board.id, "TestList")
        val card = cServices.createCard(user.token, list.id, "TestCard", "TestDescription")

        assertEquals(
            lServices.getCardsFromList(user.token, list.id)[0],
            card
        )
    }

    // getCardDetails
    @Test
    fun `Getting a card with a valid token, that belongs to the board,and a valid list id that exists in the board should return the correct list`() {
        val user = uServices.createUser("Test User", "test_user@isel.pt")
        val board = bServices.createBoard(user.token, "TestProject", "TestDescription")
        val list = lServices.createList(user.token, board.id, "TestList")
        val card = cServices.createCard(user.token, list.id, "TestCard", "TestDescription")

        assertEquals(cServices.getCardDetails(user.token, card.id), card)
    }

    // moveCard
    @Test
    fun `Getting cards from a list `() {
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
}
