package pt.isel.ls.unit.database.memory

import pt.isel.ls.database.memory.BoardNotFoundException
import pt.isel.ls.database.memory.TasksDataMem
import pt.isel.ls.database.memory.UserNotFoundException
import pt.isel.ls.domain.UserBoard
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class BoardTests {
    private val mem = TasksDataMem()
    private val tokenA = "7d444840-9dc0-11d1-b245-5ffdce74fad1"
    private val passwordA = "132513E5601D28F9DBDEBD2590514E171FEFEC9A6BE60417D79B8D626077C3FB"
    private val userA: Int = mem.createUser(tokenA, "Teste RFL", "a46631@alunos.isel.pt", passwordA)

    @Test
    fun `test create board `() {
        val name = "To Do".repeat(4)
        val description = "ISEL project"
        val user = userA

        val sut = mem.createBoard(user, name, description)

        assertEquals(mem.boards[0]?.id, sut)
        assertEquals(mem.boards[0]?.name, name)
        assertEquals(mem.boards[0]?.description, description)
    }

    @Test
    fun `test add User to a board`() {
        val user = userA
        val newDonkeyUser =
            mem.createUser(UUID.randomUUID().toString(), "test user xd", "test2xdd@gmail.com", passwordA)

        val board = mem.createBoard(user, "To Do".repeat(4), "ISEL project")
        mem.addUserToBoard(newDonkeyUser, board)

        val sut = mem.userBoard

        assertEquals(2, sut.size)
        assertEquals(board, sut[0]?.bId)
        assertEquals(user, sut[0]?.uId)
        assertEquals(board, sut[1]?.bId)
        assertEquals(newDonkeyUser, sut[1]?.uId)
    }

    @Test
    fun `test add User to a board throws UserNotFoundException`() {
        val user = userA
        val board = mem.createBoard(user, "To Do".repeat(4), "ISEL project")

        val msg = assertFailsWith<UserNotFoundException> {
            mem.addUserToBoard(100, board)
        }
        assertEquals("A user with that id does not exist", msg.description)
    }


    @Test
    fun `test get board details`() {
        val donkeyUser = userA
        val board = mem.createBoard(donkeyUser, "To Do".repeat(4), "ISEL project")

        val sut = mem.getBoardDetails(board)

        assertEquals(mem.boards[0]?.id, sut.id)
        assertEquals(mem.boards[0]?.name, sut.name)
        assertEquals(mem.boards[0]?.description, sut.description)

        // Test UserBoard Ids
        assertEquals(mem.userBoard[0]?.uId, donkeyUser)
        assertEquals(mem.userBoard[0]?.bId, board)
    }


    @Test
    fun `test get board details throws BoardNotFoundException`() {
        val msg = assertFailsWith<BoardNotFoundException> {
            mem.getBoardDetails(100)
        }
        assertEquals("The board with the id provided doesn't exist", msg.description)
    }


    @Test
    fun `test get user available boards`() {
        val donkeyUser = userA
        val b = mem.getBoardDetails(
            mem.createBoard(donkeyUser, "To Do".repeat(4), "ISEL project")
        )

        val b2 = mem.getBoardDetails(
            mem.createBoard(donkeyUser, "To Do 2".repeat(4), "ISEL project 2")
        )

        val b3 = mem.getBoardDetails(
            mem.createBoard(donkeyUser, "To Do 3".repeat(4), "ISEL project 3")
        )

        val sut = mem.getBoardsFromUser(donkeyUser)
        assertEquals(listOf(b, b2, b3), sut)
    }

    @Test
    fun `test get user available boards throws BoardNotFoundException`() {
        val donkeyUser = userA

        mem.createBoard(donkeyUser, "To Do".repeat(4), "ISEL project")
        mem.userBoard[1] = UserBoard(donkeyUser, 10)

        val msg = assertFailsWith<BoardNotFoundException> {
            mem.getBoardsFromUser(donkeyUser)
        }
        assertEquals("The board with the id provided doesn't exist", msg.description)
    }

}

