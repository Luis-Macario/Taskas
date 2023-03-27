package pt.isel.ls.api

import org.http4k.routing.bind
import org.http4k.routing.routes
import pt.isel.ls.api.routers.boards.BoardsRoutes
import pt.isel.ls.api.routers.cards.CardsRoutes
import pt.isel.ls.api.routers.lists.ListsRoutes
import pt.isel.ls.api.routers.users.UsersRoutes
import pt.isel.ls.services.TasksServices

/**
 * Represents the Web API
 *
 * @param services the services
 * @property routes the endpoints
 */
class TasksWebApi(services: TasksServices) {
    private val usersRoutes = UsersRoutes(services.users).routes
    private val boardsRoutes = BoardsRoutes(services.boards).routes
    private val listsRoutes = ListsRoutes(services.lists).routes
    private val cardsRoutes = CardsRoutes(services.cards).routes

    val routes = routes(
        "/users" bind usersRoutes,
        "/boards" bind boardsRoutes,
        "/lists" bind listsRoutes,
        "/cards" bind cardsRoutes
    )
}
