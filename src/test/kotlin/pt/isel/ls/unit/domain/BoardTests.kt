package pt.isel.ls.unit.domain

import org.junit.jupiter.api.Test
import pt.isel.ls.domain.Board
import pt.isel.ls.domain.User
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class BoardTests {

    @Test
    fun `Creates a valid board`() {
        val board = Board(1, "Projeto Kapa Mega Giga Tera Fixe", "Projeto Kapa Mega Giga Tera Fixe", listOf())

        assertEquals(1, board.id)
        assertEquals("Projeto Kapa Mega Giga Tera Fixe", board.name)
        assertEquals("Projeto Kapa Mega Giga Tera Fixe", board.description)
    }

    @Test
    fun `Creating a board with an invalid id throws IllegalArgumentException`() {
        val msg = assertFailsWith<IllegalArgumentException> {
            Board(-1, "BoardTest", "BoardTest", listOf())
        }

        assertEquals("Invalid board id: -1", msg.message)
    }

    @Test
    fun `Creating a board with an invalid name throws IllegalArgumentException`() {
        val ex = assertFailsWith<IllegalArgumentException> {
            Board(1, " ", "Projeto Kapa Mega Giga Tera Fixe", listOf())
        }

        assertEquals("Invalid board name:  ", ex.message)
    }

    // --------------------------------

    @Test
    fun `validName returns true for names within MIN_NAME_LENGTH and MAX_NAME_LENGTH`() {
        assertTrue { Board.validName("a".repeat((Board.MIN_NAME_LENGTH..Board.MAX_NAME_LENGTH).random())) }
    }

    @Test
    fun `validName returns true with a string at MIN_NAME_LENGTH`() {
        assertTrue { User.validName("a".repeat(Board.MIN_NAME_LENGTH)) }
    }

    @Test
    fun `validName returns true with a string at MAX_NAME_LENGTH`() {
        assertTrue { Board.validName("a".repeat(Board.MAX_NAME_LENGTH)) }
    }

    @Test
    fun `validName returns false with string shorter than MIN_NAME_LENGTH`() {
        assertFalse { Board.validName("a".repeat(Board.MIN_NAME_LENGTH - 1)) }
    }

    @Test
    fun `validName returns false with string longer than MAX_NAME_LENGTH`() {
        assertFalse { Board.validName("a".repeat(Board.MAX_NAME_LENGTH + 1)) }
    }
}
