package pt.isel.ls.integration.api

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Status
import pt.isel.ls.api.TasksWebApi
import pt.isel.ls.api.dto.ErrorResponse
import pt.isel.ls.api.dto.board.CreateBoardRequest
import pt.isel.ls.api.dto.board.CreateBoardResponse
import pt.isel.ls.api.dto.user.CreateUserRequest
import pt.isel.ls.api.dto.user.CreateUserResponse
import pt.isel.ls.api.routers.utils.exceptions.InvalidBodyException
import pt.isel.ls.api.routers.utils.exceptions.NoAuthenticationException
import pt.isel.ls.database.memory.BoardNameAlreadyExistsException
import pt.isel.ls.database.memory.TasksDataMem
import pt.isel.ls.services.TasksServices
import pt.isel.ls.utils.exceptions.InvalidBearerToken
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class BoardTests {

    private val app = TasksWebApi(TasksServices(TasksDataMem())).routes
    private var authHeader: String = ""

    @BeforeTest
    fun `create user to authenticate on the boards`() {
        val name = "Ricardo"
        val email = "a47673@alunos.isel.pt"
        val requestBody = Json.encodeToString(CreateUserRequest(name, email))
        authHeader = "Bearer ${
            Json.decodeFromString<CreateUserResponse>(
                app(
                    Request(Method.POST, "http://localhost:8080/users").body(requestBody)
                ).bodyString()
            ).token
        }"
    }

    @Test
    fun `POST to boards returns a 201 response with the correct response`() {
        val name = "aName"
        val description = "aDescription"
        val requestBody = Json.encodeToString(CreateBoardRequest(name, description))
        val response = app(
            Request(Method.POST, "http://localhost:8080/boards").body(requestBody)
                .header("Authorization", authHeader)
        )
        println(response)
        assertEquals(Status.CREATED, response.status)
        assertEquals("application/json", response.header("content-type"))
        assertEquals("/boards/0", response.header("Location"))
        val boardResponse = Json.decodeFromString<CreateBoardResponse>(response.bodyString())
        assertEquals(0, boardResponse.id)
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
    fun `POST to boards returns a 401 response if no Authentication header is present`() {
        val name = "aName"
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
    fun `POST to boards returns a 401 response if an invalid Authentication header is present`() {
        val name = "aName"
        val description = "aDescription"
        val requestBody = Json.encodeToString(CreateBoardRequest(name, description))
        val response = app(
            Request(Method.POST, "http://localhost:8080/boards").body(requestBody)
                .header("Authorization", "ola")
        )
        println(response.bodyString())
        assertEquals(Status.UNAUTHORIZED, response.status)
        assertEquals("application/json", response.header("content-type"))
        val errorResponse = Json.decodeFromString<ErrorResponse>(response.bodyString())
        assertEquals(
            ErrorResponse(
                code = InvalidBearerToken.code,
                name = InvalidBearerToken.javaClass.simpleName,
                description = InvalidBearerToken.description
            ),
            errorResponse
        )
    }

    @Test
    fun `POST to boards returns a 409 response if a board with that name already exists`() {
        val name = "aName"
        val description = "aDescription"
        val requestBody = Json.encodeToString(CreateBoardRequest(name, description))

        app(Request(Method.POST, "http://localhost:8080/boards").body(requestBody).header("Authorization", authHeader))

        val response = app(
            Request(Method.POST, "http://localhost:8080/boards").body(requestBody)
                .header("Authorization", authHeader)
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
}