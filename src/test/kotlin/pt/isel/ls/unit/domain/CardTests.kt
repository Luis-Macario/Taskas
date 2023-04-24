package pt.isel.ls.unit.domain

import org.junit.Test
import pt.isel.ls.domain.Card
import pt.isel.ls.services.utils.MAX_DATE
import java.sql.Date
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CardTests {

    @Test
    fun `Creates a valid card`() {
        val card =
            Card(1, 2, 3, name = "Finish Domain Tests", description = "CardTest", initDate = Date.valueOf("1111-1-1"))

        assertEquals(1, card.id)
        assertEquals(2, card.bid)
        assertEquals("Finish Domain Tests", card.name)
        assertEquals(Date.valueOf("1111-1-1"), card.initDate)
        assertEquals(Date.valueOf(MAX_DATE), card.finishDate)
    }

    @Test
    fun `Creating a card with an invalid id throws IllegalArgumentException`() {
        val ex = assertFailsWith<IllegalArgumentException> {
            Card(-1, 2, 3, "Finish Domain Tests", initDate = Date.valueOf("1111-1-1"))
        }
        assertEquals("Invalid card id: -1", ex.message)
    }

    @Test
    fun `Creating a card with an invalid bid throws IllegalArgumentException`() {
        val ex = assertFailsWith<IllegalArgumentException> {
            Card(1, -2, 3, "Finish Domain Tests", initDate = Date.valueOf("1111-1-1"))
        }
        assertEquals("Invalid board id: -2", ex.message)
    }

    /*@Test
    fun `Creating a card with an invalid lid throws IllegalArgumentException`() {
        val ex = assertFailsWith<IllegalArgumentException> {
            Card(1, 2, -3, "Finish Domain Tests", initDate = Date.valueOf("1111-1-1"))
        }
        assertEquals("Invalid list id: -3", ex.message)
    }*/

    @Test
    fun `Creating a card with an invalid name throws IllegalArgumentException`() {
        val ex = assertFailsWith<IllegalArgumentException> {
            Card(1, 2, 3, "", initDate = Date.valueOf("1111-1-1"))
        }
        assertEquals("Invalid card name: ", ex.message)
    }

    @Test
    fun `Creating a card with invalid dates throws IllegalArgumentException`() {
        val ex = assertFailsWith<IllegalArgumentException>("Invalid email: joe") {
            Card(1, 2, 3, "", initDate = Date.valueOf("2222-2-2"), finishDate = Date.valueOf("1111-1-1"))
        }
        assertEquals("Invalid card name: ", ex.message)
    }

    // --------------------------------
    @Test
    fun `validName returns true for names within MIN_NAME_LENGTH and MAX_NAME_LENGTH`() {
        assertTrue { Card.validName("a".repeat((Card.MIN_NAME_LENGTH..Card.MAX_NAME_LENGTH).random())) }
    }

    @Test
    fun `validName returns true with a string at MIN_NAME_LENGTH`() {
        assertTrue { Card.validName("a".repeat(Card.MIN_NAME_LENGTH)) }
    }

    @Test
    fun `validName returns true with a string at MAX_NAME_LENGTH`() {
        assertTrue { Card.validName("a".repeat(Card.MAX_NAME_LENGTH)) }
    }

    @Test
    fun `validName returns false with string shorter than MIN_NAME_LENGTH`() {
        assertFalse { Card.validName("a".repeat(Card.MIN_NAME_LENGTH - 1)) }
    }

    @Test
    fun `validName returns false with string longer than MAX_NAME_LENGTH`() {
        assertFalse { Card.validName("a".repeat(Card.MAX_NAME_LENGTH + 1)) }
    }
}
