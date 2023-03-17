package pt.isel.ls.utils

import kotlin.test.Test
import pt.isel.ls.DataMem
import pt.isel.ls.User
import kotlin.test.assertEquals

class DataMemTests {


    @Test
    fun `creates a user manually`() {
        val mem = DataMem()

        val name = "Francisco"
        val email = "honorStudent@gmail.com"

        val user = User(0, listOf(), name, email, "token")
        mem.users[0] = user

        assertEquals(mem.users[0]?.name, name)
        assertEquals(mem.users[0]?.email, email)
    }

    @Test
    fun `creates a user successfully`() {
        val mem = DataMem()

        val name = "Francisco"
        val email = "honorStudent@gmail.com"

        val sut = mem.createUser(name, email)

        assertEquals(mem.users[0]?.name, name)
        assertEquals(mem.users[0]?.email, email)
        assertEquals(mem.users[0]?.id, sut.second)
        assertEquals(mem.users[0]?.token, sut.first)
    }

    @Test
    fun `get a user details`() {
        val mem = DataMem()

        val name = "Tweeny"
        val email = "honorStudent@gmail.com"

        val user = mem.createUser(name, email)
        val sut = mem.getUser(user.second)      //user.second -> id

        assertEquals(name, sut.name)
        assertEquals(email, sut.email)
    }
    @Test
    fun `creates a board successfully`() {
        val mem = DataMem()
        val donkeyUser =  mem.createUser("test", "test@")

        val name = "To Do"
        val descritpion = "ISEL project"

        val sut = mem.createBoard(name, descritpion, donkeyUser.second)

        assertEquals(mem.boards[0]?.id, sut)
        assertEquals(mem.boards[0]?.name, name)
        assertEquals(mem.boards[0]?.description, descritpion)
    }
    @Test
    fun `adds a User to a board`(){
        val mem = DataMem()
        val donkeyUserId =  mem.createUser("test", "test@").second
        val newDonkeyUserId =  mem.createUser("test2", "test2@").second

        val boardId = mem. createBoard("To Do", "ISEL project", donkeyUserId)
        mem.addUserToBoard(boardId, newDonkeyUserId)

        val sut = mem.boards[boardId]?.uid

        assertEquals(2, sut?.size)
        assertEquals(donkeyUserId, sut?.get(0))
        assertEquals(newDonkeyUserId, sut?.get(1))
    }
    @Test
    fun `get a board details`() {
        val mem = DataMem()
        val donkeyUser =  mem.createUser("test", "test@")

        val boardId = mem.createBoard("To Do", "ISEL project", donkeyUser.second)

        val sut = mem.getBoard(boardId)

        assertEquals(mem.boards[0], sut)
    }

    @Test
    fun `get a user available boards`(){
        val mem = DataMem()
        val donkeyUser =  mem.createUser("test", "test@").second

        val boardId = mem.createBoard("To Do", "ISEL project", donkeyUser)
        val boardId2 = mem.createBoard("To Do 2", "ISEL project 2", donkeyUser)
        val boardId3 = mem.createBoard("To Do 3", "ISEL project 3", donkeyUser)

        val sut = mem.getUserAvailableBoards(donkeyUser)
        assertEquals(listOf(boardId, boardId2, boardId3), sut)
    }
    //TODO("Not working yet, needs discussion")

}

