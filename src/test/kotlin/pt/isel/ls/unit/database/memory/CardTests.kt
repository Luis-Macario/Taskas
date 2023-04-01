package pt.isel.ls.unit.database.memory

import org.junit.Test
import pt.isel.ls.database.memory.CardNotFoundException
import pt.isel.ls.database.memory.ListNotFoundException
import pt.isel.ls.database.memory.TasksDataMem
import pt.isel.ls.domain.Card
import java.sql.Date
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CardTests {

    @Test
    fun `test create card and get details`() {
        val mem = TasksDataMem()

        val uId = mem.createUser(UUID.randomUUID().toString(), "Pedro", "pedro@gmail.com").id
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

        val msg = assertFailsWith<ListNotFoundException> {
            mem.createCard(invalidListId, "Team Workk", "some work ".repeat(1), Date.valueOf("2019-1-26"))
        }
        assertEquals("The list with the id provided doesn't exist", ListNotFoundException.description)
    }

    @Test
    fun `test getCardDetails given incorrect card id throws CardNotFoundException`() {
        val mem = TasksDataMem()

        val uId = mem.createUser(UUID.randomUUID().toString(), "Pedro", "pedro@gmail.com").id
        val bId = mem.createBoard(uId, "To Do".repeat(4), "ISEL project").id
        val lId = mem.createList(bId, "Some work 1").id

        val card = mem.createCard(lId, "Team Workk", "some work ".repeat(1), Date.valueOf("2019-1-26"))
        val sut = mem.getCardDetails(card.id)

        assertEquals(card, sut)

        val msg = assertFailsWith<CardNotFoundException> {
            mem.getCardDetails(2)
        }
        assertEquals("The card with the id provided doesn't exist", CardNotFoundException.description)
    }

    @Test
    fun `test get set of cards`() {
        val mem = TasksDataMem()

        val uId = mem.createUser(UUID.randomUUID().toString(), "Miguel", "miguel@gmail.com").id
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

        val uId = mem.createUser(UUID.randomUUID().toString(), "Miguel", "miguel@gmail.com").id
        val bId = mem.createBoard(uId, "To Do".repeat(4), "ISEL project").id
        val lId = mem.createList(bId, "Some work ").id

        val cardDate = Date.valueOf("2019-1-26")

        mem.createCard(lId, "Team Work ", "some work ".repeat(1), cardDate).id
        mem.cards[1] = Card(2, bId, lId, "Team Work ", "some work ".repeat(1), cardDate)

        val msg = assertFailsWith<CardNotFoundException> {
            mem.getCardsFromList(lId)
        }
        assertEquals("The card with the id provided doesn't exist", CardNotFoundException.description)

        val msg2 = assertFailsWith<ListNotFoundException> {
            mem.getCardsFromList(100)
        }
        assertEquals("The list with the id provided doesn't exist", ListNotFoundException.description)
    }

    @Test
    fun `test move a card to another taskList`() {
        val mem = TasksDataMem()

        val uId = mem.createUser(UUID.randomUUID().toString(), "Tiago", "tiago@gmail.com").id
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

        val uId = mem.createUser(UUID.randomUUID().toString(), "Tiago", "tiago@gmail.com").id
        val bId = mem.createBoard(uId, "To Do".repeat(4), "ISEL project").id
        val lId = mem.createList(bId, "Some work ").id
        val cId = mem.createCard(lId, "Team Work ", "some work ".repeat(1), Date.valueOf("2019-1-26")).id

        val msg = assertFailsWith<ListNotFoundException> {
            mem.moveCard(cId, 10)
        }
        assertEquals("The list with the id provided doesn't exist", ListNotFoundException.description)
    }

    @Test
    fun `test move a card given invalid card throws CardNotFoundException`() {
        val mem = TasksDataMem()

        val uId = mem.createUser(UUID.randomUUID().toString(), "Tiago", "tiago@gmail.com").id
        val bId = mem.createBoard(uId, "To Do".repeat(4), "ISEL project").id
        val lId = mem.createList(bId, "Some work ").id
        val invalidCardId = 10

        val msg = assertFailsWith<CardNotFoundException> {
            mem.moveCard(invalidCardId, lId)
        }
        assertEquals("The card with the id provided doesn't exist", CardNotFoundException.description)
    }
}
