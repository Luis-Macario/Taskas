package pt.isel.ls.services

import org.junit.Test
import pt.isel.ls.database.memory.TasksDataMem
import pt.isel.ls.services.boards.BoardServices
import pt.isel.ls.services.lists.ListServices
import pt.isel.ls.services.users.UserServices
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class BoardServicesTests {
    private val database = TasksDataMem()
    private val uServices = UserServices(database)
    private val bServices = BoardServices(database)
    private val lServices = ListServices(database)

    // createBoard
    @Test
    fun `Creating board with valid token, name and description should return the board`() {
        val user = uServices.createUser("Test User", "test_user@isel.pt")
        val board = bServices.createBoard(user.token, "TestProject", "TestDescription")

        assertEquals(
            uServices.getBoardsFromUser(user.token, user.id)[0],
            board
        )
    }

    // addUserToBoard
    @Test
    fun `Adding user to board with valid id and token should be successful`() {
        val user = uServices.createUser("Test User", "test_user@isel.pt")
        val newUser = uServices.createUser("New Test User", "new_test_user@isel.pt")
        val board = bServices.createBoard(user.token, "TestProject", "TestDescription")

        bServices.addUserToBoard(user.token, newUser.id, board.id)

        assertTrue(bServices.getUsersFromBoard(user.token, board.id).contains(newUser))
    }

    // getBoardDetails
    @Test
    fun `Getting board object with a valid token, that belongs to the board, and a valid board id should return a board object`() {
        val user = uServices.createUser("Test User", "test_user@isel.pt")
        val createdBoard = bServices.createBoard(user.token, "TestProject", "TestDescription")
        val getBoard = bServices.getBoardDetails(user.token, createdBoard.id)

        assertEquals(createdBoard, getBoard)
    }

    // getListsFromBoard
    @Test
    fun `Getting all the list in a board a valid token, that belongs to the board, and a valid board id should return a board object`() {
        val user = uServices.createUser("Test User", "test_user@isel.pt")
        val board = bServices.createBoard(user.token, "TestProject", "TestDescription")

        val list0 = lServices.createList(user.token, board.id, "TestList")
        val list1 = lServices.createList(user.token, board.id, "TestList")
        val list2 = lServices.createList(user.token, board.id, "TestList")

        val allLists = bServices.getListsFromBoard(user.token, board.id)

        assertEquals(allLists[0], list0)
        assertEquals(allLists[1], list1)
        assertEquals(allLists[2], list2)
    }
}
