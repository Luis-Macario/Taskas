package pt.isel.ls.unit.database.memory

import pt.isel.ls.database.memory.TasksDataMem
import java.sql.Date
import kotlin.test.Test
import kotlin.test.assertEquals

class CardTests {
    private val mem = TasksDataMem()
    private val tokenA = "7d444840-9dc0-11d1-b245-5ffdce74fad1"
    private val passwordA = "132513E5601D28F9DBDEBD2590514E171FEFEC9A6BE60417D79B8D626077C3FB"
    private val userA: Int = mem.createUser(tokenA, "Teste RFL", "a46631@alunos.isel.pt", passwordA)
    private val boardA : Int = mem.createBoard(userA, "To Do".repeat(4), "ISEL project")
    private val listA : Int = mem.createList(boardA, "Some work 1")

    @Test
    fun `test create card and get details`() {
        val lId = listA
        val card = mem.createCard(lId, "Team Workk", "some work ".repeat(1), Date.valueOf("2025-1-26"), Date.valueOf("2025-1-27"))
        val sut = mem.getCardDetails(card)

        assertEquals(mem.cards[0]?.id, sut.id)
        assertEquals(mem.cards[0]?.name, sut.name)
        assertEquals(mem.cards[0]?.description, sut.description)
        assertEquals(mem.cards[0]?.lid, sut.id)
        assertEquals(mem.cards[0]?.initDate, sut.initDate)
    }

    @Test
    fun `test get set of cards`() {
        val lId = listA
        val initDate = Date.valueOf("2023-4-28")
        val cardDate = Date.valueOf("2025-1-26")
        val c = mem.getCardDetails(
            mem.createCard(lId, "Team Work ", "some work ".repeat(1),initDate, cardDate)
        )
        val c2 = mem.getCardDetails(
            mem.createCard(lId, "Team Work2", "some work 2".repeat(1),initDate, cardDate)
        )
        val c3 =
            mem.getCardDetails(
                mem.createCard(lId, "Team Work3", "some work 3".repeat(1),initDate, cardDate)
            )
        val c4 =
            mem.getCardDetails(
                mem.createCard(lId, "Team Work4", "some work 4".repeat(1),initDate, cardDate)
            )

        val sut = mem.getCardsFromList(lId, boardA)

        assertEquals(listOf(c, c2, c3, c4), sut)
    }


    @Test
    fun `test move a card to another taskList`() {
        val lId = listA
        val cId = mem.createCard(lId, "Team Work ", "some work ".repeat(1),Date.valueOf("2023-4-28"), Date.valueOf("2025-1-26"))

        assertEquals(0, mem.cards[0]?.lid)

        val newList = mem.createList(boardA, "Some work ")
        mem.moveCard(cId, newList, 0)
        assertEquals(newList, mem.cards[0]?.lid)
    }
}
