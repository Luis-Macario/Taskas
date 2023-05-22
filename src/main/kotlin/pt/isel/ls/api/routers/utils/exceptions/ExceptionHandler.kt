package pt.isel.ls.api.routers.utils.exceptions

import org.http4k.core.Response
import org.http4k.core.Status
import pt.isel.ls.api.dto.ErrorResponse
import pt.isel.ls.api.routers.utils.json
import pt.isel.ls.database.memory.*
import pt.isel.ls.domain.TaskException
import pt.isel.ls.services.utils.exceptions.*

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
        is MemoryException ->
            when (this) {
                UserNotFoundException -> Status.NOT_FOUND
                BoardNotFoundException -> Status.NOT_FOUND
                ListNotFoundException -> Status.NOT_FOUND
                CardNotFoundException -> Status.NOT_FOUND
                EmailAlreadyExistsException -> Status.CONFLICT
                BoardNameAlreadyExistsException -> Status.CONFLICT
                UserAlreadyExistsInBoardException -> Status.CONFLICT
                TaskListAlreadyExistsInBoardException -> Status.CONFLICT
                CardNameAlreadyExistsException -> Status.CONFLICT
                BoardsUserDoesNotExistException -> Status.INTERNAL_SERVER_ERROR
                UsersBoardDoesNotExistException -> Status.INTERNAL_SERVER_ERROR
            }

        is ServicesException ->
            when (this) {
                InvalidTokenException -> Status.BAD_REQUEST
                IllegalBoardAccessException -> Status.FORBIDDEN
                IllegalCardAccessException -> Status.FORBIDDEN
                IllegalListAccessException -> Status.FORBIDDEN
                IllegalUserAccessException -> Status.FORBIDDEN
                IllegalMoveCardRequestException -> Status.UNPROCESSABLE_ENTITY
                NoSuchBoardException -> Status.UNPROCESSABLE_ENTITY
                InvalidUserCredentialsException -> Status.BAD_REQUEST            }

        is ApiException ->
            when (this) {
                InvalidAuthHeaderException -> Status.BAD_REQUEST
                InvalidBoardIDException -> Status.BAD_REQUEST
                InvalidCardIDException -> Status.BAD_REQUEST
                InvalidListIDException -> Status.BAD_REQUEST
                InvalidUserIDException -> Status.BAD_REQUEST
                InvalidBodyException -> Status.BAD_REQUEST
                NoAuthenticationException -> Status.UNAUTHORIZED
            }

        else -> Status.INTERNAL_SERVER_ERROR
    }
