package pt.isel.ls.utils

import org.junit.Test
import kotlin.random.Random
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class UtilsTest {
    @Test
    fun `validDescription returns true for descriptions within MIN_DESCRIPTION_LENGTH and MAX_DESCRIPTION_LENGTH`() {
        assertTrue { validDescription("a".repeat((MIN_DESCRIPTION_LENGTH..MAX_DESCRIPTION_LENGTH).random())) }
    }

    @Test
    fun `validName returns true with a string at MIN_DESCRIPTION_LENGTH`() {
        assertTrue { validDescription("a".repeat(MIN_DESCRIPTION_LENGTH)) }
    }

    @Test
    fun `validName returns true with a string at MAX_DESCRIPTION_LENGTH`() {
        assertTrue { validDescription("a".repeat(MAX_DESCRIPTION_LENGTH)) }
    }

    @Test
    fun `validName returns false with string longer than MAX_DESCRIPTION_LENGTH`() {
        assertFalse { validDescription("a".repeat(MAX_DESCRIPTION_LENGTH + 1)) }
    }

    // -------------
    @Test
    fun `validId returns true for any id higher or equal to 0`() {
        assertTrue { validId(Random.nextInt(-0, 999999)) }
    }

    @Test
    fun `validId returns false for any id lower than 0`() {
        assertFalse { validId(Random.nextInt(-99999, -1)) }
    }
}
