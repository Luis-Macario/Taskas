package pt.isel.ls.unit.services

import pt.isel.ls.database.memory.TasksDataMem
import pt.isel.ls.domain.SimpleList
import pt.isel.ls.services.boards.BoardServices
import pt.isel.ls.services.lists.ListServices
import pt.isel.ls.services.users.UserServices
import pt.isel.ls.services.utils.exceptions.IllegalBoardAccessException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class BoardServicesTests {
    private val database = TasksDataMem()
    private val uServices = UserServices(database)
    private val bServices = BoardServices(database)
    private val lServices = ListServices(database)

    private val userA = uServices.createUser("Test User", "test_user@isel.pt", "teste12345")
    private val userB = uServices.createUser("New Test User", "new_test_user@isel.pt", "teste12345")

    // createBoard
    @Test
    fun `Creating board with valid token, name and description should return the correct board`() {
        val user = userA
        val board = bServices.createBoard(user.token, "TestProject", "TestDescription")

        assertEquals(
            database.boards[0],
            board
        )
    }

    // addUserToBoard
    @Test
    fun `Adding user to a board with valid id and token should be successful`() {
        val user = userA
        val newUser = userB
        val board = bServices.createBoard(user.token, "TestProject", "TestDescription")

        bServices.addUserToBoard(user.token, newUser.id, board.id)

        assertTrue(database.getUsersFromBoard(board.id).contains(newUser))
    }

    @Test
    fun `Adding user when user token doesn't belong to board should throw IllegalBoardAccessException`() {
        val user = userA
        val newUser = userB
        val board = bServices.createBoard(user.token, "TestProject", "TestDescription")

        assertFailsWith<IllegalBoardAccessException> {
            bServices.addUserToBoard(
                newUser.token,
                newUser.id,
                board.id
            )
        }
    }

    // getBoardDetails
    @Test
    fun `Calling getBoardDetails with valid token and board id should return a board object`() {
        val user = userA
        val createdBoard = bServices.createBoard(user.token, "TestProject", "TestDescription")
        val getBoard = bServices.getBoardDetails(user.token, createdBoard.id)

        assertEquals(createdBoard, getBoard)
    }

    @Test
    fun `Calling getBoardDetails with user token that doesn't belong to board should throw IllegalBoardAccessException`() {
        val user = userA
        val userWithNoAccess = userB
        val board = bServices.createBoard(user.token, "TestProject", "TestDescription")

        assertFailsWith<IllegalBoardAccessException> {
            bServices.getBoardDetails(
                userWithNoAccess.token,
                board.id
            )
        }
    }

    // getUsersFromBoard
    @Test
    fun `Calling getUsersFromBoard with token from user that belongs to the board should return list of users in the board`() {
        val user0 = userA
        val user1 = userB
        val user2 = uServices.createUser("TestUser2", "test_user2@isel.pt", "teste12345")
        val user3 = uServices.createUser("TestUser3", "test_user3@isel.pt", "teste12345")
        val board = bServices.createBoard(user0.token, "TestProject", "TestDescription")

        bServices.addUserToBoard(user0.token, user1.id, board.id)
        bServices.addUserToBoard(user0.token, user2.id, board.id)
        bServices.addUserToBoard(user0.token, user3.id, board.id)

        assertEquals(bServices.getUsersFromBoard(user0.token, board.id), listOf(user0, user1, user2, user3))
        assertEquals(bServices.getUsersFromBoard(user1.token, board.id), listOf(user0, user1, user2, user3))
        assertEquals(bServices.getUsersFromBoard(user2.token, board.id), listOf(user0, user1, user2, user3))
        assertEquals(bServices.getUsersFromBoard(user3.token, board.id), listOf(user0, user1, user2, user3))
    }

    @Test
    fun `Calling getUsersFromBoard with invalid token should throw IllegalBoardAccessException`() {
        val user0 = userA
        val userWithNoAccess = userB
        val board = bServices.createBoard(user0.token, "TestProject", "TestDescription")

        assertFailsWith<IllegalBoardAccessException> {
            bServices.getUsersFromBoard(
                userWithNoAccess.token,
                board.id
            )
        }
    }

    @Test
    fun `Calling getUsersFromBoard with invalid board id should throw NoSuchBoardException`() {
        val user0 = userA
        val user1 = userB
        bServices.createBoard(user0.token, "TestBoard0", "TestDescription0")
        bServices.createBoard(user1.token, "TestBoard1", "TestDescription1")

        assertFailsWith<IllegalBoardAccessException> {
            bServices.getUsersFromBoard(
                user0.token,
                1
            )
        }
    }

    // getListsFromBoard
    @Test
    fun `Calling getListsFromBoard with valid token and board id should return list with all tasklists in the board`() {
        val user = userA
        val board = bServices.createBoard(user.token, "TestProject", "TestDescription")

        val list0 = lServices.createList(user.token, board.id, "TestList0")
        val list1 = lServices.createList(user.token, board.id, "TestList1")
        val list2 = lServices.createList(user.token, board.id, "TestList2")

        val allLists = bServices.getListsFromBoard(user.token, board.id)

        assertEquals(
            allLists,
            listOf(
                SimpleList(list0.id, list0.bid, list0.name),
                SimpleList(list1.id, list1.bid, list1.name),
                SimpleList(list2.id, list2.bid, list2.name)
            )
        )
    }

    @Test
    fun `Calling getListsFromBoard with invalid token should throw IllegalListAccessException`() {
        val user = userA
        val userWithNoAccess = userB
        val board = bServices.createBoard(user.token, "TestProject", "TestDescription")

        assertFailsWith<IllegalBoardAccessException> {
            bServices.getListsFromBoard(
                userWithNoAccess.token,
                board.id
            )
        }
    }
}
