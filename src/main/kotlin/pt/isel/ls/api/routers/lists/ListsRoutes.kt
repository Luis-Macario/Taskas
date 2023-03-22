package pt.isel.ls.api.routers.lists

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.routing.bind
import org.http4k.routing.routes
import pt.isel.ls.api.dto.list.CreateListRequest
import pt.isel.ls.api.dto.list.CreateListResponse
import pt.isel.ls.api.routers.errorHandler
import pt.isel.ls.services.lists.ListServices

class ListsRoutes(private val services: ListServices) {
    val routes = routes(
        "/" bind Method.POST to ::createList
    )

    private fun createList(request: Request): Response {
        return try {
            val listRequest = Json.decodeFromString<CreateListRequest>(request.bodyString())
            val bearerToken = request.header("Authentication") ?: return Response(Status.UNAUTHORIZED)

            val list = services.createList(/*bearerToken,*/listRequest.boardID, listRequest.name)
            val listResponse = CreateListResponse(list.id)
            Response(Status.CREATED)
                .header("Content-Type", "application/json")
                .body(Json.encodeToString(listResponse))
        } catch (e: Exception) {
            errorHandler(e)
        }
    }
}
