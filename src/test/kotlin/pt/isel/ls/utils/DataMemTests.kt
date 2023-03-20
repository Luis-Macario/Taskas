package pt.isel.ls.utils

import pt.isel.ls.database.DataSimpleBoard
import pt.isel.ls.database.DataSimpleList
import kotlin.test.Test
import pt.isel.ls.database.memory.TasksDataMem
import pt.isel.ls.domain.User
import kotlin.test.assertEquals

class DataMemTests {


    @Test
    fun `test create user manually`() {
        val mem = TasksDataMem()

        val name = "Francisco"
        val email = "honorStudent@gmail.com"

        val user = User(0, name, email, "token")
        mem.users[0] = user

        assertEquals(mem.users[0]?.name, name)
        assertEquals(mem.users[0]?.email, email)
    }

    @Test
    fun `test create user`() {
        val mem = TasksDataMem()

        val name = "Luigi"
        val email = "honorStudent@gmail.com"

        val sut = mem.createUser(name, email)

        assertEquals(mem.users[0]?.name, name)
        assertEquals(mem.users[0]?.email, email)
        assertEquals(mem.users[0]?.id, sut.id)
        assertEquals(mem.users[0]?.token, sut.token)
    }

    @Test
    fun `test get user details`() {
        val mem = TasksDataMem()

        val name = "Tweeny"
        val email = "honorStudent@gmail.com"

        val user = mem.createUser(name, email)
        val sut = mem.getUserDetails(user.id)

        assertEquals(name, sut.name)
        assertEquals(email, sut.email)
    }

    @Test
    fun `test create board `() {
        val mem = TasksDataMem()
        val name = "To Do".repeat(4)
        val descritpion = "ISEL project"
        val user = mem.createUser("Francisco M", "francisco@isel.pt")

        val sut = mem.createBoard(user.id, name, descritpion)

        assertEquals(mem.boards[0]?.id, sut.id)
        assertEquals(mem.boards[0]?.name, name)
        assertEquals(mem.boards[0]?.description, descritpion)
    }

    @Test
    fun `test add User to a board`() {
        val mem = TasksDataMem()
        val donkeyUser = mem.createUser("test", "test@gmail.com")
        val newDonkeyUser = mem.createUser("test2", "test2@gmail.com")

        val board = mem.createBoard(donkeyUser.id, "To Do".repeat(4), "ISEL project")
        mem.addUserToBoard(newDonkeyUser.id, board.id)

        val sut = mem.userBoard

        assertEquals(2, sut.size)
        assertEquals(board.id, sut[0]?.bId)
        assertEquals(donkeyUser.id, sut[0]?.uId)
        assertEquals(board.id, sut[1]?.bId)
        assertEquals(newDonkeyUser.id, sut[1]?.uId)
    }

    @Test
    fun `test get board details`() {
        val mem = TasksDataMem()
        val donkeyUser = mem.createUser("test", "test@gmail.com")
        val board = mem.createBoard(donkeyUser.id, "To Do".repeat(4), "ISEL project")

        val sut = mem.getBoardDetails(board.id)

        assertEquals(mem.boards[0]?.id, sut.id)
        assertEquals(mem.boards[0]?.name, sut.name)
        assertEquals(mem.boards[0]?.description, sut.description)
        assertEquals(mem.boards[0]?.uid, sut.users[0].id)
    }
/*
    @Test
    fun `test get user available boards`() {
        val mem = TasksDataMem()
        val donkeyUser = mem.createUser("test", "test@gmail.com")

        val bId = mem.createBoard(donkeyUser.id, "To Do".repeat(4), "ISEL project")
        val bId2 = mem.createBoard(donkeyUser.id,"To Do 2".repeat(4), "ISEL project 2")
        val bId3 = mem.createBoard(donkeyUser.id,"To Do 3".repeat(4), "ISEL project 3")

        val bAux = mem.getBoardDetails(bId.id)
        val bAux2 = mem.getBoardDetails(bId2.id)
        val bAux3 = mem.getBoardDetails(bId3.id)

        val board = DataSimpleBoard(bAux.id, bAux.name, bAux.description)
        val board2 = DataSimpleBoard(bAux2.id, bAux2.name, bAux2.description)
        val board3 = DataSimpleBoard(bAux2.id, bAux2.name, bAux3.description)

        val sut = mem.getBoardsFromUser(donkeyUser.id)
        assertEquals(listOf(board, board2, board3), sut.boards)
    }

    @Test
    fun `test create tasklist successfully`() {
        val mem = TasksDataMem()
        val name = "Backed Work"

        val boardId = mem.createBoard("To Do".repeat(4), "ISEL project")
        val sut = mem.createTaskList(name, boardId)

        assertEquals(mem.taskLists[0]?.id, sut)
    }
    @Test
    fun `test get tasklist details`() {
        val mem = TasksDataMem()
        val boardId = mem.createBoard("To Do".repeat(4), "ISEL project")
        val taskList = mem.createTaskList("some work..", boardId)

        val sut = mem.getTaskListDetails(taskList)

        assertEquals(mem.taskLists[0], sut)
    }

    @Test
    fun `test getTaskListsOfBoard giving the correct id`(){
        val mem = TasksDataMem()

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
        val mem = TasksDataMem()

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
        val mem = TasksDataMem()

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
        val mem = TasksDataMem()

        val uId = mem.createUser("Tiago", "tiago@gmail.com").second
        val bId = mem.createBoard("To Do".repeat(4), "ISEL project")
        mem.addUserToBoard(bId, uId)
        val lId = mem.createTaskList("Some work ", bId)
        val cId =  mem.createCard(uId, bId, lId, "Team Work ", "some work ".repeat(1), Date(1L), Date(2L))

        assertEquals(0, mem.cards[0]?.lid)

        mem.moveCard(cId, null)

        assertEquals(null, mem.cards[0]?.lid)
    }
    */

}

