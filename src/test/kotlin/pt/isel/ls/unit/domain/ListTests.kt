package pt.isel.ls.unit.domain

import org.junit.jupiter.api.Test
import pt.isel.ls.domain.TaskList
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ListTests {

    @Test
    fun `Creates a valid tasklist`() {
        val list = TaskList(1, 2, "TO-DO", true, listOf())

        assertEquals(1, list.id)
        assertEquals(2, list.bid)
        assertEquals("TO-DO", list.name)
    }

    @Test
    fun `Creating a list with an invalid id throws IllegalArgumentException`() {
        val msg = assertFailsWith<IllegalArgumentException> {
            TaskList(-1, 2, "TO-DO", true, listOf())
        }

        assertEquals("Invalid task id: -1", msg.message)
    }

    @Test
    fun `Creating a list with an invalid board id throws IllegalArgumentException`() {
        val msg = assertFailsWith<IllegalArgumentException>("Invalid email: joe") {
            TaskList(1, -2, "TO-DO", true, listOf())
        }
        assertEquals("Invalid board id: -2", msg.message)
    }

    @Test
    fun `Creating a list with an invalid name throws IllegalArgumentException`() {
        val msg = assertFailsWith<IllegalArgumentException>("Invalid name: ") {
            TaskList(1, 2, "", true, listOf())
        }

        assertEquals("Invalid task name: ", msg.message)
    }

    // --------------------------------
    @Test
    fun `validName returns true for names within MIN_NAME_LENGTH and MAX_NAME_LENGTH`() {
        assertTrue { TaskList.validName("a".repeat((TaskList.MIN_NAME_LENGTH..TaskList.MAX_NAME_LENGTH).random())) }
    }

    @Test
    fun `validName returns true with a string at MIN_NAME_LENGTH`() {
        assertTrue { TaskList.validName("a".repeat(TaskList.MIN_NAME_LENGTH)) }
    }

    @Test
    fun `validName returns true with a string at MAX_NAME_LENGTH`() {
        assertTrue { TaskList.validName("a".repeat(TaskList.MAX_NAME_LENGTH)) }
    }

    @Test
    fun `validName returns false with string shorter than MIN_NAME_LENGTH`() {
        assertFalse { TaskList.validName("a".repeat(TaskList.MIN_NAME_LENGTH - 1)) }
    }

    @Test
    fun `validName returns false with string longer than MAX_NAME_LENGTH`() {
        assertFalse { TaskList.validName("a".repeat(TaskList.MAX_NAME_LENGTH + 1)) }
    }
}
