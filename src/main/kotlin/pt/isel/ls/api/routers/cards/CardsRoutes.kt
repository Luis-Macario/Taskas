package pt.isel.ls.api.routers.cards

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.routing.bind
import org.http4k.routing.routes
import pt.isel.ls.api.dto.card.CreateCardRequest
import pt.isel.ls.api.dto.card.CreateCardResponse
import pt.isel.ls.api.routers.errorHandler
import pt.isel.ls.services.cards.CardServices

class CardsRoutes(private val services: CardServices) {
    val routes = routes(
        "/" bind Method.POST to ::createCard
    )

    private fun createCard(request: Request): Response {
        return try {
            val cardRequest = Json.decodeFromString<CreateCardRequest>(request.bodyString())
            val bearerToken = request.header("Authentication") ?: return Response(Status.UNAUTHORIZED)

            val card =
                services.createCard(/*bearerToken,*/cardRequest.listID, cardRequest.name, cardRequest.description, cardRequest.dueDate)
            val cardResponse = CreateCardResponse(card.id)
            Response(Status.CREATED)
                .header("Content-Type", "application/json")
                .body(Json.encodeToString(cardResponse))
        } catch (e: Exception) {
            errorHandler(e)
        }
    }
}
