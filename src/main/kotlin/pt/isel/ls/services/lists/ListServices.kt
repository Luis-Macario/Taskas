package pt.isel.ls.services.lists

import pt.isel.ls.database.AppDatabase
import pt.isel.ls.domain.Card
import pt.isel.ls.domain.TaskList
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
        database.getBoardDetails(bid)

        val users = database.getUsersFromBoard(bid)
        if (!users.any { it.token == token }) throw IllegalBoardAccessException

        val simpleList = database.createList(bid, name)
        val cards = database.getCardsFromList(simpleList.id)

        return TaskList(simpleList.id, bid, name, false, cards)
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
        val users = database.getUsersFromBoard(simpleList.bid)
        if (!users.any { it.token == token }) throw IllegalListAccessException

        val cards = database.getCardsFromList(simpleList.id)

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
    fun getCardsFromList(token: String, lid: Int): List<Card> {
        checkToken(token)
        val list = database.getListDetails(lid)
        val users = database.getUsersFromBoard(list.bid)
        if (!users.any { it.token == token }) throw IllegalListAccessException
        return database.getCardsFromList(lid)
    }
}
