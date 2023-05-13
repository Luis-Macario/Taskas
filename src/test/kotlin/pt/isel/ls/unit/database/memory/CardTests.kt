package pt.isel.ls.unit.database.memory

import pt.isel.ls.database.memory.ListNotFoundException
import pt.isel.ls.database.memory.TasksDataMem
import java.sql.Date
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CardTests {
    // TODO: FIX THE COMMENTED TESTS
    /*
    @Test
    fun `test create card and get details`() {
        val mem = TasksDataMem()

        val uId = mem.createUser(UUID.randomUUID().toString(), "Pedro", "pedro@gmail.com").id
        val bId = mem.createBoard(uId, "To Do".repeat(4), "ISEL project").id
        val lId = mem.createList(bId, "Some work 1").id

        val card = mem.createCard(lId, "Team Workk", "some work ".repeat(1), Date.valueOf("2025-1-26"), Date.valueOf("2025-1-26"))
        val sut = mem.getCardDetails(card.id)

        assertEquals(mem.cards[0]?.id, sut.id)
        assertEquals(mem.cards[0]?.name, sut.name)
        assertEquals(mem.cards[0]?.description, sut.description)
        assertEquals(mem.cards[0]?.lid, sut.id)
        assertEquals(mem.cards[0]?.initDate, sut.initDate)
    }
    */
    @Test
    fun `test create card with invalid list id throws ListNotFoundException`() {
        val mem = TasksDataMem()
        val invalidListId = 1

        val msg = assertFailsWith<ListNotFoundException> {
            mem.createCard(
                invalidListId,
                "Team Workk",
                "some work ".repeat(1),
                Date.valueOf("2019-1-26"),
                Date.valueOf("2025-1-26")
            )
        }
        assertEquals(msg.description, ListNotFoundException.description)
    }

    /*
    @Test
    fun `test getCardDetails given incorrect card id throws CardNotFoundException`() {
        val mem = TasksDataMem()

        val uId = mem.createUser(UUID.randomUUID().toString(), "Pedro", "pedro@gmail.com").id
        val bId = mem.createBoard(uId, "To Do".repeat(4), "ISEL project").id
        val lId = mem.createList(bId, "Some work 1").id

        val card = mem.createCard(lId, "Team Workk", "some work ".repeat(1),Date.valueOf("2023-4-28"), Date.valueOf("2025-1-26"))
        val sut = mem.getCardDetails(card.id)

        assertEquals(card, sut)

        val msg = assertFailsWith<CardNotFoundException> {
            mem.getCardDetails(2)
        }
        assertEquals(msg.description, CardNotFoundException.description)
    }

    @Test
    fun `test get set of cards`() {
        val mem = TasksDataMem()

        val uId = mem.createUser(UUID.randomUUID().toString(), "Miguel", "miguel@gmail.com").id
        val bId = mem.createBoard(uId, "To Do".repeat(4), "ISEL project").id
        val lId = mem.createList(bId, "Some work ").id
        val initDate = Date.valueOf("2023-4-28")
        val cardDate = Date.valueOf("2025-1-26")
        val c = mem.getCardDetails(
            mem.createCard(lId, "Team Work ", "some work ".repeat(1),initDate, cardDate).id
        )
        val c2 = mem.getCardDetails(
            mem.createCard(lId, "Team Work2", "some work 2".repeat(1),initDate, cardDate).id
        )
        val c3 =
            mem.getCardDetails(
                mem.createCard(lId, "Team Work3", "some work 3".repeat(1),initDate, cardDate).id
            )
        val c4 =
            mem.getCardDetails(
                mem.createCard(lId, "Team Work4", "some work 4".repeat(1),initDate, cardDate).id
            )

        val sut = mem.getCardsFromList(lId, bId)

        assertEquals(listOf(c, c2, c3, c4), sut)
    }

    @Test
    fun `test get set of cards with invalid cards throws CardNotFoundException `() {
        val mem = TasksDataMem()

        val uId = mem.createUser(UUID.randomUUID().toString(), "Miguel", "miguel@gmail.com").id
        val bId = mem.createBoard(uId, "To Do".repeat(4), "ISEL project").id
        val lId = mem.createList(bId, "Some work ").id
        val initDate = Date.valueOf("2023-4-28")
        val cardDate = Date.valueOf("2025-1-26")

        mem.createCard(lId, "Team Work ", "some work ".repeat(1),initDate, cardDate).id
        mem.cards[1] = Card(2, bId, lId, "Team Work ", "some work ".repeat(1), cardDate)

        val msg = assertFailsWith<CardNotFoundException> {
            mem.getCardsFromList(lId, bId)
        }
        assertEquals(msg.description, CardNotFoundException.description)

        val msg2 = assertFailsWith<ListNotFoundException> {
            mem.getCardsFromList(100, bId)
        }
        assertEquals(msg2.description, ListNotFoundException.description)
    }

    @Test
    fun `test move a card to another taskList`() {
        val mem = TasksDataMem()

        val uId = mem.createUser(UUID.randomUUID().toString(), "Tiago", "tiago@gmail.com").id
        val bId = mem.createBoard(uId, "To Do".repeat(4), "ISEL project").id
        val lId = mem.createList(bId, "Some work for next week").id
        val cId = mem.createCard(lId, "Team Work ", "some work ".repeat(1),Date.valueOf("2023-4-28"), Date.valueOf("2025-1-26")).id

        assertEquals(0, mem.cards[0]?.lid)

        val newList = mem.createList(bId, "Some work ")
        mem.moveCard(cId, newList.id, 0)
        assertEquals(newList.id, mem.cards[0]?.lid)
    }

    @Test
    fun `test move a card to another invalid list thorws ListNotFoundException`() {
        val mem = TasksDataMem()

        val uId = mem.createUser(UUID.randomUUID().toString(), "Tiago", "tiago@gmail.com").id
        val bId = mem.createBoard(uId, "To Do".repeat(4), "ISEL project").id
        val lId = mem.createList(bId, "Some work ").id
        val cId = mem.createCard(lId, "Team Work ", "some work ".repeat(1),Date.valueOf("2023-4-28"), Date.valueOf("2025-1-26")).id

        val msg = assertFailsWith<ListNotFoundException> {
            mem.moveCard(cId, 10, 0)
        }
        assertEquals(msg.description, ListNotFoundException.description)
    }

    @Test
    fun `test move a card given invalid card throws CardNotFoundException`() {
        val mem = TasksDataMem()

        val uId = mem.createUser(UUID.randomUUID().toString(), "Tiago", "tiago@gmail.com").id
        val bId = mem.createBoard(uId, "To Do".repeat(4), "ISEL project").id
        val lId = mem.createList(bId, "Some work ").id
        val invalidCardId = 10

        val msg = assertFailsWith<CardNotFoundException> {
            mem.moveCard(invalidCardId, lId, 0)
        }
        assertEquals(msg.description, CardNotFoundException.description)
    }
    */
}
