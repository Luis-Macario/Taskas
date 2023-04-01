package pt.isel.ls.unit.database.memory

import org.junit.Test
import pt.isel.ls.database.memory.BoardNameAlreadyExistsException
import pt.isel.ls.database.memory.BoardNotFoundException
import pt.isel.ls.database.memory.TasksDataMem
import pt.isel.ls.database.memory.UserAlreadyExistsInBoardException
import pt.isel.ls.database.memory.UserNotFoundException
import pt.isel.ls.domain.UserBoard
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class BoardTests {

    @Test
    fun `test create board `() {
        val mem = TasksDataMem()
        val name = "To Do".repeat(4)
        val description = "ISEL project"
        val token = UUID.randomUUID().toString()
        val user = mem.createUser(token, "Francisco M", "francisco@isel.pt")

        val sut = mem.createBoard(user.id, name, description)

        assertEquals(mem.boards[0]?.id, sut.id)
        assertEquals(mem.boards[0]?.name, name)
        assertEquals(mem.boards[0]?.description, description)
    }

    @Test
    fun `test create board throws BoardNameAlreadyExistsException when creating a board with a repeated name`() {
        val mem = TasksDataMem()

        val user = mem.createUser(UUID.randomUUID().toString(), "Francisco M", "francisco@isel.pt")
        val boardName = "To Do".repeat(4)

        mem.createBoard(user.id, boardName, "ISEL project")

        val msg = assertFailsWith<BoardNameAlreadyExistsException> {
            mem.createBoard(user.id, boardName, "Outra descrição")
        }

        assertEquals("A board with that name already exists", BoardNameAlreadyExistsException.description)
    }

    @Test
    fun `test create board throws UserNotFoundException when creating a board with a invalid user`() {
        val mem = TasksDataMem()
        val user = mem.createUser(UUID.randomUUID().toString(), "Francisco M", "francisco@isel.pt")
        val boardName = "To Do".repeat(4)

        val msg = assertFailsWith<UserNotFoundException> {
            mem.createBoard(100, boardName, "Outra descrição")
        }

        assertEquals("A user with that id does not exist", UserNotFoundException.description)
    }

    @Test
    fun `test add User to a board`() {
        val mem = TasksDataMem()
        val donkeyUser = mem.createUser(UUID.randomUUID().toString(), "test", "test@gmail.com")
        val newDonkeyUser = mem.createUser(UUID.randomUUID().toString(), "test2", "test2@gmail.com")

        val board = mem.createBoard(donkeyUser.id, "To Do".repeat(4), "ISEL project")
        mem.addUserToBoard(newDonkeyUser.id, board.id)

        val sut = mem.userBoard

        assertEquals(2, sut.size)
        assertEquals(board.id, sut[0]?.bId)
        assertEquals(donkeyUser.id, sut[0]?.uId)
        assertEquals(board.id, sut[1]?.bId)
        assertEquals(newDonkeyUser.id, sut[1]?.uId)
    }

    @Test
    fun `test add User to a board throws UserNotFoundException`() {
        val mem = TasksDataMem()
        val donkeyUser = mem.createUser(UUID.randomUUID().toString(), "test", "test@gmail.com")
        val board = mem.createBoard(donkeyUser.id, "To Do".repeat(4), "ISEL project")

        val msg = assertFailsWith<UserNotFoundException> {
            mem.addUserToBoard(100, board.id)
        }
        assertEquals("A user with that id does not exist", UserNotFoundException.description)
    }

    @Test
    fun `test add User to a board throws BoardNotFoundException`() {
        val mem = TasksDataMem()
        val donkeyUser = mem.createUser(UUID.randomUUID().toString(), "test", "test@gmail.com")

        val msg = assertFailsWith<BoardNotFoundException> {
            mem.addUserToBoard(donkeyUser.id, 100)
        }
        assertEquals("The board with the id provided doesn't exist", BoardNotFoundException.description)
    }

    @Test
    fun `test add User to a board throws UserAlreadyExistsInBoardException`() {
        val mem = TasksDataMem()
        val donkeyUser = mem.createUser(UUID.randomUUID().toString(), "test", "test@gmail.com")
        val board = mem.createBoard(donkeyUser.id, "To Do".repeat(4), "ISEL project")

        val msg = assertFailsWith<UserAlreadyExistsInBoardException> {
            mem.addUserToBoard(donkeyUser.id, board.id)
        }
        assertEquals("A user already exists in that board", UserAlreadyExistsInBoardException.description)
    }

    @Test
    fun `test get board details`() {
        val mem = TasksDataMem()
        val donkeyUser = mem.createUser(UUID.randomUUID().toString(), "test", "test@gmail.com")
        val board = mem.createBoard(donkeyUser.id, "To Do".repeat(4), "ISEL project")

        val sut = mem.getBoardDetails(board.id)

        assertEquals(mem.boards[0]?.id, sut.id)
        assertEquals(mem.boards[0]?.name, sut.name)
        assertEquals(mem.boards[0]?.description, sut.description)

        // Test UserBoard Ids
        assertEquals(mem.userBoard[0]?.uId, donkeyUser.id)
        assertEquals(mem.userBoard[0]?.bId, board.id)
    }

    @Test
    fun `test get board details throws BoardNotFoundException`() {
        val mem = TasksDataMem()

        val msg = assertFailsWith<BoardNotFoundException> {
            mem.getBoardDetails(100)
        }
        assertEquals("The board with the id provided doesn't exist", BoardNotFoundException.description)
    }

    @Test
    fun `test get user available boards`() {
        val mem = TasksDataMem()
        val donkeyUser = mem.createUser(UUID.randomUUID().toString(), "test", "test@gmail.com")

        val b = mem.getBoardDetails(
            mem.createBoard(donkeyUser.id, "To Do".repeat(4), "ISEL project").id
        )

        val b2 = mem.getBoardDetails(
            mem.createBoard(donkeyUser.id, "To Do 2".repeat(4), "ISEL project 2").id
        )

        val b3 = mem.getBoardDetails(
            mem.createBoard(donkeyUser.id, "To Do 3".repeat(4), "ISEL project 3").id
        )

        val sut = mem.getBoardsFromUser(donkeyUser.id)
        assertEquals(listOf(b, b2, b3), sut)
    }

    @Test
    fun `test get user available boards throws BoardNotFoundException`() {
        val mem = TasksDataMem()
        val donkeyUser = mem.createUser(UUID.randomUUID().toString(), "test", "test@gmail.com")

        mem.createBoard(donkeyUser.id, "To Do".repeat(4), "ISEL project").id
        // Hammer boards
        mem.userBoard[1] = UserBoard(donkeyUser.id, 10)

        val msg = assertFailsWith<BoardNotFoundException> {
            mem.getBoardsFromUser(donkeyUser.id)
        }
        assertEquals("The board with the id provided doesn't exist", BoardNotFoundException.description)
    }
}
