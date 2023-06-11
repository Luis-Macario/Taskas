package pt.isel.ls.api.routers.utils

import kotlinx.serialization.SerializationException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.routing.path
import pt.isel.ls.api.routers.utils.exceptions.InvalidAuthHeaderException
import pt.isel.ls.api.routers.utils.exceptions.InvalidBoardIDException
import pt.isel.ls.api.routers.utils.exceptions.InvalidBodyException
import pt.isel.ls.api.routers.utils.exceptions.InvalidCardIDException
import pt.isel.ls.api.routers.utils.exceptions.InvalidListIDException
import pt.isel.ls.api.routers.utils.exceptions.InvalidQueryException
import pt.isel.ls.api.routers.utils.exceptions.InvalidUserIDException
import pt.isel.ls.api.routers.utils.exceptions.NoAuthenticationException

private const val BEARER_REGEX: String = "^Bearer .+\$"
private const val MINIMUM_SKIP = 0
private const val MINIMUM_LIMIT = 0
private const val MAXIMUM_LIMIT = 100

/**
 * Parses Bearer Token
 *
 * @return String without the "Bearer " part
 *
 * @throws InvalidAuthHeaderException if the token parameter isn't a valid Bearer Token
 */
fun String.parseBearerToken(): String {
    if (!this.matches(BEARER_REGEX.toRegex())) throw InvalidAuthHeaderException
    return this.substring(7)
}

/**
 * Attempts to get the string located in the Authentication Header of the [Request]
 *
 * @return the string located in the Authentication Header
 * @throws NoAuthenticationException if no Authentication Header is present
 */
fun Request.getAuthorizationHeader(): String =
    (header("Authorization") ?: throw NoAuthenticationException).parseBearerToken()

/**
 * Attempts to get the string skip and limit queries of the [Request]
 *
 * @return a pair of: the skip amount or null; the limit amount or null.
 * @throws InvalidQueryException if at least one of the queries is invalid
 */
fun Request.getPaging(): Pair<Int?, Int?> {
    val skipQuery = query("skip")
    val skip =
        if (skipQuery != null) {
            skipQuery.toIntOrNull()?.takeIf { it >= MINIMUM_SKIP } ?: throw InvalidQueryException
        } else {
            null
        }
    val limitQuery = query("limit")
    val limit =
        if (limitQuery != null) {
            limitQuery.toIntOrNull()?.takeIf { it in MINIMUM_LIMIT..MAXIMUM_LIMIT } ?: throw InvalidQueryException
        } else {
            null
        }
    return Pair(skip, limit)
}

/**
 * gets the userID from the [Request]
 *
 * @return the value located in the userID path
 * @throws InvalidUserIDException if no userID is provided or if it is of the wrong type
 */
fun Request.getUserID(): Int = path("userID")?.toIntOrNull() ?: throw InvalidUserIDException

/**
 * gets the boardID from the [Request]
 *
 * @return the value located in the boardID path
 * @throws InvalidUserIDException if no boardID is provided or if it is of the wrong type
 */
fun Request.getBoardID(): Int = path("boardID")?.toIntOrNull() ?: throw InvalidBoardIDException

/**
 * gets the listID from the [Request]
 *
 * @return the value located in the listID path
 * @throws InvalidUserIDException if no listID is provided or if it is of the wrong type
 */
fun Request.getListID(): Int = path("listID")?.toIntOrNull() ?: throw InvalidListIDException

/**
 * gets the cardID from the [Request]
 *
 * @return the value located in the cardID path
 * @throws InvalidUserIDException if no cardID is provided or if it is of the wrong type
 */
fun Request.getCardID(): Int = path("cardID")?.toIntOrNull() ?: throw InvalidCardIDException

inline fun <reified T> Request.getJsonBodyTo(): T =
    try {
        Json.decodeFromString(bodyString())
    } catch (e: SerializationException) {
        throw InvalidBodyException
    }

/**
 * Adds a "Content-Type" header to the [Response] with the value "application/json" and places the given [data] into the
 * response's body as JSON.
 *
 * @param data the data to put in the [Response] body
 * @return the updated [Response]
 * @throws InvalidBodyException if the data provided is invalid
 */
inline fun <reified T> Response.json(data: T): Response =
    try {
        this.header("Content-Type", "application/json")
            .body(Json.encodeToString(data))
    } catch (e: SerializationException) {
        throw InvalidBodyException
    } catch (e: IllegalArgumentException) {
        throw InvalidBodyException
    }
