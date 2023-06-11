package pt.isel.ls.integration.api

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Status
import pt.isel.ls.api.TasksWebApi
import pt.isel.ls.api.dto.ErrorResponse
import pt.isel.ls.api.dto.board.AddUserRequest
import pt.isel.ls.api.dto.board.BoardDTO
import pt.isel.ls.api.dto.board.CreateBoardRequest
import pt.isel.ls.api.dto.board.CreateBoardResponse
import pt.isel.ls.api.dto.board.GetListsFromBoardResponse
import pt.isel.ls.api.dto.board.GetUsersFromBoardResponse
import pt.isel.ls.api.dto.board.toDTO
import pt.isel.ls.api.dto.user.toDTO
import pt.isel.ls.api.routers.utils.exceptions.InvalidAuthHeaderException
import pt.isel.ls.api.routers.utils.exceptions.InvalidBoardIDException
import pt.isel.ls.api.routers.utils.exceptions.InvalidBodyException
import pt.isel.ls.api.routers.utils.exceptions.NoAuthenticationException
import pt.isel.ls.database.memory.BoardNameAlreadyExistsException
import pt.isel.ls.database.memory.BoardNotFoundException
import pt.isel.ls.database.memory.TasksDataMem
import pt.isel.ls.database.memory.UserAlreadyExistsInBoardException
import pt.isel.ls.domain.Board
import pt.isel.ls.domain.User
import pt.isel.ls.services.TasksServices
import pt.isel.ls.services.utils.exceptions.IllegalBoardAccessException
import pt.isel.ls.services.utils.exceptions.InvalidTokenException
import kotlin.test.Test
import kotlin.test.assertEquals

class BoardsTests {
    private val database = TasksDataMem()
    private val services = TasksServices(database)
    private val app = TasksWebApi(services).routes
    private val tokenA = "7d444840-9dc0-11d1-b245-5ffdce74fad2"
    private val authHeaderA = "Bearer $tokenA"
    private val nameA = "Ricardo"
    private val emailA = "A47673@alunos.isel.pt"
    private val tokenB = "7d444840-9dc0-11d1-b245-5ffdce74fad1"
    private val authHeaderB = "Bearer $tokenB"
    private val password = "teste password"
    private val nameB = "Luis"
    private val emailB = "A47671@alunos.isel.pt"
    private val boardName = "aName"
    private val boardDescription = "aDescription"

    private val userA: User = User(database.createUser(tokenA, nameA, emailA, password), nameA, emailA, tokenA)
    private val userB: User = User(database.createUser(tokenB, nameB, emailB, password), nameB, emailB, tokenB)
    private val board =
        Board(database.createBoard(userA.id, boardName, boardDescription), boardName, boardDescription, listOf())

    @Test
    fun `POST to boards returns a 201 response with the correct response`() {
        val name = "anotherName"
        val description = "aDescription"
        val requestBody = Json.encodeToString(CreateBoardRequest(name, description))
        val response = app(
            Request(Method.POST, "http://localhost:8080/boards").body(requestBody)
                .header("Authorization", authHeaderA)
        )
        assertEquals(Status.CREATED, response.status)
        assertEquals("application/json", response.header("content-type"))
        assertEquals("/boards/1", response.header("Location"))
        val boardResponse = Json.decodeFromString<CreateBoardResponse>(response.bodyString())
        assertEquals(1, boardResponse.id)
        assertEquals(name, database.getBoardDetails(1).name)
        assertEquals(description, database.getBoardDetails(1).description)
    }

    @Test
    fun `POST to boards returns a 400 response for no body`() {
        val response = app(
            Request(Method.POST, "http://localhost:8080/boards")
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
    fun `POST to boards returns a 400 response for an invalid body`() {
        val response = app(
            Request(Method.POST, "http://localhost:8080/boards").body("Hello!")
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
    fun `POST to boards returns a 400 response if an invalid Authentication header is present`() {
        val name = "anotherName"
        val description = "aDescription"
        val requestBody = Json.encodeToString(CreateBoardRequest(name, description))
        val response = app(
            Request(Method.POST, "http://localhost:8080/boards").body(requestBody)
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
    fun `POST to boards returns a 400 response if an invalid token is present`() {
        val name = "anotherName"
        val description = "aDescription"
        val requestBody = Json.encodeToString(CreateBoardRequest(name, description))
        val response = app(
            Request(Method.POST, "http://localhost:8080/boards").body(requestBody)
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
    fun `POST to boards returns a 401 response if no Authentication header is present`() {
        val name = "anotherName"
        val description = "aDescription"
        val requestBody = Json.encodeToString(CreateBoardRequest(name, description))
        val response = app(
            Request(Method.POST, "http://localhost:8080/boards").body(requestBody)
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
    fun `POST to boards returns a 409 response if a board with that name already exists`() {
        val requestBody = Json.encodeToString(CreateBoardRequest(board.name, "aDescription"))
        val response = app(
            Request(Method.POST, "http://localhost:8080/boards").body(requestBody)
                .header("Authorization", authHeaderA)
        )
        assertEquals(Status.CONFLICT, response.status)
        assertEquals("application/json", response.header("content-type"))
        val errorResponse = Json.decodeFromString<ErrorResponse>(response.bodyString())
        assertEquals(
            ErrorResponse(
                code = BoardNameAlreadyExistsException.code,
                name = BoardNameAlreadyExistsException.javaClass.simpleName,
                description = BoardNameAlreadyExistsException.description
            ),
            errorResponse
        )
    }

    @Test
    fun `GET to boards(slash)boardID returns a 200 response with the correct response`() {
        val response = app(
            Request(Method.GET, "http://localhost:8080/boards/0")
                .header("Authorization", authHeaderA)
        )

        assertEquals(Status.OK, response.status)
        assertEquals("application/json", response.header("content-type"))
        val boardResponse = Json.decodeFromString<BoardDTO>(response.bodyString())
        assertEquals(board.toDTO(), boardResponse)
    }

    @Test
    fun `GET to boards(slash)boardID returns a 400 response if an invalid boardID is provided`() {
        val response = app(
            Request(Method.GET, "http://localhost:8080/boards/invalidID")
                .header("Authorization", authHeaderA)
        )

        assertEquals(Status.BAD_REQUEST, response.status)
        assertEquals("application/json", response.header("content-type"))
        val errorResponse = Json.decodeFromString<ErrorResponse>(response.bodyString())
        assertEquals(
            ErrorResponse(
                code = InvalidBoardIDException.code,
                name = InvalidBoardIDException.javaClass.simpleName,
                description = InvalidBoardIDException.description
            ),
            errorResponse
        )
    }

    @Test
    fun `GET to boards(slash)boardID returns a 400 response if an invalid authorization header is provided`() {
        val response = app(
            Request(Method.GET, "http://localhost:8080/boards/0")
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
    fun `GET to boards(slash)boardID returns a 400 response if an invalid bearer token is provided`() {
        val response = app(
            Request(Method.GET, "http://localhost:8080/boards/0")
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
    fun `GET to boards(slash)boardID returns a 401 response if no authentication header is provided`() {
        val response = app(
            Request(Method.GET, "http://localhost:8080/boards/0")
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
    fun `GET to boards(slash)boardID returns a 403 response if the user doesn't have access to that board`() {
        val response = app(
            Request(Method.GET, "http://localhost:8080/boards/0")
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
    fun `GET to boards(slash)boardID returns a 404 response if a board with that boardID doesn't exist`() {
        val response = app(
            Request(Method.GET, "http://localhost:8080/boards/99")
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
    fun `GET to boards(slash)boardID(slash)users returns a 200 response with the correct response`() {
        val response = app(
            Request(Method.GET, "http://localhost:8080/boards/0/users")
                .header("Authorization", authHeaderA)
        )

        assertEquals(Status.OK, response.status)
        assertEquals("application/json", response.header("content-type"))
        val usersResponse = Json.decodeFromString<GetUsersFromBoardResponse>(response.bodyString())
        assertEquals(GetUsersFromBoardResponse(listOf(userA.toDTO())), usersResponse)
    }

    @Test
    fun `GET to boards(slash)boardID(slash)users returns a 400 response if the boardID is invalid`() {
        val response = app(
            Request(Method.GET, "http://localhost:8080/boards/invalidID/users")
                .header("Authorization", authHeaderA)
        )

        assertEquals(Status.BAD_REQUEST, response.status)
        assertEquals("application/json", response.header("content-type"))
        val errorResponse = Json.decodeFromString<ErrorResponse>(response.bodyString())
        assertEquals(
            ErrorResponse(
                code = InvalidBoardIDException.code,
                name = InvalidBoardIDException.javaClass.simpleName,
                description = InvalidBoardIDException.description
            ),
            errorResponse
        )
    }

    @Test
    fun `GET to boards(slash)boardID(slash)users returns a 400 response if the authorization header is invalid`() {
        val response = app(
            Request(Method.GET, "http://localhost:8080/boards/0/users")
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
    fun `GET to boards(slash)boardID(slash)users returns a 400 response if the token is invalid`() {
        val response = app(
            Request(Method.GET, "http://localhost:8080/boards/0/users")
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
    fun `GET to boards(slash)boardID(slash)users returns a 401 response if no authorization header is present`() {
        val response = app(
            Request(Method.GET, "http://localhost:8080/boards/0/users")
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
    fun `GET to boards(slash)boardID(slash)users returns a 403 response if  the user doesn't have access to that board`() {
        val response = app(
            Request(Method.GET, "http://localhost:8080/boards/0/users")
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
    fun `GET to boards(slash)boardID(slash)users returns a 404 response if no board with that boardID exists`() {
        val response = app(
            Request(Method.GET, "http://localhost:8080/boards/99/users")
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
    fun `POST to boards(slash))boardID(slash)users returns a 204 response if successful`() {
        val userID = userB.id
        val requestBody = Json.encodeToString(AddUserRequest(userID))
        val response = app(
            Request(Method.POST, "http://localhost:8080/boards/0/users").body(requestBody)
                .header("Authorization", authHeaderA)
        )
        assertEquals(Status.NO_CONTENT, response.status)
        assertEquals(null, response.header("content-type"))
        assertEquals("", response.bodyString())
    }

    @Test
    fun `POST to boards(slash))boardID(slash)users returns a 400 response if invalid boardID`() {
        val userID = userB.id
        val requestBody = Json.encodeToString(AddUserRequest(userID))

        val response = app(
            Request(Method.POST, "http://localhost:8080/boards/invalidID/users").body(requestBody)
                .header("Authorization", authHeaderA)
        )

        assertEquals(Status.BAD_REQUEST, response.status)
        assertEquals("application/json", response.header("content-type"))
        val errorResponse = Json.decodeFromString<ErrorResponse>(response.bodyString())
        assertEquals(
            ErrorResponse(
                code = InvalidBoardIDException.code,
                name = InvalidBoardIDException.javaClass.simpleName,
                description = InvalidBoardIDException.description
            ),
            errorResponse
        )
    }

    @Test
    fun `POST to boards(slash))boardID(slash)users returns a 400 response if invalid body`() {
        val response = app(
            Request(Method.POST, "http://localhost:8080/boards/0/users").body("ola")
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
    fun `POST to boards(slash))boardID(slash)users returns a 400 response if invalid auth header`() {
        val userID = userB.id
        val requestBody = Json.encodeToString(AddUserRequest(userID))

        val response = app(
            Request(Method.POST, "http://localhost:8080/boards/0/users").body(requestBody)
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
    fun `POST to boards(slash))boardID(slash)users returns a 400 response if invalid token`() {
        val userID = userB.id
        val requestBody = Json.encodeToString(AddUserRequest(userID))

        val response = app(
            Request(Method.POST, "http://localhost:8080/boards/0/users").body(requestBody)
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
    fun `POST to boards(slash))boardID(slash)users returns a 401 response if no auth header`() {
        val userID = userB.id
        val requestBody = Json.encodeToString(AddUserRequest(userID))

        val response = app(
            Request(Method.POST, "http://localhost:8080/boards/0/users").body(requestBody)
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
    fun `POST to boards(slash))boardID(slash)users returns a 403 response if user doesn't have access to that board`() {
        val userID = userB.id
        val requestBody = Json.encodeToString(AddUserRequest(userID))

        val response = app(
            Request(Method.POST, "http://localhost:8080/boards/0/users").body(requestBody)
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
    fun `POST to boards(slash))boardID(slash)users returns a 404 response if the board doesn't exist`() {
        val userID = userB.id
        val requestBody = Json.encodeToString(AddUserRequest(userID))

        val response = app(
            Request(Method.POST, "http://localhost:8080/boards/99/users").body(requestBody)
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
    fun `POST to boards(slash))boardID(slash)users returns a 409 response if user is already in board`() {
        val userID = userA.id
        val requestBody = Json.encodeToString(AddUserRequest(userID))

        val response = app(
            Request(Method.POST, "http://localhost:8080/boards/0/users").body(requestBody)
                .header("Authorization", authHeaderA)
        )

        assertEquals(Status.CONFLICT, response.status)
        assertEquals("application/json", response.header("content-type"))
        val errorResponse = Json.decodeFromString<ErrorResponse>(response.bodyString())
        assertEquals(
            ErrorResponse(
                code = UserAlreadyExistsInBoardException.code,
                name = UserAlreadyExistsInBoardException.javaClass.simpleName,
                description = UserAlreadyExistsInBoardException.description
            ),
            errorResponse
        )
    }

    @Test
    fun `GET to boards(slash)boardID(slash)lists returns a 200 response with the correct response`() {
        val response = app(
            Request(Method.GET, "http://localhost:8080/boards/0/lists")
                .header("Authorization", authHeaderA)
        )
        assertEquals(Status.OK, response.status)
        assertEquals("application/json", response.header("content-type"))
        val listsResponse = Json.decodeFromString<GetListsFromBoardResponse>(response.bodyString())
        assertEquals(GetListsFromBoardResponse(emptyList()), listsResponse)
    }

    @Test
    fun `GET to boards(slash)boardID(slash)lists returns a 400 response if invalid boardID`() {
        val response = app(
            Request(Method.GET, "http://localhost:8080/boards/invalidID/lists")
                .header("Authorization", authHeaderA)
        )

        assertEquals(Status.BAD_REQUEST, response.status)
        assertEquals("application/json", response.header("content-type"))
        val errorResponse = Json.decodeFromString<ErrorResponse>(response.bodyString())
        assertEquals(
            ErrorResponse(
                code = InvalidBoardIDException.code,
                name = InvalidBoardIDException.javaClass.simpleName,
                description = InvalidBoardIDException.description
            ),
            errorResponse
        )
    }

    @Test
    fun `GET to boards(slash)boardID(slash)lists returns a 400 response if invalid auth header`() {
        val response = app(
            Request(Method.GET, "http://localhost:8080/boards/0/lists")
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
    fun `GET to boards(slash)boardID(slash)lists returns a 400 response if invalid token`() {
        val response = app(
            Request(Method.GET, "http://localhost:8080/boards/0/lists")
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
    fun `GET to boards(slash)boardID(slash)lists returns a 401 response if no auth header`() {
        val response = app(
            Request(Method.GET, "http://localhost:8080/boards/0/lists")
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
    fun `GET to boards(slash)boardID(slash)lists returns a 403 response if user doesn't have access to that board`() {
        val response = app(
            Request(Method.GET, "http://localhost:8080/boards/0/lists")
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
    fun `GET to boards(slash)boardID(slash)lists returns a 404 response if a board with the provided boardID doesn't exist`() {
        val response = app(
            Request(Method.GET, "http://localhost:8080/boards/99/lists")
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
}
