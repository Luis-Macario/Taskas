package pt.isel.ls.unit.services

import org.junit.jupiter.api.Test
import pt.isel.ls.database.memory.TasksDataMem
import pt.isel.ls.database.memory.UserNotFoundException
import pt.isel.ls.domain.SimpleList
import pt.isel.ls.services.boards.BoardServices
import pt.isel.ls.services.cards.CardServices
import pt.isel.ls.services.lists.ListServices
import pt.isel.ls.services.users.UserServices
import pt.isel.ls.services.utils.exceptions.IllegalListAccessException
import java.util.UUID
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ListServicesTest {
    private val database = TasksDataMem()
    private val uServices = UserServices(database)
    private val bServices = BoardServices(database)
    private val lServices = ListServices(database)
    private val cServices = CardServices(database)

    private val userA = uServices.createUser("Test User", "test_user@isel.pt", "teste12345")
    private val userB = uServices.createUser("New Test User", "new_test_user@isel.pt", "teste12345")


    // createList
    @Test
    fun `Creating list with valid token should return the board`() {
        val user = userA
        val board = bServices.createBoard(user.token, "TestProject", "TestDescription")
        val list = lServices.createList(user.token, board.id, "TestList")

        assertEquals(
            bServices.getListsFromBoard(user.token, board.id)[0], SimpleList(list.id, board.id, "TestList")
        )
    }

    @Test
    fun `Creating list with invalid token should throw UserNotFoundException`() {
        val user = userA
        val board = bServices.createBoard(user.token, "TestProject", "TestDescription")

        assertFailsWith<UserNotFoundException> {
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
        val user = userA
        val board = bServices.createBoard(user.token, "TestProject", "TestDescription")
        val list = lServices.createList(user.token, board.id, "TestList")

        assertEquals(lServices.getList(user.token, list.id), list)
    }

    @Test
    fun `Getting a list with an invalid token should throw IllegalListAccessException`() {
        val user = userA
        val userWithNoAccess = userB
        val board = bServices.createBoard(user.token, "TestProject", "TestDescription")
        val list = lServices.createList(user.token, board.id, "TestList")

        assertFailsWith<IllegalListAccessException> {
            lServices.getList(
                userWithNoAccess.token,
                list.id
            )
        }
    }

    // getCardsFromList
    @Test
    fun `Calling  getCardsFromList with valid user token and list id should return cards in that list`() {
        val user = userA
        val board = bServices.createBoard(user.token, "TestProject", "TestDescription")
        val list = lServices.createList(user.token, board.id, "TestList")

        val card0 = cServices.createCard(user.token, list.id, "TestLis0", "TestList0", "2023-04-23","2023-05-23")
        val card1 = cServices.createCard(user.token, list.id, "TestList1", "TestList1", "2023-04-23","2023-05-23")
        val card2 = cServices.createCard(user.token, list.id, "TestList2", "TestList2", "2023-04-23","2023-05-23")

        val cards = lServices.getCardsFromList(user.token, list.id)

        assertEquals(
            cards,
            listOf(
                cServices.getCardDetails(user.token,card0),
                cServices.getCardDetails(user.token,card1),
                cServices.getCardDetails(user.token,card2)
            )
        )
        assertEquals(cards, database.cards.values.toList())
    }

    @Test
    fun `Calling  getCardsFromList with invalid user token should throw IllegalListAccessException`() {
        val user = userA
        val userWithNoAccess = userB
        val board = bServices.createBoard(user.token, "TestProject", "TestDescription")
        val list = lServices.createList(user.token, board.id, "TestList")

        assertFailsWith<IllegalListAccessException> {
            lServices.getCardsFromList(
                userWithNoAccess.token,
                list.id
            )
        }
    }
}

