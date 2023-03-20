package pt.isel.ls.api

import org.http4k.routing.bind
import org.http4k.routing.routes
import pt.isel.ls.api.routers.users.UsersRoutes
import pt.isel.ls.services.TaskServices

class TasksWebApi(services: TaskServices) {
    private val usersRoutes = UsersRoutes(services).routes
    val routes = routes(
        "/users" bind usersRoutes
    )
}