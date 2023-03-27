package pt.isel.ls.api.routers.lists

import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.CREATED
import org.http4k.core.Status.Companion.OK
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.routes
import pt.isel.ls.api.dto.card.toDTO
import pt.isel.ls.api.dto.list.CreateListRequest
import pt.isel.ls.api.dto.list.CreateListResponse
import pt.isel.ls.api.dto.list.GetCardFromListResponse
import pt.isel.ls.api.dto.list.toDTO
import pt.isel.ls.api.routers.utils.exceptions.runAndHandleExceptions
import pt.isel.ls.api.routers.utils.getBearerToken
import pt.isel.ls.api.routers.utils.getJsonBodyTo
import pt.isel.ls.api.routers.utils.getListID
import pt.isel.ls.api.routers.utils.json
import pt.isel.ls.services.lists.ListServices

/**
 * Represents the Lists portion of the routes available in the Web API
 *
 * @param services the list services
 * @property routes the list endpoints
 */
class ListsRoutes(private val services: ListServices) {
    val routes: RoutingHttpHandler = routes(
        "/" bind POST to ::createList,
        "/{listID}" bind GET to ::getListDetails,
        "/{listID}/cards" bind GET to ::getCardsFromList
    )

    private fun createList(request: Request): Response =
        runAndHandleExceptions {
            val listRequest = request.getJsonBodyTo<CreateListRequest>()
            val bearerToken = request.getBearerToken()

            val list = services.createList(
                bearerToken,
                listRequest.boardID,
                listRequest.name
            )

            val listResponse = CreateListResponse(list.id)
            Response(CREATED)
                .header("Location", "/lists/${list.id}")
                .json(listResponse)
        }

    private fun getCardsFromList(request: Request): Response =
        runAndHandleExceptions {
            val listID = request.getListID()
            val bearerToken = request.getBearerToken()

            val cards = services.getCardsFromList(bearerToken, listID)
            val cardsResponse = GetCardFromListResponse(cards.map { it.toDTO() })
            Response(OK).json(cardsResponse)
        }

    private fun getListDetails(request: Request): Response =
        runAndHandleExceptions {
            val listID = request.getListID()
            val bearerToken = request.getBearerToken()

            val list = services.getList(bearerToken, listID)
            val listResponse = list.toDTO()
            Response(OK).json(listResponse)
        }
}
