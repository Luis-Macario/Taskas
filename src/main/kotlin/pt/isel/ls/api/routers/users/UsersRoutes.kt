package pt.isel.ls.api.routers.users

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.BAD_REQUEST
import org.http4k.core.Status.Companion.CONFLICT
import org.http4k.core.Status.Companion.CREATED
import org.http4k.core.Status.Companion.INTERNAL_SERVER_ERROR
import org.http4k.core.Status.Companion.NOT_FOUND
import org.http4k.core.Status.Companion.OK
import org.http4k.routing.bind
import org.http4k.routing.path
import org.http4k.routing.routes
import pt.isel.ls.api.routers.ErrorResponse
import pt.isel.ls.database.memory.EmailAreadyExists
import pt.isel.ls.database.memory.UserNotFound
import pt.isel.ls.services.TaskServices

class UsersRoutes(private val services: TaskServices) {
    val routes = routes(
        "/" bind POST to ::createUser,
        "/{id}" bind GET to ::getUserDetails
    )

    private fun createUser(request: Request): Response {
        val userRequest = Json.decodeFromString<CreateUserRequest>(request.bodyString())
        return try {
            val userResponse = services.users.createUser(userRequest.name, userRequest.email)

            Response(CREATED)
                .header("Content-Type", "application/json")
                .body(Json.encodeToString(userResponse))
        } catch (e: Exception) {
            when (e) {
                is EmailAreadyExists -> Response(CONFLICT)
                    .header("Content-Type", "application/json")
                    .body(
                        Json.encodeToString(
                            ErrorResponse(
                                e.code,
                                e.javaClass.simpleName,
                                e.description
                            )
                        )
                    )

                else -> {
                    Response(INTERNAL_SERVER_ERROR)
                        .header("Content-Type", "application/json")
                        .body(
                            Json.encodeToString(
                                ErrorResponse(
                                    code = 9000, //TODO
                                    name = e.javaClass.simpleName,
                                    description = e.message ?: "An unknown error has occurred."
                                )
                            )
                        )
                }
            }
        }
    }

    private fun getUserDetails(request: Request): Response {
        val uid = request.path("id")?.toInt() ?: return Response(BAD_REQUEST)
            .header("Content-Type", "application/json")
            .body(
                Json.encodeToString(
                    ErrorResponse(
                        code = 1, //TODO
                        name = "Invalid User ID",
                        description = "Invalid ID"
                    )
                )
            )
        return try {
            val user = services.users.getUser(uid)
            Response(OK)
                .header("Content-Type", "application/json")
                .body(Json.encodeToString(user))
        } catch (e: Exception) {
            when (e) {
                is UserNotFound -> Response(NOT_FOUND)
                    .header("Content-Type", "application/json")
                    .body(
                        Json.encodeToString(
                            ErrorResponse(
                                e.code,
                                e.javaClass.simpleName,
                                e.description
                            )
                        )
                    )

                else -> {
                    Response(INTERNAL_SERVER_ERROR)
                        .header("Content-Type", "application/json")
                        .body(
                            Json.encodeToString(
                                ErrorResponse(
                                    code = 9000, //TODO
                                    name = e.javaClass.simpleName,
                                    description = e.message ?: "An unknown error has occurred."
                                )
                            )
                        )
                }
            }
        }
    }
}