package pt.isel.ls.api.routers.boards

import org.http4k.core.HttpHandler
import org.http4k.core.Method
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.routes
import pt.isel.ls.services.boards.BoardServices

/**
 * Represents the Boards portion of the routes available in the Web API
 *
 * @param services the board services
 * @property routes the board endpoints
 */
class BoardsRoutes(private val services: BoardServices) {

    val routes: RoutingHttpHandler = routes(TODO() as Pair<Method, HttpHandler>)
    /*
        "/" bind POST to ::createBoard
        "/{boardID}" bind GET to ::getBoardDetails,
        "/{boardID}/users" bind GET to ::getUsersFromBoard,
        "/{boardID}/users" bind POST to ::addUserToBoard,
        "/{boardID}/lists" bind GET to ::getListsFromBoard,
    )

    private fun createBoard(request: Request): Response =
        runAndHandleExceptions {
            val boardRequest = request.getJsonBodyTo<CreateBoardRequest>()
            val bearerToken = request.getBearerToken()

            val board = services.createBoard(
                bearerToken,
                boardRequest.name,
                boardRequest.description
            )
            val boardResponse = CreateBoardResponse(board.id)
            Response(CREATED)
                .header("Location", "/boards/${board.id}")
                .json(boardResponse)
        }

    private fun getUsersFromBoard(request: Request): Response =
        runAndHandleExceptions {
            val boardID = request.getBoardID()
            val bearerToken = request.getBearerToken()

            val users = services.getUsersFromBoard(bearerToken, boardID)
            val getUsersResponse = GetUsersFromBoardResponse(users.map { it.toDTO() })

            Response(OK).json(getUsersResponse)
        }

    private fun addUserToBoard(request: Request): Response =
        runAndHandleExceptions {
            val boardID = request.getBoardID()
            val bearerToken = request.getBearerToken()
            val addUserRequest = request.getJsonBodyTo<AddUserRequest>()

            services.addUserToBoard(bearerToken, boardID, addUserRequest.userID)

            Response(NO_CONTENT)
        }

    private fun getListsFromBoard(request: Request): Response =
        runAndHandleExceptions {
            val boardID = request.getBoardID()
            val bearerToken = request.getBearerToken()

            val lists = services.getListsFromBoard(bearerToken, boardID)
            val getListsResponse = GetListsFromBoardResponse(lists.map { it.toDTO() })

            Response(OK).json(getListsResponse)
        }
    */
}
