package pt.isel.ls.api.routers.users

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.BAD_REQUEST
import org.http4k.core.Status.Companion.CREATED
import org.http4k.core.Status.Companion.OK
import org.http4k.routing.bind
import org.http4k.routing.path
import org.http4k.routing.routes
import pt.isel.ls.api.dto.ErrorResponse
import pt.isel.ls.api.dto.user.CreateUserRequest
import pt.isel.ls.api.dto.user.CreateUserResponse
import pt.isel.ls.api.routers.errorHandler
import pt.isel.ls.services.users.UserServices

class UsersRoutes(private val services: UserServices) {
    val routes = routes(
        "/" bind POST to ::createUser,
        "/{id}" bind GET to ::getUserDetails
    )

    private fun createUser(request: Request): Response {
        return try {
            val userRequest = Json.decodeFromString<CreateUserRequest>(request.bodyString())
            val user = services.createUser(userRequest.name, userRequest.email)
            val userResponse = CreateUserResponse(user.id, user.token)

            Response(CREATED)
                .header("Content-Type", "application/json")
                .body(Json.encodeToString(userResponse))
        } catch (e: Exception) {
            return errorHandler(e)
        }
    }

    private fun getUserDetails(request: Request): Response {
        val uid = request.path("id")?.toInt() ?: return Response(BAD_REQUEST)
            .header("Content-Type", "application/json")
            .body(
                Json.encodeToString(
                    ErrorResponse(
                        code = 1, // TODO
                        name = "Invalid User ID",
                        description = "Invalid ID"
                    )
                )
            )
        return try {
            val user = services.getUser(uid)
            Response(OK)
                .header("Content-Type", "application/json")
                .body(Json.encodeToString(user))
        } catch (e: Exception) {
            errorHandler(e)
        }
    }
}
