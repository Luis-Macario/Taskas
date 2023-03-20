package pt.isel.ls

import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Http4kServer
import org.http4k.server.Jetty
import org.http4k.server.asServer
import pt.isel.ls.api.TasksWebApi
import pt.isel.ls.database.AppDatabase
import pt.isel.ls.services.TaskServices

class TasksServer(port: Int, database: AppDatabase) {
    private val server: Http4kServer

    init {
        val services = TaskServices(database)
        val webAPI = TasksWebApi(services)

        val app = routes(
            "/api" bind webAPI.routes
        )

        server = app.asServer(Jetty(port))
    }

    fun start() {
        server.start()
    }

    fun stop() {
        server.stop()
    }
}