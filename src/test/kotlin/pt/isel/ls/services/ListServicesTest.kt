package pt.isel.ls.services

import org.junit.Test
import pt.isel.ls.database.memory.TasksDataMem
import pt.isel.ls.services.boards.BoardServices
import pt.isel.ls.services.cards.CardServices
import pt.isel.ls.services.lists.ListServices
import pt.isel.ls.services.users.UserServices
import kotlin.test.assertEquals

class ListServicesTest {
    private val database = TasksDataMem()
    private val uServices = UserServices(database)
    private val bServices = BoardServices(database)
    private val lServices = ListServices(database)
    private val cServices = CardServices(database)

    // createList
    @Test
    fun `Creating list with valid token should return the board`() {
        val user = uServices.createUser("Test User", "test_user@isel.pt")
        val board = bServices.createBoard(user.token, "TestProject", "TestDescription")

        val list = lServices.createList(user.token, board.id, "TestList")

        assertEquals(
            bServices.getListsFromBoard(user.token, board.id)[0],
            list
        )
    }

    // getList
    @Test
    fun `Getting a list with a valid token, that belongs to the board,and a valid list id that exists in the board should return the correct list`() {
        val user = uServices.createUser("Test User", "test_user@isel.pt")
        val board = bServices.createBoard(user.token, "TestProject", "TestDescription")
        val list = lServices.createList(user.token, board.id, "TestList")

        assertEquals(lServices.getList(user.token, board.id), list)
    }

    // getCardsFromList
    @Test
    fun `Getting cards from a list `() {
        val user = uServices.createUser("Test User", "test_user@isel.pt")
        val board = bServices.createBoard(user.token, "TestProject", "TestDescription")
        val list = lServices.createList(user.token, board.id, "TestList")

        val card0 = cServices.createCard(user.token, list.id, "TestLis0", "TestList0")
        val card1 = cServices.createCard(user.token, list.id, "TestList1", "TestList1")
        val card2 = cServices.createCard(user.token, list.id, "TestList2", "TestList2")

        val cards = lServices.getCardsFromList(user.token, list.id)

        assertEquals(cards[0], card0)
        assertEquals(cards[1], card1)
        assertEquals(cards[2], card2)
    }
}
