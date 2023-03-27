package pt.isel.ls.api.routers.utils.exceptions

import org.http4k.core.Response
import org.http4k.core.Status
import pt.isel.ls.api.dto.ErrorResponse
import pt.isel.ls.api.routers.utils.json
import pt.isel.ls.database.memory.BoardNotFoundException
import pt.isel.ls.database.memory.CardNotFoundException
import pt.isel.ls.database.memory.EmailAlreadyExistsException
import pt.isel.ls.database.memory.ListNotFoundException
import pt.isel.ls.database.memory.UserNotFoundException
import pt.isel.ls.domain.TaskException
import pt.isel.ls.utils.exceptions.IllegalBoardAccessException
import pt.isel.ls.utils.exceptions.IllegalCardAccessException
import pt.isel.ls.utils.exceptions.IllegalListAccessException
import pt.isel.ls.utils.exceptions.IllegalUserAccessException
import pt.isel.ls.utils.exceptions.InvalidBearerToken

/**
 * Runs the given block, and if an exception is thrown, runs [exceptionHandler]
 *
 * @param block the block of code to execute
 * @return response from [block] or if an exception was thrown, the response from [exceptionHandler]
 */
fun runAndHandleExceptions(block: () -> Response): Response =
    try {
        block()
    } catch (e: Exception) {
        exceptionHandler(e)
    }

/**
 * Handles the exception thrown during a routing operation.
 * If [exception] is not a [TaskException] then the result will always be a [Response] with status [Status.INTERNAL_SERVER_ERROR]
 *
 * @param exception the [Exception]
 * @return the [Response] correspondent to the exception thrown
 */
fun exceptionHandler(exception: Exception): Response =
    if (exception is TaskException) {
        Response(exception.toStatus()).json(
            ErrorResponse(
                code = exception.code,
                name = exception.javaClass.simpleName,
                description = exception.description
            )
        )
    } else {
        Response(Status.INTERNAL_SERVER_ERROR).json(
            ErrorResponse(
                code = 9000, // TODO: Figure out what codes to use
                name = "Unknown Error: " + exception.javaClass.simpleName,
                description = "An unknown error has occurred: " + exception.message
            )
        )
    }

/**
 * Converts a [TaskException] to a [Status]
 *
 * @return the correspondent [Status]
 */
fun TaskException.toStatus() =
    when (this) {
        InvalidUserIDException, InvalidBoardIDException, InvalidListIDException, InvalidCardIDException, InvalidBodyException -> Status.BAD_REQUEST
        NoAuthenticationException, InvalidBearerToken -> Status.UNAUTHORIZED
        IllegalUserAccessException, IllegalBoardAccessException, IllegalListAccessException, IllegalCardAccessException -> Status.FORBIDDEN
        UserNotFoundException, BoardNotFoundException, ListNotFoundException, CardNotFoundException -> Status.NOT_FOUND
        EmailAlreadyExistsException -> Status.CONFLICT
        // -> Status.BAD_GATEWAY
        else -> Status.INTERNAL_SERVER_ERROR
    }
