package pt.isel.ls.unit.database.memory
/*
import org.junit.Test
import pt.isel.ls.database.memory.BoardNotFoundException
import pt.isel.ls.database.memory.ListNotFoundException
import pt.isel.ls.database.memory.TasksDataMem
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ListTests {

    @Test
    fun `test create taskList successfully`() {
        val mem = TasksDataMem()
        val name = "Backend Work"
        val donkeyUser = mem.createUser(UUID.randomUUID().toString(), "test", "test@gmail.com")
        val board = mem.createBoard(donkeyUser.id, "To Do".repeat(4), "ISEL project")

        val sut = mem.createList(board.id, name)

        assertEquals(mem.taskLists[0]?.id, sut.id)
    }

    @Test
    fun `test create taskList throws BoardNotFoundException`() {
        val mem = TasksDataMem()
        val name = "Backend Work"

        val msg = assertFailsWith<BoardNotFoundException> {
            mem.createList(0, name)
        }
        assertEquals("The board with the id provided doesn't exist", BoardNotFoundException.description)
    }

    @Test
    fun `test get tasklist details`() {
        val mem = TasksDataMem()
        val donkeyUser = mem.createUser(UUID.randomUUID().toString(), "test", "test@gmail.com")
        val board = mem.createBoard(donkeyUser.id, "To Do".repeat(4), "ISEL project")
        val taskList = mem.createList(board.id, "some work..")

        val sut = mem.getListDetails(taskList.id)

        assertEquals(mem.taskLists[0]?.id, sut.id)
        assertEquals(mem.taskLists[0]?.name, sut.name)
        assertEquals(mem.taskLists[0]?.bid, sut.bid)
    }

    @Test
    fun `test get taskList details throws ListNotFoundException`() {
        val mem = TasksDataMem()

        val msg = assertFailsWith<ListNotFoundException> {
            mem.getListDetails(0)
        }
        assertEquals("The list with the id provided doesn't exist", ListNotFoundException.description)
    }

    @Test
    fun `test getListsFromBoard giving the correct id`() {
        val mem = TasksDataMem()
        val donkeyUser = mem.createUser(UUID.randomUUID().toString(), "test", "test@gmail.com")
        val board = mem.createBoard(donkeyUser.id, "To Do".repeat(4), "ISEL project")

        val l1 = mem.getListDetails(mem.createList(board.id, "Some work 1").id)
        val l2 = mem.getListDetails(mem.createList(board.id, "Some work 2").id)
        val l3 = mem.getListDetails(mem.createList(board.id, "Some work 3").id)

        val sut = mem.getListsFromBoard(board.id)

        // assertEquals(3, sut.size)
        assertEquals(listOf(l1, l2, l3), sut)
    }

    @Test
    fun `test getListsFromBoard giving wrong board id throws BoardNotFoundException`() {
        val mem = TasksDataMem()

        val msg = assertFailsWith<BoardNotFoundException> {
            mem.getListsFromBoard(1)
        }
        assertEquals("The board with the id provided doesn't exist", BoardNotFoundException.description)
    }
}
*/
