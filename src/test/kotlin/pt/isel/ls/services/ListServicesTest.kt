package pt.isel.ls.services

import org.junit.Test
import pt.isel.ls.database.memory.TasksDataMem
import pt.isel.ls.services.boards.BoardServices
import pt.isel.ls.services.cards.CardServices
import pt.isel.ls.services.lists.ListServices
import pt.isel.ls.services.users.UserServices
import pt.isel.ls.services.utils.exceptions.IllegalBoardAccessException
import java.util.UUID
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

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

    @Test
    fun `Creating list with invalid token should throw IllegalBoardAccessException`() {
        val user = uServices.createUser("Test User", "test_user@isel.pt")
        val board = bServices.createBoard(user.token, "TestProject", "TestDescription")

        assertFailsWith<IllegalBoardAccessException> {
            lServices.createList(
                UUID.randomUUID().toString(),
                board.id,
                "TestList"
            )
        }
    }

    // getList
    @Test
    fun `Getting a list with a valid token, that belongs to the board,and a valid list id that exists in the board should return the correct list`() {
        val user = uServices.createUser("Test User", "test_user@isel.pt")
        val board = bServices.createBoard(user.token, "TestProject", "TestDescription")
        val list = lServices.createList(user.token, board.id, "TestList")

        assertEquals(lServices.getList(user.token, list.id), list)
    }

    @Test
    fun `Getting a list with an invalid token should throw IllegalListAccessException`() {
        val user = uServices.createUser("Test User", "test_user@isel.pt")
        val board = bServices.createBoard(user.token, "TestProject", "TestDescription")
        val list = lServices.createList(user.token, board.id, "TestList")

        assertFailsWith<IllegalBoardAccessException> {
            lServices.getList(
                UUID.randomUUID().toString(),
                list.id
            )
        }
    }

    // getCardsFromList
    @Test
    fun `Calling  getCardsFromList with valid user token and list id should return cards in that list`() {
        val user = uServices.createUser("Test User", "test_user@isel.pt")
        val board = bServices.createBoard(user.token, "TestProject", "TestDescription")
        val list = lServices.createList(user.token, board.id, "TestList")

        val card0 = cServices.createCard(user.token, list.id, "TestLis0", "TestList0")
        val card1 = cServices.createCard(user.token, list.id, "TestList1", "TestList1")
        val card2 = cServices.createCard(user.token, list.id, "TestList2", "TestList2")

        val cards = lServices.getCardsFromList(user.token, list.id)

        assertEquals(cards, listOf(card0, card1, card2))
        assertEquals(cards, database.cards.values.toList())
    }

    @Test
    fun `Calling  getCardsFromList with invalid user token should throw IllegalBoardAccessException`() {
        val user = uServices.createUser("Test User", "test_user@isel.pt")
        val board = bServices.createBoard(user.token, "TestProject", "TestDescription")
        val list = lServices.createList(user.token, board.id, "TestList")

        assertFailsWith<IllegalBoardAccessException> {
            lServices.getCardsFromList(
                UUID.randomUUID().toString(),
                list.id
            )
        }
    }
}
