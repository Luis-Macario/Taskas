package pt.isel.ls.unit.domain

import pt.isel.ls.domain.User
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class UserTests {

    private val token = UUID.randomUUID().toString()

    @Test
    fun `Creates a valid user`() {
        val user = User(1, "Joel", "joel@gmail.com", token)

        assertEquals(1, user.id)
        assertEquals("Joel", user.name)
        assertEquals("joel@gmail.com", user.email)
        assertEquals(token, user.token)
    }

    @Test
    fun `Creating a user with an invalid id throws IllegalArgumentException`() {
        val msg = assertFailsWith<IllegalArgumentException> {
            User(-1, "joel", "joel@hotmail.com", token)
        }

        assertEquals("Invalid user id: -1", msg.message)
    }

    @Test
    fun `Creating a user with an invalid name throws IllegalArgumentException`() {
        val msg = assertFailsWith<IllegalArgumentException> {
            User(1, "", "joel@hotmail.com", token)
        }

        assertEquals("Invalid username: ", msg.message)
    }

    @Test
    fun `Creating a user with an invalid email throws IllegalArgumentException`() {
        val msg = assertFailsWith<IllegalArgumentException> {
            User(1, "Joel", "joe", token)
        }

        assertEquals("Invalid email: joe", msg.message)
    }

    // --------------------------------
    @Test
    fun `validName returns true for names within MIN_NAME_LENGTH and MAX_NAME_LENGTH`() {
        assertTrue { User.validName("a".repeat((User.MIN_NAME_LENGTH..User.MAX_NAME_LENGTH).random())) }
    }

    @Test
    fun `validName returns true with a string at MIN_NAME_LENGTH`() {
        assertTrue { User.validName("a".repeat(User.MIN_NAME_LENGTH)) }
    }

    @Test
    fun `validName returns true with a string at MAX_NAME_LENGTH`() {
        assertTrue { User.validName("a".repeat(User.MAX_NAME_LENGTH)) }
    }

    @Test
    fun `validName returns false with string shorter than MIN_NAME_LENGTH`() {
        assertFalse { User.validName("a".repeat(User.MIN_NAME_LENGTH - 1)) }
    }

    @Test
    fun `validName returns false with string longer than MAX_NAME_LENGTH`() {
        assertFalse { User.validName("a".repeat(User.MAX_NAME_LENGTH + 1)) }
    }

    // --------------------------------
    @Test
    fun `validEmail returns true with a string that matches the REGEX expression`() {
        assertTrue { User.validEmail("abc@.") }
    }

    @Test
    fun `validEmail returns true with a string that does not match the REGEX expression`() {
        assertFalse { User.validEmail("123") }
    }

    @Test
    fun `validEmail returns false with a string without the char @`() {
        assertFalse { User.validEmail("abc.") }
    }

    @Test
    fun `validEmail returns false with a string without a dot`() {
        assertFalse { User.validEmail("abc@") }
    }
}
