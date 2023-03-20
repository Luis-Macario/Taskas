package pt.isel.ls.services

import pt.isel.ls.database.AppDatabase
import pt.isel.ls.services.boards.BoardServices
import pt.isel.ls.services.cards.CardServices
import pt.isel.ls.services.lists.ListServices
import pt.isel.ls.services.users.UserServices

/**
 * Services aggregator
 */
class TaskServices (db: AppDatabase){
    val users: UserServices = UserServices(db)
    val boards: BoardServices = BoardServices(db)
    val lists: ListServices = ListServices(db)
    val cards: CardServices = CardServices(db)
}