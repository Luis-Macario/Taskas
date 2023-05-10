package pt.isel.ls.services.lists

import pt.isel.ls.database.AppDatabase
import pt.isel.ls.database.memory.BoardNotFoundException
import pt.isel.ls.database.memory.TaskListAlreadyExistsInBoardException
import pt.isel.ls.domain.Card
import pt.isel.ls.domain.TaskList
import pt.isel.ls.domain.checkListCredentials
import pt.isel.ls.services.utils.checkToken
import pt.isel.ls.services.utils.exceptions.IllegalBoardAccessException
import pt.isel.ls.services.utils.exceptions.IllegalListAccessException

class ListServices(private val database: AppDatabase) {
    /**
     * Creates a new list on a board
     *
     * @param token token of the user creating the list
     * @param bid id of the board containing this list
     * @param name list's name
     *
     * @throws IllegalBoardAccessException if user doesn't have access to the board specified by bid
     *
     * @return list unique identifier
     */
    fun createList(token: String, bid: Int, name: String): TaskList {
        checkToken(token)
        checkListCredentials(name)
        if (!database.checkBoardExists(bid)) throw BoardNotFoundException
        if (!database.checkUserTokenExistsInBoard(token, bid)) throw IllegalBoardAccessException
        if (database.checkListAlreadyExistsInBoard(name, bid)) throw TaskListAlreadyExistsInBoardException

        val id = database.createList(bid, name)

        return TaskList(id, bid, name, false, listOf())
    }

    /**
     * Get detailed information of a list.
     *
     * @param token token of the user requesting the list
     * @param lid list's unique identifier
     *
     * @throws IllegalListAccessException if user doesn't have access to the board specified by bid
     * @return TaskList object
     */
    fun getList(token: String, lid: Int): TaskList {
        checkToken(token)
        val simpleList = database.getListDetails(lid)
        if (!database.checkUserTokenExistsInBoard(token, simpleList.bid)) throw IllegalListAccessException

        val cards = database.getCardsFromList(simpleList.id, simpleList.bid)

        return TaskList(
            lid,
            simpleList.bid,
            simpleList.name,
            false,
            cards
        )
    }

    /**
     * Get list of cards in the list.
     *
     * @param lid list's unique identifier
     *
     * @throws IllegalListAccessException if user doesn't have access to the board specified by bid
     *
     * @return List of Card object
     */
    fun getCardsFromList(token: String, lid: Int, skip: Int? = null, limit: Int? = null): List<Card> {
        checkToken(token)
        val list = database.getListDetails(lid)
        if (!database.checkUserTokenExistsInBoard(token, list.bid)) throw IllegalListAccessException

        val cards = database.getCardsFromList(lid, list.bid)
        val droppedCards = if (skip != null) cards.drop(skip) else cards
        return if (limit != null) droppedCards.take(limit) else droppedCards
    }
}
