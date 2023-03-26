package pt.isel.ls.utils

import pt.isel.ls.database.memory.*
import pt.isel.ls.domain.Board
import pt.isel.ls.domain.Card
import pt.isel.ls.domain.User
import pt.isel.ls.domain.UserBoard
import java.sql.Date
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

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
    fun `test create user with repeated email throws EmailAlreadyExistsException`() {
        val mem = TasksDataMem()
        val name = "Luigi"
        val email = "honorStudent@gmail.com"

        mem.createUser(name, email)

        assertFailsWith<EmailAlreadyExistsException> {
            mem.createUser("Ricky", email)
        }
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

        assertFailsWith<UserNotFoundException> { mem.getUserDetails(10) }
    }

    @Test
    fun `test get user details throws UserNotFoundException for a not created user`() {
        val mem = TasksDataMem()

        assertFailsWith<UserNotFoundException> {
            mem.getUserDetails(-1)
        }
    }

    @Test
    fun `test create board `() {
        val mem = TasksDataMem()
        val name = "To Do".repeat(4)
        val description = "ISEL project"
        val user = mem.createUser("Francisco M", "francisco@isel.pt")

        val sut = mem.createBoard(user.id, name, description)

        assertEquals(mem.boards[0]?.id, sut.id)
        assertEquals(mem.boards[0]?.name, name)
        assertEquals(mem.boards[0]?.description, description)
    }

    @Test
    fun `test create board throws BoardNameAlreadyExistsException when creating a board with a repeated name`() {
        val mem = TasksDataMem()
        val user = mem.createUser("Francisco M", "francisco@isel.pt")
        val boardName = "To Do".repeat(4)

        mem.createBoard(user.id, boardName, "ISEL project")

        assertFailsWith<BoardNameAlreadyExistsException> {
            mem.createBoard(user.id, boardName, "Outra descrição")
        }
    }

    @Test
    fun `test create board throws UserNotFoundException when creating a board with a invalid user`() {
        val mem = TasksDataMem()
        val user = mem.createUser("Francisco M", "francisco@isel.pt")
        val boardName = "To Do".repeat(4)

        assertFailsWith<UserNotFoundException> {
            mem.createBoard(100, boardName, "Outra descrição")
        }
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
    fun `test add User to a board throws UserNotFoundException`() {
        val mem = TasksDataMem()
        val donkeyUser = mem.createUser("test", "test@gmail.com")
        val board = mem.createBoard(donkeyUser.id, "To Do".repeat(4), "ISEL project")

        assertFailsWith<UserNotFoundException> {
            mem.addUserToBoard(100, board.id)
        }

    }

    @Test
    fun `test add User to a board throws BoardNotFoundException`() {
        val mem = TasksDataMem()
        val donkeyUser = mem.createUser("test", "test@gmail.com")

        assertFailsWith<BoardNotFoundException> {
            mem.addUserToBoard(donkeyUser.id, 100)
        }
    }

    @Test
    fun `test add User to a board throws UserAlreadyExistsInBoardException`() {
        val mem = TasksDataMem()
        val donkeyUser = mem.createUser("test", "test@gmail.com")
        val board = mem.createBoard(donkeyUser.id, "To Do".repeat(4), "ISEL project")

        assertFailsWith<UserAlreadyExistsInBoardException> {
            mem.addUserToBoard(donkeyUser.id, board.id)
        }

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

        //Test UserBoard Ids
        assertEquals(mem.userBoard[0]?.uId, donkeyUser.id)
        assertEquals(mem.userBoard[0]?.bId, board.id)
    }

    @Test
    fun `test get board details throws BoardNotFoundException`() {
        val mem = TasksDataMem()

        assertFailsWith<BoardNotFoundException> {
            mem.getBoardDetails(100)
        }
    }

    @Test
    fun `test get user available boards`() {
        val mem = TasksDataMem()
        val donkeyUser = mem.createUser("test", "test@gmail.com")

        val b = mem.getBoardDetails(
            mem.createBoard(donkeyUser.id, "To Do".repeat(4), "ISEL project").id
        )

        val b2 = mem.getBoardDetails(
            mem.createBoard(donkeyUser.id, "To Do 2".repeat(4), "ISEL project 2").id
        )

        val b3 = mem.getBoardDetails(
            mem.createBoard(donkeyUser.id, "To Do 3".repeat(4), "ISEL project 3").id
        )

        val sut = mem.getBoardsFromUser(donkeyUser.id)
        assertEquals(listOf(b, b2, b3), sut)
    }

    @Test
    fun `test get user available boards throws BoardNotFoundException`() {
        val mem = TasksDataMem()
        val donkeyUser = mem.createUser("test", "test@gmail.com")


        mem.createBoard(donkeyUser.id, "To Do".repeat(4), "ISEL project").id
        //Hammer boards
        mem.userBoard[1] = UserBoard(donkeyUser.id, 10)

        assertFailsWith<BoardNotFoundException> {
            mem.getBoardsFromUser(donkeyUser.id)
        }
    }


    @Test
    fun `test create taskList successfully`() {
        val mem = TasksDataMem()
        val name = "Backend Work"
        val donkeyUser = mem.createUser("test", "test@gmail.com")
        val board = mem.createBoard(donkeyUser.id, "To Do".repeat(4), "ISEL project")

        val sut = mem.createList(board.id, name)

        assertEquals(mem.taskLists[0]?.id, sut.id)
    }

    @Test
    fun `test create taskList throws BoardNotFoundException`() {
        val mem = TasksDataMem()
        val name = "Backend Work"

        assertFailsWith<BoardNotFoundException> {
            mem.createList(0, name)
        }
    }

    @Test
    fun `test get tasklist details`() {
        val mem = TasksDataMem()
        val donkeyUser = mem.createUser("test", "test@gmail.com")
        val board = mem.createBoard(donkeyUser.id, "To Do".repeat(4), "ISEL project")
        val taskList = mem.createList(board.id, "some work..")

        val sut = mem.getListDetails(taskList.id)

        assertEquals(mem.taskLists[0]?.id, sut.id)
        assertEquals(mem.taskLists[0]?.name, sut.name)
        assertEquals(mem.taskLists[0]?.bid, sut.bid)
    }

    @Test
    fun `test get taskList details throws ListNotFoundException`() {
        val mem = TasksDataMem()

        assertFailsWith<ListNotFoundException> {
            mem.getListDetails(0)
        }
    }

    @Test
    fun `test getListsFromBoard giving the correct id`() {
        val mem = TasksDataMem()
        val donkeyUser = mem.createUser("test", "test@gmail.com")
        val board = mem.createBoard(donkeyUser.id, "To Do".repeat(4), "ISEL project")

        val l1 = mem.getListDetails(mem.createList(board.id, "Some work 1").id)
        val l2 = mem.getListDetails(mem.createList(board.id, "Some work 2").id)
        val l3 = mem.getListDetails(mem.createList(board.id, "Some work 3").id)

        val sut = mem.getListsFromBoard(board.id)

        //assertEquals(3, sut.size)
        assertEquals(listOf(l1, l2, l3), sut)
    }

    @Test
    fun `test getListsFromBoard giving wrong board id throws BoardNotFoundException`() {
        val mem = TasksDataMem()

        assertFailsWith<BoardNotFoundException> {
            mem.getListsFromBoard(1)
        }
    }


    @Test
    fun `test create card and get details`() {
        val mem = TasksDataMem()

        val uId = mem.createUser("Pedro", "pedro@gmail.com").id
        val bId = mem.createBoard(uId, "To Do".repeat(4), "ISEL project").id
        val lId = mem.createList(bId, "Some work 1").id

        val card = mem.createCard(lId, "Team Workk", "some work ".repeat(1), Date.valueOf("2019-1-26"))
        val sut = mem.getCardDetails(card.id)

        assertEquals(mem.cards[0]?.id, sut.id)
        assertEquals(mem.cards[0]?.name, sut.name)
        assertEquals(mem.cards[0]?.description, sut.description)
        assertEquals(mem.cards[0]?.lid, sut.id)
        assertEquals(mem.cards[0]?.initDate, sut.initDate)
    }

    @Test
    fun `test create card with invalid list id throws ListNotFoundException`() {
        val mem = TasksDataMem()
        val invalidListId = 1

        assertFailsWith<ListNotFoundException> {
            mem.createCard(invalidListId, "Team Workk", "some work ".repeat(1), Date.valueOf("2019-1-26"))
        }
    }

    @Test
    fun `test getCardDetails given incorrect card id throws CardNotFoundException`() {
        val mem = TasksDataMem()

        val uId = mem.createUser("Pedro", "pedro@gmail.com").id
        val bId = mem.createBoard(uId, "To Do".repeat(4), "ISEL project").id
        val lId = mem.createList(bId, "Some work 1").id

        val card = mem.createCard(lId, "Team Workk", "some work ".repeat(1), Date.valueOf("2019-1-26"))
        val sut = mem.getCardDetails(card.id)

        assertEquals(card, sut)

        assertFailsWith<CardNotFoundException> {
            mem.getCardDetails(2)
        }

    }

    @Test
    fun `test get set of cards`() {
        val mem = TasksDataMem()

        val uId = mem.createUser("Miguel", "miguel@gmail.com").id
        val bId = mem.createBoard(uId, "To Do".repeat(4), "ISEL project").id
        val lId = mem.createList(bId, "Some work ").id

        val cardDate = Date.valueOf("2019-1-26")
        val c = mem.getCardDetails(
            mem.createCard(lId, "Team Work ", "some work ".repeat(1), cardDate).id
        )
        val c2 = mem.getCardDetails(
            mem.createCard(lId, "Team Work2", "some work 2".repeat(1), cardDate).id
        )
        val c3 =
            mem.getCardDetails(
                mem.createCard(lId, "Team Work3", "some work 3".repeat(1), cardDate).id
            )
        val c4 =
            mem.getCardDetails(
                mem.createCard(lId, "Team Work4", "some work 4".repeat(1), cardDate).id
            )

        val sut = mem.getCardsFromList(lId)

        assertEquals(listOf(c, c2, c3, c4), sut)
    }

    @Test
    fun `test get set of cards with invalid cards throws CardNotFoundException `() {
        val mem = TasksDataMem()

        val uId = mem.createUser("Miguel", "miguel@gmail.com").id
        val bId = mem.createBoard(uId, "To Do".repeat(4), "ISEL project").id
        val lId = mem.createList(bId, "Some work ").id

        val cardDate = Date.valueOf("2019-1-26")

        mem.createCard(lId, "Team Work ", "some work ".repeat(1), cardDate).id
        mem.cards[1] = Card(2, bId, lId, "Team Work ", "some work ".repeat(1), cardDate)

        assertFailsWith<CardNotFoundException> {
            mem.getCardsFromList(lId)
        }

        assertFailsWith<ListNotFoundException> {
            mem.getCardsFromList(100)
        }
    }

    @Test
    fun `test move a card to another taskList`() {
        val mem = TasksDataMem()

        val uId = mem.createUser("Tiago", "tiago@gmail.com").id
        val bId = mem.createBoard(uId, "To Do".repeat(4), "ISEL project").id
        val lId = mem.createList(bId, "Some work ").id
        val cId = mem.createCard(lId, "Team Work ", "some work ".repeat(1), Date.valueOf("2019-1-26")).id

        assertEquals(0, mem.cards[0]?.lid)

        val newList = mem.createList(bId, "Some work ")
        mem.moveCard(cId, newList.id)
        assertEquals(newList.id, mem.cards[0]?.lid)
    }

    @Test
    fun `test move a card to another invalid list thorws ListNotFoundException`() {
        val mem = TasksDataMem()

        val uId = mem.createUser("Tiago", "tiago@gmail.com").id
        val bId = mem.createBoard(uId, "To Do".repeat(4), "ISEL project").id
        val lId = mem.createList(bId, "Some work ").id
        val cId = mem.createCard(lId, "Team Work ", "some work ".repeat(1), Date.valueOf("2019-1-26")).id

        assertFailsWith<ListNotFoundException> {
            mem.moveCard(cId, 10)
        }
    }

}
