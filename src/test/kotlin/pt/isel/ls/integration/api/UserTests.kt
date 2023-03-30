package pt.isel.ls.integration.api

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Status
import pt.isel.ls.api.TasksWebApi
import pt.isel.ls.api.dto.ErrorResponse
import pt.isel.ls.api.dto.user.CreateUserRequest
import pt.isel.ls.api.dto.user.CreateUserResponse
import pt.isel.ls.api.dto.user.GetBoardsFromUserResponse
import pt.isel.ls.api.dto.user.UserDTO
import pt.isel.ls.api.routers.utils.exceptions.InvalidAuthHeader
import pt.isel.ls.api.routers.utils.exceptions.InvalidBodyException
import pt.isel.ls.api.routers.utils.exceptions.InvalidUserIDException
import pt.isel.ls.api.routers.utils.exceptions.NoAuthenticationException
import pt.isel.ls.database.memory.EmailAlreadyExistsException
import pt.isel.ls.database.memory.TasksDataMem
import pt.isel.ls.database.memory.UserNotFoundException
import pt.isel.ls.services.TasksServices
import pt.isel.ls.services.utils.exceptions.IllegalUserAccessException
import pt.isel.ls.services.utils.exceptions.InvalidBearerToken
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class UserTests {

    private val app = TasksWebApi(TasksServices(TasksDataMem())).routes

    @Test
    fun `POST to users returns a 201 response with the correct response`() {
        val name = "Ricardo"
        val email = "a47673@alunos.isel.pt"
        val requestBody = Json.encodeToString(CreateUserRequest(name, email))
        val response = app(
            Request(Method.POST, "http://localhost:8080/users").body(requestBody)
        )
        assertEquals(Status.CREATED, response.status)
        assertEquals("application/json", response.header("content-type"))
        assertEquals("/users/0", response.header("Location"))
        val userResponse = Json.decodeFromString<CreateUserResponse>(response.bodyString())
        assertEquals(0, userResponse.id)
    }

    @Test
    fun `POST to users returns a 400 response for no body`() {
        val response = app(
            Request(Method.POST, "http://localhost:8080/users")
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
    fun `POST to users returns a 400 response for an invalid body`() {
        val response = app(
            Request(Method.POST, "http://localhost:8080/users").body("Hello!")
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
    fun `POST to users returns a 409 response if a user with that email already exists`() {
        val name = "Ricardo"
        val email = "a47673@alunos.isel.pt"
        val requestBody = Json.encodeToString(CreateUserRequest(name, email))
        app(
            Request(Method.POST, "http://localhost:8080/users").body(requestBody)
        )

        val response = app(
            Request(Method.POST, "http://localhost:8080/users").body(requestBody)
        )
        assertEquals(Status.CONFLICT, response.status)
        assertEquals("application/json", response.header("content-type"))
        val errorResponse = Json.decodeFromString<ErrorResponse>(response.bodyString())
        assertEquals(
            ErrorResponse(
                code = EmailAlreadyExistsException.code,
                name = EmailAlreadyExistsException.javaClass.simpleName,
                description = EmailAlreadyExistsException.description
            ),
            errorResponse
        )
    }

    @Test
    fun `GET to users(slash)userID returns a 200 response with the correct response`() {
        val name = "Ricardo"
        val email = "a47673@alunos.isel.pt"
        val requestBody = Json.encodeToString(CreateUserRequest(name, email))
        app(
            Request(Method.POST, "http://localhost:8080/users").body(requestBody)
        )
        val response = app(Request(Method.GET, "http://localhost:8080/users/0"))
        assertEquals(Status.OK, response.status)
        assertEquals("application/json", response.header("content-type"))
        val userResponse = Json.decodeFromString<UserDTO>(response.bodyString())
        assertEquals(0, userResponse.id)
        assertEquals(name, userResponse.name)
        assertEquals(email, userResponse.email)
    }

    @Test
    fun `GET to users(slash)userID returns a 400 response for an invalid id`() {
        val response = app(Request(Method.GET, "http://localhost:8080/users/invalidID"))
        assertEquals(Status.BAD_REQUEST, response.status)
        assertEquals("application/json", response.header("content-type"))
        val errorResponse = Json.decodeFromString<ErrorResponse>(response.bodyString())
        assertEquals(
            ErrorResponse(
                code = InvalidUserIDException.code,
                name = InvalidUserIDException.javaClass.simpleName,
                description = InvalidUserIDException.description
            ),
            errorResponse
        )
    }

    @Test
    fun `GET to users(slash)userID returns a 404 response for an invalid id`() {
        val response = app(Request(Method.GET, "http://localhost:8080/users/75"))

        assertEquals(Status.NOT_FOUND, response.status)
        assertEquals("application/json", response.header("content-type"))
        val errorResponse = Json.decodeFromString<ErrorResponse>(response.bodyString())
        assertEquals(
            ErrorResponse(
                code = UserNotFoundException.code,
                name = UserNotFoundException.javaClass.simpleName,
                description = UserNotFoundException.description
            ),
            errorResponse
        )
    }

    @Test
    fun `GET to users(slash)userID(slash)boards returns a 200 response with the correct response`() {
        val name = "Ricardo"
        val email = "a47673@alunos.isel.pt"
        val requestBody = Json.encodeToString(CreateUserRequest(name, email))
        val token = Json.decodeFromString<CreateUserResponse>(
            app(
                Request(Method.POST, "http://localhost:8080/users").body(requestBody)
            ).bodyString()
        ).token
        val response = app(
            Request(Method.GET, "http://localhost:8080/users/0/boards")
                .header("Authorization", "Bearer $token")
        )
        assertEquals(Status.OK, response.status)
        assertEquals("application/json", response.header("content-type"))
        val boards = Json.decodeFromString<GetBoardsFromUserResponse>(response.bodyString())
        assertTrue(boards.boards.isEmpty())
    }

    @Test
    fun `GET to users(slash)userID(slash)boards returns a 400 response for an invalid id`() {
        val response = app(Request(Method.GET, "http://localhost:8080/users/invalidID/boards"))
        assertEquals(Status.BAD_REQUEST, response.status)
        assertEquals("application/json", response.header("content-type"))
        val errorResponse = Json.decodeFromString<ErrorResponse>(response.bodyString())
        assertEquals(
            ErrorResponse(
                code = InvalidUserIDException.code,
                name = InvalidUserIDException.javaClass.simpleName,
                description = InvalidUserIDException.description
            ),
            errorResponse
        )
    }

    @Test
    fun `GET to users(slash)userID(slash)boards returns a 401 response if no Authentication header is present`() {
        val response = app(Request(Method.GET, "http://localhost:8080/users/0/boards"))
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
    fun `GET to users(slash)userID(slash)boards returns a 400 response if an invalid Authentication header is present`() {
        val response = app(
            Request(Method.GET, "http://localhost:8080/users/0/boards")
                .header("Authorization", "ola")
        )
        println(response.bodyString())
        assertEquals(Status.BAD_REQUEST, response.status)
        assertEquals("application/json", response.header("content-type"))
        val errorResponse = Json.decodeFromString<ErrorResponse>(response.bodyString())
        assertEquals(
            ErrorResponse(
                code = InvalidAuthHeader.code,
                name = InvalidAuthHeader.javaClass.simpleName,
                description = InvalidAuthHeader.description
            ),
            errorResponse
        )
    }

    @Test
    fun `GET to users(slash)userID(slash)boards returns a 400 response if an invalid token is present`() {
        val response = app(
            Request(Method.GET, "http://localhost:8080/users/0/boards")
                .header("Authorization", "Bearer ola")
        )
        println(response.bodyString())
        assertEquals(Status.BAD_REQUEST, response.status)
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
    fun `GET to users(slash)userID(slash)boards returns a 403 response if the user doesn't have access to that user`() {
        app(
            Request(Method.POST, "http://localhost:8080/users")
                .body(Json.encodeToString(CreateUserRequest("Ricardo", "a47673@alunos.isel.pt")))
        )

        val response = app(
            Request(Method.GET, "http://localhost:8080/users/0/boards")
                .header("Authorization", "Bearer 7d444840-9dc0-11d1-b245-5ffdce74fad2")
        )
        println(response.bodyString())
        assertEquals(Status.FORBIDDEN, response.status)
        assertEquals("application/json", response.header("content-type"))
        val errorResponse = Json.decodeFromString<ErrorResponse>(response.bodyString())
        assertEquals(
            ErrorResponse(
                code = IllegalUserAccessException.code,
                name = IllegalUserAccessException.javaClass.simpleName,
                description = IllegalUserAccessException.description
            ),
            errorResponse
        )
    }
}
