package pt.isel.ls.unit.database.memory

import pt.isel.ls.database.memory.TasksDataMem
import pt.isel.ls.database.memory.UserNotFoundException
import pt.isel.ls.domain.User
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class UserTests {
    private val mem = TasksDataMem()
    private val token = UUID.randomUUID().toString()
    private val name = "Francisco"
    private val email = "honorStudent@gmail.com"
    private val password = "C04825961B2415A75D4DE08598E8BBF4D1ECDCAE1A44D58E7CE03111BDA25A3A"

    @Test
    fun `test create user manually`() {
        val user = User(0, name, email, token)
        mem.users[0] = user

        assertEquals(mem.users[0]?.name, name)
        assertEquals(mem.users[0]?.email, email)
    }


    @Test
    fun `test create user`() {
        val sut = mem.createUser(token, name, email, password)

        assertEquals(mem.users[0]?.name, name)
        assertEquals(mem.users[0]?.email, email)
        assertEquals(mem.users[0]?.id, sut)
        assertEquals(mem.users[0]?.token, token)
    }


    @Test
    fun `test get user details`() {
        val mem = TasksDataMem()

        val name = "Tweeny"
        val email = "honorStudent@gmail.com"
        val token = UUID.randomUUID().toString()

        val user = mem.createUser(token, name, email, password)
        val sut = mem.getUserDetails(user)

        assertEquals(name, sut.name)
        assertEquals(email, sut.email)

        assertFailsWith<UserNotFoundException> { mem.getUserDetails(10) }
    }

}
