package pt.isel.ls.services.cards

import pt.isel.ls.api.dto.card.MoveCardRequest
import pt.isel.ls.database.AppDatabase
import pt.isel.ls.domain.Card
import pt.isel.ls.domain.checkCardCredentials
import pt.isel.ls.services.utils.MAX_DATE
import pt.isel.ls.services.utils.checkToken
import pt.isel.ls.services.utils.exceptions.IllegalCardAccessException
import pt.isel.ls.services.utils.exceptions.IllegalListAccessException
import pt.isel.ls.services.utils.exceptions.IllegalMoveCardRequestException
import java.sql.Date

class CardServices(private val database: AppDatabase) {

    /**
     * Creates a new card in a list.
     *
     * @param token token of the user creating the card
     * @param lid id of the list that contains the task
     * @param name task's name
     * @param description  the task description
     * @param dueDate  the task's conclusion date, optional
     *
     * @throws IllegalListAccessException if user doesn't have access to the list
     *
     * @return Card's unique identifier
     */
    fun createCard(
        token: String,
        lid: Int,
        name: String,
        description: String,
        initDate: String,
        dueDate: String? = null
    ): Card {
        checkToken(token)
        checkCardCredentials(name, description, initDate, dueDate)
        val list = database.getListDetails(lid)
        if (!database.checkUserTokenExistsInBoard(token, list.bid)) throw IllegalListAccessException
        val date = if (dueDate != null) Date.valueOf(dueDate) else Date.valueOf(MAX_DATE)

        println("Services: initDate = $initDate, valueOf = ${Date.valueOf(initDate)}")

        return database.createCard(lid, name, description, Date.valueOf(initDate), date)
    }

    /**
     * Get the detailed information of a card.
     *
     * @param token token of the user requesting card's details
     * @param cid card's unique identifier
     *
     * @throws IllegalCardAccessException if user doesn't have access to the card
     *
     * @return Card Object
     */
    fun getCardDetails(token: String, cid: Int): Card {
        checkToken(token)
        val card = database.getCardDetails(cid)
        if (!database.checkUserTokenExistsInBoard(token, card.bid)) throw IllegalCardAccessException

        return card
    }

    /**
     * Moves a card, given the following parameter
     *
     * @param token token od the user moving the card
     * @param cid the id of the card we want to move
     * @param request the destination list identifier
     *
     * @throws IllegalMoveCardRequestException if the source and destination lists are not the same
     * @throws IllegalCardAccessException if user doesn't have access to that card
     */
    fun moveCard(token: String, cid: Int, request: MoveCardRequest) {
        checkToken(token)
        val card = database.getCardDetails(cid)
        if (!database.checkUserTokenExistsInBoard(token, card.bid)) throw IllegalCardAccessException
        val destList = database.getListDetails(request.listID)

        if (card.bid != destList.bid) throw IllegalMoveCardRequestException
        database.moveCard(cid, request.listID, 0)
    }

    fun deleteCard(token: String, cid: Int) {
        checkToken(token)
        val card = database.getCardDetails(cid)
        if (!database.checkUserTokenExistsInBoard(token, card.bid)) throw IllegalCardAccessException

        database.deleteCard(cid)
    }
}
