package pt.isel.ls.utils

import kotlin.test.Test
import pt.isel.ls.DataMem
import pt.isel.ls.User
import java.sql.Date
import kotlin.test.assertEquals

class DataMemTests {


    @Test
    fun `test create user manually`() {
        val mem = DataMem()

        val name = "Francisco"
        val email = "honorStudent@gmail.com"

        val user = User(0, name, email, "token")
        mem.users[0] = user

        assertEquals(mem.users[0]?.name, name)
        assertEquals(mem.users[0]?.email, email)
    }

    @Test
    fun `test create user`() {
        val mem = DataMem()

        val name = "Luigi"
        val email = "honorStudent@gmail.com"

        val sut = mem.createUser(name, email)

        assertEquals(mem.users[0]?.name, name)
        assertEquals(mem.users[0]?.email, email)
        assertEquals(mem.users[0]?.id, sut.second)
        assertEquals(mem.users[0]?.token, sut.first)
    }

    @Test
    fun `test get user details`() {
        val mem = DataMem()

        val name = "Tweeny"
        val email = "honorStudent@gmail.com"

        val user = mem.createUser(name, email)
        val sut = mem.getUser(user.second)      //user.second -> id

        assertEquals(name, sut.name)
        assertEquals(email, sut.email)
    }

    @Test
    fun `test create board `() {
        val mem = DataMem()
        val name = "To Do".repeat(4)
        val descritpion = "ISEL project"

        val sut = mem.createBoard(name, descritpion)

        assertEquals(mem.boards[0]?.id, sut)
        assertEquals(mem.boards[0]?.name, name)
        assertEquals(mem.boards[0]?.description, descritpion)
    }

    @Test
    fun `test add User to a board`() {
        val mem = DataMem()
        val donkeyUserId = mem.createUser("test", "test@gmail.com").second
        val newDonkeyUserId = mem.createUser("test2", "test2@gmail.com").second

        val boardId = mem.createBoard("To Do".repeat(4), "ISEL project")
        mem.addUserToBoard(boardId, donkeyUserId)
        mem.addUserToBoard(boardId, newDonkeyUserId)

        val sut = mem.userBoard

        assertEquals(2, sut.size)
        assertEquals(boardId, sut[0]?.bId)
        assertEquals(donkeyUserId, sut[0]?.uId)
        assertEquals(boardId, sut[1]?.bId)
        assertEquals(newDonkeyUserId, sut[1]?.uId)
    }

    @Test
    fun `test get board details`() {
        val mem = DataMem()
        val boardId = mem.createBoard("To Do".repeat(4), "ISEL project")
        val sut = mem.getBoard(boardId)

        assertEquals(mem.boards[0], sut)
    }

    @Test
    fun `test get user available boards`() {
        val mem = DataMem()
        val donkeyUser = mem.createUser("test", "test@gmail.com").second

        val boardId = mem.createBoard("To Do".repeat(4), "ISEL project")
        val boardId2 = mem.createBoard("To Do 2".repeat(4), "ISEL project 2")
        val boardId3 = mem.createBoard("To Do 3".repeat(4), "ISEL project 3")

        mem.addUserToBoard(boardId, donkeyUser)
        mem.addUserToBoard(boardId, donkeyUser)
        mem.addUserToBoard(boardId, donkeyUser)

        val sut = mem.getUserAvailableBoards(donkeyUser)
        assertEquals(listOf(boardId, boardId2, boardId3), sut)
        assertEquals(mem.boards.keys.toList(), sut)
    }

    @Test
    fun `test create tasklist successfully`() {
        val mem = DataMem()
        val name = "Backed Work"

        val boardId = mem.createBoard("To Do".repeat(4), "ISEL project")
        val sut = mem.createTaskList(name, boardId)

        assertEquals(mem.taskLists[0]?.id, sut)
    }
    @Test
    fun `test get tasklist details`() {
        val mem = DataMem()
        val boardId = mem.createBoard("To Do".repeat(4), "ISEL project")
        val taskList = mem.createTaskList("some work..", boardId)

        val sut = mem.getTaskListDetails(taskList)

        assertEquals(mem.taskLists[0], sut)
    }

    @Test
    fun `test getTaskListsOfBoard giving the correct id`(){
        val mem = DataMem()

        val boardId = mem.createBoard("To Do".repeat(4), "ISEL project")

        val l1 = mem.createTaskList("Some work 1", boardId)
        val l2 = mem.createTaskList("Some work 2", boardId)
        val l3 = mem.createTaskList("Some work 3", boardId)

        val sut =  mem.getTaskListsOfBoard(boardId)

        assertEquals(3, sut.size)
        assertEquals(listOf(l1,l2,l3), sut)
        assertEquals(mem.taskLists.keys.toList(), sut)
    }

    @Test
    fun `test create card and get details`(){
        val mem =  DataMem()

        val uId = mem.createUser("Pedro", "pedro@gmail.com").second
        val bId = mem.createBoard("To Do".repeat(4), "ISEL project")
        mem.addUserToBoard(bId, uId)
        val lId = mem.createTaskList("Some work 1", bId)

        val cardId =  mem.createCard(uId, bId, lId, "Team Workk", "some work ".repeat(1), Date(1L), Date(2L))
        val sut = mem.getCardInformation(cardId)

        assertEquals(mem.cards[0], sut)
    }

    @Test
    fun `test get set of cards`(){
        val mem =  DataMem()

        val uId = mem.createUser("Miguel", "miguel@gmail.com").second
        val bId = mem.createBoard("To Do".repeat(4), "ISEL project")
        mem.addUserToBoard(bId, uId)
        val lId = mem.createTaskList("Some work ", bId)

        val cId =  mem.createCard(uId, bId, lId, "Team Work ", "some work ".repeat(1), Date(1L), Date(2L))
        val cId2 =  mem.createCard(uId, bId, lId, "Team Work2", "some work 2".repeat(1), Date(2L), Date(3L))
        val cId3 =  mem.createCard(uId, bId, lId, "Team Work3", "some work 3".repeat(1), Date(3L), Date(4L))
        val cId4 =  mem.createCard(uId, bId, lId, "Team Work4", "some work 4".repeat(1), Date(4L), Date(5L))

        val sut = mem.getSetOfCards(lId)

        assertEquals(listOf(cId,cId2,cId3, cId4), sut)
        assertEquals(mem.cards.keys.toList(), sut)
    }

    @Test
    fun `test move a card to another taskList`(){
        val mem =  DataMem()

        val uId = mem.createUser("Tiago", "tiago@gmail.com").second
        val bId = mem.createBoard("To Do".repeat(4), "ISEL project")
        mem.addUserToBoard(bId, uId)
        val lId = mem.createTaskList("Some work ", bId)
        val cId =  mem.createCard(uId, bId, lId, "Team Work ", "some work ".repeat(1), Date(1L), Date(2L))

        assertEquals(0, mem.cards[0]?.lid)

        mem.moveCard(cId, null)

        assertEquals(null, mem.cards[0]?.lid)
    }
}

