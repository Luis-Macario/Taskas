package pt.isel.ls.services.cards

import pt.isel.ls.database.AppDatabase
import pt.isel.ls.database.DataCard
import pt.isel.ls.database.DataListCards
import java.sql.Date

class CardServices(private val database: AppDatabase) {

    private val MAX_DATE = "+999999999-12-31"
    /**
     * Creates a new card in a list.
     *
     * @param bid id of the board that contains the list
     * @param lid id of the list that contains the task
     * @param name task's name
     * @param description  the task description
     * @param dueDate  the task's conclusion date, optional
     *
     * @return Card's unique identifier
     */
    fun createCard(bid:Int, lid:Int, name: String, description: String, dueDate: Date = Date.valueOf(MAX_DATE)): Int =
        database.createCard(bid,lid,name,description,dueDate).id

    /**
     * Get the set of cards in a list.
     *
     * @param lid list's unique identifier
     *
     * @return List of Card objects
     */
    fun getTaskListCards(lid: Int): DataListCards = database.getCardsFromList(lid)

    /**
     * Get the detailed information of a card.
     *
     * @param lid id of the list that contains the card
     * @param cid card's unique identifier
     *
     * @return Card Object
     */
    fun getCard(cid: Int): DataCard = database.getCardDetails(cid)

    /**
     * Moves a card, given the following parameter
     *
     * @param lid the destination list identifier
     *
     * @return ?
     */
    fun moveCard(lid: Int) = database
}