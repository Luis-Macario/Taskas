package pt.isel.ls.unit.database.memory

import pt.isel.ls.database.memory.BoardNotFoundException
import pt.isel.ls.database.memory.ListNotFoundException
import pt.isel.ls.database.memory.TasksDataMem
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ListTests {
    private val mem = TasksDataMem()
    private val tokenA = "7d444840-9dc0-11d1-b245-5ffdce74fad1"
    private val passwordA = "132513E5601D28F9DBDEBD2590514E171FEFEC9A6BE60417D79B8D626077C3FB"
    private val userA: Int = mem.createUser(tokenA, "Teste RFL", "a46631@alunos.isel.pt", passwordA)
    private val boardA: Int = mem.createBoard(userA, "To Do".repeat(4), "ISEL project")

    @Test
    fun `test create taskList successfully`() {
        val name = "Backend Work"
        val board = boardA

        val sut = mem.createList(board, name)

        assertEquals(mem.taskLists[0]?.id, sut)
    }

    @Test
    fun `test get tasklist details`() {
        val board = boardA
        val taskList = mem.createList(board, "some work..")

        val sut = mem.getListDetails(taskList)

        assertEquals(mem.taskLists[0]?.id, sut.id)
        assertEquals(mem.taskLists[0]?.name, sut.name)
        assertEquals(mem.taskLists[0]?.bid, sut.bid)
    }

    @Test
    fun `test get taskList details throws ListNotFoundException`() {
        val msg = assertFailsWith<ListNotFoundException> {
            mem.getListDetails(0)
        }
        assertEquals("The list with the id provided doesn't exist", msg.description)
    }

    @Test
    fun `test getListsFromBoard giving the correct id`() {
        val board = boardA

        val l1 = mem.getListDetails(mem.createList(board, "Some work 1"))
        val l2 = mem.getListDetails(mem.createList(board, "Some work 2"))
        val l3 = mem.getListDetails(mem.createList(board, "Some work 3"))

        val sut = mem.getListsFromBoard(board)

        // assertEquals(3, sut.size)
        assertEquals(listOf(l1, l2, l3), sut)
    }

    @Test
    fun `test getListsFromBoard giving wrong board id throws BoardNotFoundException`() {
        val mem = TasksDataMem()

        val msg = assertFailsWith<BoardNotFoundException> {
            mem.getListsFromBoard(1)
        }
        assertEquals("The board with the id provided doesn't exist", msg.description)
    }
}
