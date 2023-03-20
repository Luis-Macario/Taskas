package pt.isel.ls.api.routers.boards

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.routing.bind
import org.http4k.routing.routes
import pt.isel.ls.api.dto.board.CreateBoardRequest
import pt.isel.ls.api.dto.board.CreateBoardResponse
import pt.isel.ls.api.routers.errorHandler
import pt.isel.ls.services.boards.BoardServices

class BoardsRoutes(private val services: BoardServices) {
    /*
    val routes = routes(
        "/" bind Method.POST to ::createBoard
    )

    private fun createBoard(request: Request): Response {
        val boardRequest = Json.decodeFromString<CreateBoardRequest>(request.bodyString())
        return try {
            val board = services.createBoard(boardRequest.name, boardRequest.description)
            val boardResponse = CreateBoardResponse(board.id)
            Response(Status.CREATED)
                .header("Content-Type", "application/json")
                .body(Json.encodeToString(boardResponse))
        } catch (e: Exception) {
            errorHandler(e)
        }
    }
    */
}
