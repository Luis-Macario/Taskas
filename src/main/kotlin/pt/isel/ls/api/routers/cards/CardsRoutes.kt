package pt.isel.ls.api.routers.cards

import org.http4k.core.Request
import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.core.Response
import org.http4k.core.Status.Companion.NO_CONTENT
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.routes
import pt.isel.ls.services.cards.CardServices
import org.http4k.core.Status.Companion.CREATED
import org.http4k.core.Status.Companion.OK
import pt.isel.ls.api.dto.card.toDTO
import pt.isel.ls.api.dto.card.CreateCardRequest
import pt.isel.ls.api.dto.card.CreateCardResponse
import pt.isel.ls.api.dto.card.MoveCardRequest
import pt.isel.ls.api.dto.user.toDTO
import pt.isel.ls.api.routers.utils.exceptions.runAndHandleExceptions
import pt.isel.ls.api.routers.utils.getJsonBodyTo
import pt.isel.ls.api.routers.utils.getBearerToken
import pt.isel.ls.api.routers.utils.json
import pt.isel.ls.api.routers.utils.getCardID

/**
 * Represents the Cards portion of the routes available in the Web API
 *
 * @param services the card services
 * @property routes the card endpoints
 */
class CardsRoutes(private val services: CardServices) {
    val routes: RoutingHttpHandler = routes(
        "/" bind POST to ::createCard,
        "/{cardID}" bind GET to ::getCardDetails,
        "/{cardID}/move" bind POST to ::moveCard,
    )

    private fun createCard(request: Request): Response =
        runAndHandleExceptions {
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
            Response(CREATED)
                .header("Location", "/cards/${card.id}")
                .json(cardResponse)
        }

    private fun getCardDetails(request: Request): Response =
        runAndHandleExceptions {
            val cardID = request.getCardID()
            val bearerToken = request.getBearerToken()

            val card = services.getCardDetails(bearerToken, cardID)
            val cardResponse = card.toDTO()
            Response(OK).json(cardResponse)
        }

    private fun moveCard(request: Request): Response =
        runAndHandleExceptions {
            val cardID = request.getCardID()
            val bearerToken = request.getBearerToken()
            val moveCardRequest = request.getJsonBodyTo<MoveCardRequest>()

            services.moveCard(bearerToken, cardID, moveCardRequest)
            Response(NO_CONTENT)
        }
}
