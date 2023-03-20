package pt.isel.ls.services.lists

import pt.isel.ls.database.AppDatabase
import pt.isel.ls.database.DataBoardLists
import pt.isel.ls.database.DataList

class ListServices(private val database: AppDatabase) {
    /**
     * Creates a new list on a board
     *
     * @param bid id of the board containing this list
     * @param name list's name
     *
     * @return list unique identifier
     */
    fun createList(bid: Int, name: String): Int = database.createList(bid, name).id

    /**
     * Get the lists of a board.
     *
     * @param bid board's unique identifier
     *
     * @return DataBoardLists object
     */
    fun getBoardsLists(bid: Int): DataBoardLists = database.getListsFromBoard(bid)

    /**
     * Get detailed information of a list.
     *
     * @param lid list's unique identifier
     *
     * @return BList object
     */
    fun getList(lid: Int): DataList = database.getListDetails(lid)
}