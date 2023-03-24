package pt.isel.ls.api.routers.lists

import org.http4k.core.HttpHandler
import org.http4k.core.Method
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.routes
import pt.isel.ls.services.lists.ListServices

/**
 * Represents the Lists portion of the routes available in the Web API
 *
 * @param services the list services
 * @property routes the list endpoints
 */
class ListsRoutes(private val services: ListServices) {
    val routes: RoutingHttpHandler = routes(TODO() as Pair<Method, HttpHandler>)
    /*
        "/" bind POST to ::createList
        "/{listID}" bind GET to ::getListDetails,
        "/{listID}/cards" bind GET to ::getCardsFromList
    )

    private fun createList(request: Request): Response =
        runAndHandleExceptions {
            val listRequest = request.getJsonBodyTo<CreateListRequest>()
            val bearerToken = request.getBearerToken()

            val list = services.createList(/*bearerToken,*/listRequest.boardID, listRequest.name)
            val listResponse = CreateListResponse(list.id)
            Response(CREATED)
                .header("Location", "/lists/${list.id}")
                .json(listResponse)
        }
    private fun getCardsFromList(request: Request): Response =
        runAndHandleExceptions {
            val listID = request.getListID()
            val bearerToken = request.getBearerToken()

            val cards: List<Card> = services.getCardsFromList(bearerToken, listID)
            val cardsResponse = GetCardFromListResponse(cards.map { it.toDTO() })
            Response(OK).json(cardsResponse)
        }
     */
}
