package pt.isel.ls.services

import org.junit.Test
import pt.isel.ls.services.utils.MAX_DESCRIPTION_LENGTH
import pt.isel.ls.services.utils.MIN_DESCRIPTION_LENGTH
import pt.isel.ls.services.utils.checkToken
import pt.isel.ls.services.utils.exceptions.InvalidTokenException
import pt.isel.ls.services.utils.validDescription
import pt.isel.ls.services.utils.validId
import kotlin.random.Random
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class UtilsServicesTests {
    @Test
    fun `validDescription returns true for descriptions within MIN_DESCRIPTION_LENGTH and MAX_DESCRIPTION_LENGTH`() {
        assertTrue { validDescription("a".repeat((MIN_DESCRIPTION_LENGTH..MAX_DESCRIPTION_LENGTH).random())) }
    }

    @Test
    fun `validDescription returns true with a string at MAX_DESCRIPTION_LENGTH`() {
        assertTrue { validDescription("a".repeat(MAX_DESCRIPTION_LENGTH)) }
    }

    @Test
    fun `validDescription returns false with string longer than MAX_DESCRIPTION_LENGTH`() {
        assertFalse { validDescription("a".repeat(MAX_DESCRIPTION_LENGTH + 1)) }
    }

    @Test
    fun `validId returns true for any id higher or equal to 0`() {
        assertTrue { validId(Random.nextInt(-0, 999999)) }
    }

    @Test
    fun `validId returns false for any id lower than 0`() {
        assertFalse { validId(Random.nextInt(-99999, -1)) }
    }

    @Test
    fun `Checking invalid token should throw InvalidTokenException`() {
        assertFailsWith<InvalidTokenException> { checkToken("aaaa") }
    }
}
