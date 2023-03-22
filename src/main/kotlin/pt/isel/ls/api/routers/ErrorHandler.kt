package pt.isel.ls.api.routers

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.http4k.core.Response
import org.http4k.core.Status
import pt.isel.ls.api.InvalidBoardID
import pt.isel.ls.api.InvalidCardID
import pt.isel.ls.api.InvalidListID
import pt.isel.ls.api.InvalidUserID
import pt.isel.ls.api.dto.ErrorResponse
import pt.isel.ls.database.memory.EmailAreadyExists
import pt.isel.ls.database.memory.UserNotFound
import pt.isel.ls.domain.TaskError

fun errorHandler(e: Exception): Response =
    if (e is TaskError) {
        when (e) {
            is InvalidUserID, InvalidBoardID, InvalidListID, InvalidCardID -> Response(Status.BAD_REQUEST)
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

            is UserNotFound -> Response(Status.NOT_FOUND)
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

            is EmailAreadyExists -> Response(Status.CONFLICT)
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

            else -> Response(Status.INTERNAL_SERVER_ERROR)
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
        }
    } else {
        Response(Status.INTERNAL_SERVER_ERROR)
            .header("Content-Type", "application/json")
            .body(
                Json.encodeToString(
                    ErrorResponse(
                        code = 9000, // TODO: Figure out what codes to use
                        name = "Unknown Error: " + e.javaClass.simpleName,
                        description = "An unknown error has occurred: " + e.message
                    )
                )
            )
    }
