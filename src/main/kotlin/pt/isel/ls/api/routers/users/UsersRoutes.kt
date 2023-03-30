package pt.isel.ls.api.routers.users

import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.CREATED
import org.http4k.core.Status.Companion.OK
import org.http4k.routing.bind
import org.http4k.routing.routes
import pt.isel.ls.api.dto.board.toDTO
import pt.isel.ls.api.dto.user.CreateUserRequest
import pt.isel.ls.api.dto.user.CreateUserResponse
import pt.isel.ls.api.dto.user.GetBoardsFromUserResponse
import pt.isel.ls.api.dto.user.toDTO
import pt.isel.ls.api.routers.utils.exceptions.runAndHandleExceptions
import pt.isel.ls.api.routers.utils.getAuthorizationHeader
import pt.isel.ls.api.routers.utils.getJsonBodyTo
import pt.isel.ls.api.routers.utils.getUserID
import pt.isel.ls.api.routers.utils.json
import pt.isel.ls.services.users.UserServices

/**
 * Represents the Users portion of the routes available in the Web API
 *
 * @param services the user services
 * @property routes the user endpoints
 */
class UsersRoutes(private val services: UserServices) {
    val routes = routes(
        "/" bind POST to ::createUser,
        "/{userID}" bind GET to ::getUserDetails,
        "/{userID}/boards" bind GET to ::getBoardsFromUser
    )

    private fun createUser(request: Request): Response =
        runAndHandleExceptions {
            val userRequest = request.getJsonBodyTo<CreateUserRequest>()

            val user = services.createUser(userRequest.name, userRequest.email)
            val userResponse = CreateUserResponse(user.id, user.token)

            Response(CREATED)
                .header("Location", "/users/${user.id}")
                .json(userResponse)
        }

    private fun getUserDetails(request: Request): Response =
        runAndHandleExceptions {
            val uid = request.getUserID()

            val user = services.getUser(uid)
            val userResponse = user.toDTO()

            Response(OK).json(userResponse)
        }

    private fun getBoardsFromUser(request: Request): Response =
        runAndHandleExceptions {
            val uid = request.getUserID()
            val bearerToken = request.getAuthorizationHeader()

            val boards = services.getBoardsFromUser(bearerToken, uid)
            val boardsResponse = GetBoardsFromUserResponse(boards.map { it.toDTO() })

            Response(OK).json(boardsResponse)
        }
}
