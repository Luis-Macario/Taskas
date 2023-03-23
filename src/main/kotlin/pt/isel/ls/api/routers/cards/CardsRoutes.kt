package pt.isel.ls.api.routers.cards

import org.http4k.core.Method.POST
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.routing.bind
import org.http4k.routing.routes
import pt.isel.ls.api.dto.card.CreateCardRequest
import pt.isel.ls.api.dto.card.CreateCardResponse
import pt.isel.ls.api.routers.exceptions.runAndHandleExceptions
import pt.isel.ls.api.routers.utils.getBearerToken
import pt.isel.ls.api.routers.utils.getJsonBodyTo
import pt.isel.ls.api.routers.utils.json
import pt.isel.ls.services.cards.CardServices

/**
 * Represents the Cards portion of the routes available in the Web API
 *
 * @param services the card services
 * @property routes the card endpoints
 */
class CardsRoutes(private val services: CardServices) {
    val routes = routes(
        "/" bind POST to ::createCard
        // "/{cardID}" bind GET to ::getCardDetails,
        // "/{cardID}/move" bind POST to ::moveCard,
    )

    private fun createCard(request: Request): Response {
        return runAndHandleExceptions {
            val cardRequest = request.getJsonBodyTo<CreateCardRequest>()
            val bearerToken = request.getBearerToken()

            val card =
                services.createCard(
                    // bearerToken,
                    cardRequest.listID,
                    cardRequest.name,
                    cardRequest.description,
                    cardRequest.dueDate
                )
            val cardResponse = CreateCardResponse(card.id)
            Response(Status.CREATED)
                .header("Location", "/cards/${card.id}")
                .json(cardResponse)
        }
    }
}
