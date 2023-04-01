package pt.isel.ls.integration.api

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Status
import pt.isel.ls.api.TasksWebApi
import pt.isel.ls.api.dto.ErrorResponse
import pt.isel.ls.api.dto.list.CreateListRequest
import pt.isel.ls.api.dto.list.CreateListResponse
import pt.isel.ls.api.dto.list.GetCardFromListResponse
import pt.isel.ls.api.dto.list.ListDTO
import pt.isel.ls.api.dto.list.toDTO
import pt.isel.ls.api.routers.utils.exceptions.InvalidAuthHeaderException
import pt.isel.ls.api.routers.utils.exceptions.InvalidBodyException
import pt.isel.ls.api.routers.utils.exceptions.InvalidListIDException
import pt.isel.ls.api.routers.utils.exceptions.NoAuthenticationException
import pt.isel.ls.database.memory.BoardNotFoundException
import pt.isel.ls.database.memory.ListNotFoundException
import pt.isel.ls.database.memory.TaskListAlreadyExistsInBoardException
import pt.isel.ls.database.memory.TasksDataMem
import pt.isel.ls.domain.User
import pt.isel.ls.services.TasksServices
import pt.isel.ls.services.utils.exceptions.IllegalBoardAccessException
import pt.isel.ls.services.utils.exceptions.IllegalListAccessException
import pt.isel.ls.services.utils.exceptions.InvalidTokenException
import kotlin.test.Test
import kotlin.test.assertEquals

class ListsTests {
    private val database = TasksDataMem()
    private val services = TasksServices(database)
    private val app = TasksWebApi(services).routes
    private val tokenA = "7d444840-9dc0-11d1-b245-5ffdce74fad2"
    private val authHeaderA = "Bearer $tokenA"
    private val tokenB = "7d444840-9dc0-11d1-b245-5ffdce74fad1"
    private val authHeaderB = "Bearer $tokenB"

    private val userA: User = database.createUser(tokenA, "Ricardo", "A47673@alunos.isel.pt")
    private val userB: User = database.createUser(tokenB, "Luis", "A47671@alunos.isel.pt")
    private val board = database.createBoard(userA.id, "aName", "aDescription")
    private val list = database.createList(board.id, "aList")

    @Test
    fun `POST to lists returns a 201 response with the correct response`() {
        val boardID = board.id
        val name = "anotherList"
        val requestBody = Json.encodeToString(CreateListRequest(boardID, name))
        val response = app(
            Request(Method.POST, "http://localhost:8080/lists").body(requestBody)
                .header("Authorization", authHeaderA)
        )
        assertEquals(Status.CREATED, response.status)
        assertEquals("application/json", response.header("content-type"))
        assertEquals("/lists/1", response.header("Location"))
        val listResponse = Json.decodeFromString<CreateListResponse>(response.bodyString())
        assertEquals(1, listResponse.id)
    }

    @Test
    fun `POST to lists returns a 400 response if invalid body`() {
        val response = app(
            Request(Method.POST, "http://localhost:8080/lists").body("ola")
                .header("Authorization", authHeaderA)
        )

        assertEquals(Status.BAD_REQUEST, response.status)
        assertEquals("application/json", response.header("content-type"))
        val errorResponse = Json.decodeFromString<ErrorResponse>(response.bodyString())
        assertEquals(
            ErrorResponse(
                code = InvalidBodyException.code,
                name = InvalidBodyException.javaClass.simpleName,
                description = InvalidBodyException.description
            ),
            errorResponse
        )
    }

    @Test
    fun `POST to lists returns a 400 response if invalid auth header`() {
        val boardID = board.id
        val name = "anotherList"
        val requestBody = Json.encodeToString(CreateListRequest(boardID, name))
        val response = app(
            Request(Method.POST, "http://localhost:8080/lists").body(requestBody)
                .header("Authorization", "ola")
        )

        assertEquals(Status.BAD_REQUEST, response.status)
        assertEquals("application/json", response.header("content-type"))
        val errorResponse = Json.decodeFromString<ErrorResponse>(response.bodyString())
        assertEquals(
            ErrorResponse(
                code = InvalidAuthHeaderException.code,
                name = InvalidAuthHeaderException.javaClass.simpleName,
                description = InvalidAuthHeaderException.description
            ),
            errorResponse
        )
    }

    @Test
    fun `POST to lists returns a 400 response if invalid token`() {
        val boardID = board.id
        val name = "anotherList"
        val requestBody = Json.encodeToString(CreateListRequest(boardID, name))
        val response = app(
            Request(Method.POST, "http://localhost:8080/lists").body(requestBody)
                .header("Authorization", "Bearer ola")
        )

        assertEquals(Status.BAD_REQUEST, response.status)
        assertEquals("application/json", response.header("content-type"))
        val errorResponse = Json.decodeFromString<ErrorResponse>(response.bodyString())
        assertEquals(
            ErrorResponse(
                code = InvalidTokenException.code,
                name = InvalidTokenException.javaClass.simpleName,
                description = InvalidTokenException.description
            ),
            errorResponse
        )
    }

    @Test
    fun `POST to lists returns a 401 response if no auth header`() {
        val boardID = board.id
        val name = "anotherList"
        val requestBody = Json.encodeToString(CreateListRequest(boardID, name))
        val response = app(
            Request(Method.POST, "http://localhost:8080/lists").body(requestBody)
        )

        assertEquals(Status.UNAUTHORIZED, response.status)
        assertEquals("application/json", response.header("content-type"))
        val errorResponse = Json.decodeFromString<ErrorResponse>(response.bodyString())
        assertEquals(
            ErrorResponse(
                code = NoAuthenticationException.code,
                name = NoAuthenticationException.javaClass.simpleName,
                description = NoAuthenticationException.description
            ),
            errorResponse
        )
    }

    @Test
    fun `POST to lists returns a 403 response if user doesn't have access to that board`() {
        val boardID = board.id
        val name = "anotherList"
        val requestBody = Json.encodeToString(CreateListRequest(boardID, name))
        val response = app(
            Request(Method.POST, "http://localhost:8080/lists").body(requestBody)
                .header("Authorization", authHeaderB)
        )

        assertEquals(Status.FORBIDDEN, response.status)
        assertEquals("application/json", response.header("content-type"))
        val errorResponse = Json.decodeFromString<ErrorResponse>(response.bodyString())
        assertEquals(
            ErrorResponse(
                code = IllegalBoardAccessException.code,
                name = IllegalBoardAccessException.javaClass.simpleName,
                description = IllegalBoardAccessException.description
            ),
            errorResponse
        )
    }

    @Test
    fun `POST to lists returns a 404 response if board not found`() {
        val boardID = 99
        val name = "anotherList"
        val requestBody = Json.encodeToString(CreateListRequest(boardID, name))
        val response = app(
            Request(Method.POST, "http://localhost:8080/lists").body(requestBody)
                .header("Authorization", authHeaderA)
        )

        assertEquals(Status.NOT_FOUND, response.status)
        assertEquals("application/json", response.header("content-type"))
        val errorResponse = Json.decodeFromString<ErrorResponse>(response.bodyString())
        assertEquals(
            ErrorResponse(
                code = BoardNotFoundException.code,
                name = BoardNotFoundException.javaClass.simpleName,
                description = BoardNotFoundException.description
            ),
            errorResponse
        )
    }

    @Test
    fun `POST to lists returns a 409 response if list with that name already exists`() {
        val boardID = board.id
        val name = list.name
        val requestBody = Json.encodeToString(CreateListRequest(boardID, name))
        val response = app(
            Request(Method.POST, "http://localhost:8080/lists").body(requestBody)
                .header("Authorization", authHeaderA)
        )

        assertEquals(Status.CONFLICT, response.status)
        assertEquals("application/json", response.header("content-type"))
        val errorResponse = Json.decodeFromString<ErrorResponse>(response.bodyString())
        assertEquals(
            ErrorResponse(
                code = TaskListAlreadyExistsInBoardException.code,
                name = TaskListAlreadyExistsInBoardException.javaClass.simpleName,
                description = TaskListAlreadyExistsInBoardException.description
            ),
            errorResponse
        )
    }

    @Test
    fun `Get to lists(slash)listID returns a 200 response with the correct response`() {
        val response = app(
            Request(Method.GET, "http://localhost:8080/lists/0")
                .header("Authorization", authHeaderA)
        )

        assertEquals(Status.OK, response.status)
        assertEquals("application/json", response.header("content-type"))
        val listResponse = Json.decodeFromString<ListDTO>(response.bodyString())
        assertEquals(list.toDTO(), listResponse)
    }

    @Test
    fun `Get to lists(slash)listID returns a 400 response if invalid id`() {
        val response = app(
            Request(Method.GET, "http://localhost:8080/lists/invalidID")
                .header("Authorization", authHeaderA)
        )

        assertEquals(Status.BAD_REQUEST, response.status)
        assertEquals("application/json", response.header("content-type"))
        val errorResponse = Json.decodeFromString<ErrorResponse>(response.bodyString())
        assertEquals(
            ErrorResponse(
                code = InvalidListIDException.code,
                name = InvalidListIDException.javaClass.simpleName,
                description = InvalidListIDException.description
            ),
            errorResponse
        )
    }

    @Test
    fun `Get to lists(slash)listID returns a 400 response if invalid auth header`() {
        val response = app(
            Request(Method.GET, "http://localhost:8080/lists/0")
                .header("Authorization", "ola")
        )

        assertEquals(Status.BAD_REQUEST, response.status)
        assertEquals("application/json", response.header("content-type"))
        val errorResponse = Json.decodeFromString<ErrorResponse>(response.bodyString())
        assertEquals(
            ErrorResponse(
                code = InvalidAuthHeaderException.code,
                name = InvalidAuthHeaderException.javaClass.simpleName,
                description = InvalidAuthHeaderException.description
            ),
            errorResponse
        )
    }

    @Test
    fun `Get to lists(slash)listID returns a 400 response if invalid token`() {
        val response = app(
            Request(Method.GET, "http://localhost:8080/lists/0")
                .header("Authorization", "Bearer ola")
        )

        assertEquals(Status.BAD_REQUEST, response.status)
        assertEquals("application/json", response.header("content-type"))
        val errorResponse = Json.decodeFromString<ErrorResponse>(response.bodyString())
        assertEquals(
            ErrorResponse(
                code = InvalidTokenException.code,
                name = InvalidTokenException.javaClass.simpleName,
                description = InvalidTokenException.description
            ),
            errorResponse
        )
    }

    @Test
    fun `Get to lists(slash)listID returns a 401 response if no auth header`() {
        val response = app(
            Request(Method.GET, "http://localhost:8080/lists/0")
        )

        assertEquals(Status.UNAUTHORIZED, response.status)
        assertEquals("application/json", response.header("content-type"))
        val errorResponse = Json.decodeFromString<ErrorResponse>(response.bodyString())
        assertEquals(
            ErrorResponse(
                code = NoAuthenticationException.code,
                name = NoAuthenticationException.javaClass.simpleName,
                description = NoAuthenticationException.description
            ),
            errorResponse
        )
    }

    @Test
    fun `Get to lists(slash)listID returns a 403 response if the user doesn't have access to that list`() {
        val response = app(
            Request(Method.GET, "http://localhost:8080/lists/0")
                .header("Authorization", authHeaderB)
        )

        assertEquals(Status.FORBIDDEN, response.status)
        assertEquals("application/json", response.header("content-type"))
        val errorResponse = Json.decodeFromString<ErrorResponse>(response.bodyString())
        assertEquals(
            ErrorResponse(
                code = IllegalListAccessException.code,
                name = IllegalListAccessException.javaClass.simpleName,
                description = IllegalListAccessException.description
            ),
            errorResponse
        )
    }

    @Test
    fun `Get to lists(slash)listID returns a 404 response if a list with the provided id doesn't exist`() {
        val response = app(
            Request(Method.GET, "http://localhost:8080/lists/99")
                .header("Authorization", authHeaderA)
        )

        assertEquals(Status.NOT_FOUND, response.status)
        assertEquals("application/json", response.header("content-type"))
        val errorResponse = Json.decodeFromString<ErrorResponse>(response.bodyString())
        assertEquals(
            ErrorResponse(
                code = ListNotFoundException.code,
                name = ListNotFoundException.javaClass.simpleName,
                description = ListNotFoundException.description
            ),
            errorResponse
        )
    }

    @Test
    fun `Get to lists(slash)listID(slash)cards returns a 200 response with the correct response`() {
        val response = app(
            Request(Method.GET, "http://localhost:8080/lists/0/cards")
                .header("Authorization", authHeaderA)
        )

        assertEquals(Status.OK, response.status)
        assertEquals("application/json", response.header("content-type"))
        val cardsResponse = Json.decodeFromString<GetCardFromListResponse>(response.bodyString())
        assertEquals(
            emptyList(),
            cardsResponse.cards
        )
    }

    @Test
    fun `Get to lists(slash)listID(slash)cards returns a 400 response if invalid id`() {
        val response = app(
            Request(Method.GET, "http://localhost:8080/lists/invalidID/cards")
                .header("Authorization", authHeaderA)
        )

        assertEquals(Status.BAD_REQUEST, response.status)
        assertEquals("application/json", response.header("content-type"))
        val errorResponse = Json.decodeFromString<ErrorResponse>(response.bodyString())
        assertEquals(
            ErrorResponse(
                code = InvalidListIDException.code,
                name = InvalidListIDException.javaClass.simpleName,
                description = InvalidListIDException.description
            ),
            errorResponse
        )
    }

    @Test
    fun `Get to lists(slash)listID(slash)cards returns a 400 response if invalid auth header`() {
        val response = app(
            Request(Method.GET, "http://localhost:8080/lists/0/cards")
                .header("Authorization", "ola")
        )

        assertEquals(Status.BAD_REQUEST, response.status)
        assertEquals("application/json", response.header("content-type"))
        val errorResponse = Json.decodeFromString<ErrorResponse>(response.bodyString())
        assertEquals(
            ErrorResponse(
                code = InvalidAuthHeaderException.code,
                name = InvalidAuthHeaderException.javaClass.simpleName,
                description = InvalidAuthHeaderException.description
            ),
            errorResponse
        )
    }

    @Test
    fun `Get to lists(slash)listID(slash)cards returns a 400 response if invalid token`() {
        val response = app(
            Request(Method.GET, "http://localhost:8080/lists/0/cards")
                .header("Authorization", "Bearer ola")
        )

        assertEquals(Status.BAD_REQUEST, response.status)
        assertEquals("application/json", response.header("content-type"))
        val errorResponse = Json.decodeFromString<ErrorResponse>(response.bodyString())
        assertEquals(
            ErrorResponse(
                code = InvalidTokenException.code,
                name = InvalidTokenException.javaClass.simpleName,
                description = InvalidTokenException.description
            ),
            errorResponse
        )
    }

    @Test
    fun `Get to lists(slash)listID(slash)cards returns a 401 response if no auth header`() {
        val response = app(
            Request(Method.GET, "http://localhost:8080/lists/0/cards")
        )

        assertEquals(Status.UNAUTHORIZED, response.status)
        assertEquals("application/json", response.header("content-type"))
        val errorResponse = Json.decodeFromString<ErrorResponse>(response.bodyString())
        assertEquals(
            ErrorResponse(
                code = NoAuthenticationException.code,
                name = NoAuthenticationException.javaClass.simpleName,
                description = NoAuthenticationException.description
            ),
            errorResponse
        )
    }

    @Test
    fun `Get to lists(slash)listID(slash)cards returns a 403 response if the user doesn't have access to that list`() {
        val response = app(
            Request(Method.GET, "http://localhost:8080/lists/0/cards")
                .header("Authorization", authHeaderB)
        )

        assertEquals(Status.FORBIDDEN, response.status)
        assertEquals("application/json", response.header("content-type"))
        val errorResponse = Json.decodeFromString<ErrorResponse>(response.bodyString())
        assertEquals(
            ErrorResponse(
                code = IllegalListAccessException.code,
                name = IllegalListAccessException.javaClass.simpleName,
                description = IllegalListAccessException.description
            ),
            errorResponse
        )
    }

    @Test
    fun `Get to lists(slash)listID(slash)cards returns a 404 response if a list with that id doesn't exist`() {
        val response = app(
            Request(Method.GET, "http://localhost:8080/lists/99/cards")
                .header("Authorization", authHeaderA)
        )

        assertEquals(Status.NOT_FOUND, response.status)
        assertEquals("application/json", response.header("content-type"))
        val errorResponse = Json.decodeFromString<ErrorResponse>(response.bodyString())
        assertEquals(
            ErrorResponse(
                code = ListNotFoundException.code,
                name = ListNotFoundException.javaClass.simpleName,
                description = ListNotFoundException.description
            ),
            errorResponse
        )
    }
}
