package pt.isel.ls.api.routers

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.http4k.core.Response
import org.http4k.core.Status
import pt.isel.ls.api.dto.ErrorResponse
import pt.isel.ls.database.memory.EmailAreadyExists
import pt.isel.ls.database.memory.UserNotFound

fun errorHandler(e: Exception): Response =
    when (e) {
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

        else -> {
            Response(Status.INTERNAL_SERVER_ERROR)
                .header("Content-Type", "application/json")
                .body(
                    Json.encodeToString(
                        ErrorResponse(
                            code = 9000, // TODO
                            name = e.javaClass.simpleName,
                            description = e.message ?: "An unknown error has occurred."
                        )
                    )
                )
        }
    }
