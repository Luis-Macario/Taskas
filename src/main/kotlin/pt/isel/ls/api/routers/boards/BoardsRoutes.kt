package pt.isel.ls.api.routers.boards

import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.CREATED
import org.http4k.core.Status.Companion.NO_CONTENT
import org.http4k.core.Status.Companion.OK
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.routes
import pt.isel.ls.api.dto.board.AddUserRequest
import pt.isel.ls.api.dto.board.CreateBoardRequest
import pt.isel.ls.api.dto.board.CreateBoardResponse
import pt.isel.ls.api.dto.board.GetListsFromBoardResponse
import pt.isel.ls.api.dto.board.GetUsersFromBoardResponse
import pt.isel.ls.api.dto.board.toDTO
import pt.isel.ls.api.dto.list.toDTO
import pt.isel.ls.api.dto.user.toDTO
import pt.isel.ls.api.routers.utils.exceptions.runAndHandleExceptions
import pt.isel.ls.api.routers.utils.getAuthorizationHeader
import pt.isel.ls.api.routers.utils.getBoardID
import pt.isel.ls.api.routers.utils.getJsonBodyTo
import pt.isel.ls.api.routers.utils.getPagging
import pt.isel.ls.api.routers.utils.json
import pt.isel.ls.services.boards.BoardServices

/**
 * Represents the Boards portion of the routes available in the Web API
 *
 * @param services the board services
 * @property routes the board endpoints
 */
class BoardsRoutes(private val services: BoardServices) {

    val routes: RoutingHttpHandler = routes(

        "/" bind POST to ::createBoard,
        "/{boardID}" bind GET to ::getBoardDetails,
        "/{boardID}/users" bind GET to ::getUsersFromBoard,
        "/{boardID}/users" bind POST to ::addUserToBoard,
        "/{boardID}/lists" bind GET to ::getListsFromBoard
    )

    /**
     * Creates a new board
     *
     * @param request The request information
     * @return the corresponding [Response]
     */
    private fun createBoard(request: Request): Response =
        runAndHandleExceptions {
            val boardRequest = request.getJsonBodyTo<CreateBoardRequest>()
            val bearerToken = request.getAuthorizationHeader()

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

    /**
     * Gets the details of a board
     *
     * @param request The request information
     * @return the corresponding [Response]
     */
    fun getBoardDetails(request: Request): Response =
        runAndHandleExceptions {
            val boardID = request.getBoardID()
            val bearerToken = request.getAuthorizationHeader()

            val board = services.getBoardDetails(bearerToken, boardID)
            val getBoardsDetails = board.toDTO()

            Response(OK).json(getBoardsDetails)
        }

    /**
     * Gets the list of users in a board
     *
     * @param request The request information
     * @return the corresponding [Response]
     */
    private fun getUsersFromBoard(request: Request): Response =
        runAndHandleExceptions {
            val boardID = request.getBoardID()
            val bearerToken = request.getAuthorizationHeader()
            val (skip, limit) = request.getPagging()

            val users = services.getUsersFromBoard(bearerToken, boardID)
                .drop(skip)
                .take(limit)

            val getUsersResponse = GetUsersFromBoardResponse(users.map { it.toDTO() })

            Response(OK).json(getUsersResponse)
        }

    /**
     * Adds a user to a board
     *
     * @param request The request information
     * @return the corresponding [Response]
     */
    private fun addUserToBoard(request: Request): Response =
        runAndHandleExceptions {
            val boardID = request.getBoardID()
            val bearerToken = request.getAuthorizationHeader()
            val addUserRequest = request.getJsonBodyTo<AddUserRequest>()

            services.addUserToBoard(bearerToken, addUserRequest.userID, boardID)

            Response(NO_CONTENT)
        }

    /**
     * Gets the list of Lists in a Board
     *
     * @param request The request information
     * @return the corresponding [Response]
     */
    private fun getListsFromBoard(request: Request): Response =
        runAndHandleExceptions {
            val boardID = request.getBoardID()
            val bearerToken = request.getAuthorizationHeader()
            val (skip, limit) = request.getPagging()

            val lists = services.getListsFromBoard(bearerToken, boardID)
                .drop(skip)
                .take(limit)

            val getListsResponse = GetListsFromBoardResponse(lists.map { it.toDTO() })
            Response(OK).json(getListsResponse)
        }
}
