package pt.isel.ls.integration.api

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Status
import pt.isel.ls.api.TasksWebApi
import pt.isel.ls.api.dto.ErrorResponse
import pt.isel.ls.api.dto.card.CardDTO
import pt.isel.ls.api.dto.card.CreateCardRequest
import pt.isel.ls.api.dto.card.MoveCardRequest
import pt.isel.ls.api.dto.list.CreateListResponse
import pt.isel.ls.api.routers.utils.exceptions.InvalidAuthHeaderException
import pt.isel.ls.api.routers.utils.exceptions.InvalidBodyException
import pt.isel.ls.api.routers.utils.exceptions.InvalidCardIDException
import pt.isel.ls.api.routers.utils.exceptions.NoAuthenticationException
import pt.isel.ls.database.memory.CardNameAlreadyExistsException
import pt.isel.ls.database.memory.CardNotFoundException
import pt.isel.ls.database.memory.ListNotFoundException
import pt.isel.ls.database.memory.TasksDataMem
import pt.isel.ls.services.TasksServices
import pt.isel.ls.services.utils.MAX_DATE
import pt.isel.ls.services.utils.exceptions.IllegalCardAccessException
import pt.isel.ls.services.utils.exceptions.IllegalListAccessException
import pt.isel.ls.services.utils.exceptions.IllegalMoveCardRequestException
import pt.isel.ls.services.utils.exceptions.InvalidTokenException
import java.sql.Date
import kotlin.test.Test

import kotlin.test.assertEquals

class CardsTests {

    private val database = TasksDataMem()
    private val services = TasksServices(database)
    private val app = TasksWebApi(services).routes
    private val tokenA = "7d444840-9dc0-11d1-b245-5ffdce74fad2"
    private val authHeaderA = "Bearer $tokenA"
    private val passwordA = "6559D8CAEFE3D38D0AD455B8A072BB5A11DA31AC19DA7AFFAD563FC4D0AFF0EF"
    private val tokenB = "7d444840-9dc0-11d1-b245-5ffdce74fad1"
    private val authHeaderB = "Bearer $tokenB"
    private val passwordB = "132513E5601D28F9DBDEBD2590514E171FEFEC9A6BE60417D79B8D626077C3FB"

    private val userA: Int = database.createUser(tokenA, "Ricardo", "A47673@alunos.isel.pt", passwordA)
    private val userB: Int = database.createUser(tokenB, "Luis", "A47671@alunos.isel.pt", passwordB)
    private val boardA = database.createBoard(userA, "aName", "aDescription")
    private val boardB = database.createBoard(userB, "anotherName", "aDescription")
    private val listA = database.createList(boardA, "aList")
    private val listB = database.createList(boardA, "anotherList")
    private val listC = database.createList(boardB, "anotherList")
    private val cardA = database.createCard(
        listA, "aCard", "aDescription", Date(System.currentTimeMillis() + 1000), Date.valueOf(
            MAX_DATE
        )
    )

    @Test
    fun `POST to cards returns a 201 response with the correct response`() {
        val listID = listA
        val name = "anotherCard"
        val description = "aDescription"
        val requestBody = Json.encodeToString(CreateCardRequest(listID, name, description, "2023-04-23", "2023-06-23"))
        val response = app(
            Request(Method.POST, "http://localhost:8080/cards").body(requestBody)
                .header("Authorization", authHeaderA)
        )
        assertEquals(Status.CREATED, response.status)
        assertEquals("application/json", response.header("content-type"))
        assertEquals("/cards/1", response.header("Location"))
        val cardResponse = Json.decodeFromString<CreateListResponse>(response.bodyString())
        assertEquals(1, cardResponse.id)
    }

    @Test
    fun `POST to cards returns a 400 response if invalid body`() {
        val response = app(
            Request(Method.POST, "http://localhost:8080/cards").body("ola")
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
    fun `POST to cards returns a 400 response if invalid auth header`() {
        val listID = listA
        val name = "anotherCard"
        val description = "aDescription"
        val requestBody = Json.encodeToString(CreateCardRequest(listID, name, description, "2023-04-23", null))
        val response = app(
            Request(Method.POST, "http://localhost:8080/cards").body(requestBody)
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
    fun `POST to cards returns a 400 response if invalid token`() {
        val listID = listA
        val name = "anotherCard"
        val description = "aDescription"
        val requestBody = Json.encodeToString(CreateCardRequest(listID, name, description, "2023-04-23", null))
        val response = app(
            Request(Method.POST, "http://localhost:8080/cards").body(requestBody)
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
    fun `POST to cards returns a 401 response if no auth header`() {
        val listID = listA
        val name = "anotherCard"
        val description = "aDescription"
        val requestBody = Json.encodeToString(CreateCardRequest(listID, name, description, "2023-04-23", null))
        val response = app(
            Request(Method.POST, "http://localhost:8080/cards").body(requestBody)
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
    fun `POST to cards returns a 403 response if the user doesn't have access to that list`() {
        val listID = listA
        val name = "anotherCard"
        val description = "aDescription"
        val requestBody = Json.encodeToString(CreateCardRequest(listID, name, description, "2023-04-23", "2023-06-23"))
        val response = app(
            Request(Method.POST, "http://localhost:8080/cards/").body(requestBody)
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
    fun `POST to cards returns a 404 response if a list with the provided id doesn't exist`() {
        val listID = 99
        val name = "anotherCard"
        val description = "aDescription"
        val requestBody = Json.encodeToString(CreateCardRequest(listID, name, description, "2023-04-23", "2023-06-23"))
        val response = app(
            Request(Method.POST, "http://localhost:8080/cards").body(requestBody)
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
    fun `POST to cards returns a 409 response if a card with that name already exists`() {
        val listID = listA
        val name = "aCard"
        val description = "aDescription"
        val requestBody = Json.encodeToString(CreateCardRequest(listID, name, description, "2023-04-23", "2023-06-23"))
        val response = app(
            Request(Method.POST, "http://localhost:8080/cards").body(requestBody)
                .header("Authorization", authHeaderA)
        )
        assertEquals(Status.CONFLICT, response.status)
        assertEquals("application/json", response.header("content-type"))
        val errorResponse = Json.decodeFromString<ErrorResponse>(response.bodyString())
        assertEquals(
            ErrorResponse(
                code = CardNameAlreadyExistsException.code,
                name = CardNameAlreadyExistsException.javaClass.simpleName,
                description = CardNameAlreadyExistsException.description
            ),
            errorResponse
        )
    }

    @Test
    fun `Get to cards(slash)cardID returns a 200 response with the correct response`() {
        val response = app(
            Request(Method.GET, "http://localhost:8080/cards/0")
                .header("Authorization", authHeaderA)
        )

        assertEquals(Status.OK, response.status)
        assertEquals("application/json", response.header("content-type"))
        val cardResponse = Json.decodeFromString<CardDTO>(response.bodyString())
        val card = CardDTO(
            cardA, "aCard", "aDescription", Date(System.currentTimeMillis() + 1000).toString(),
            Date.valueOf(MAX_DATE).toString(), listA, boardA
        )
        assertEquals(card, cardResponse)
    }

    @Test
    fun `Get to cards(slash)cardID returns a 400 response if invalid id`() {
        val response = app(
            Request(Method.GET, "http://localhost:8080/cards/invalidID")
                .header("Authorization", authHeaderA)
        )

        assertEquals(Status.BAD_REQUEST, response.status)
        assertEquals("application/json", response.header("content-type"))
        val errorResponse = Json.decodeFromString<ErrorResponse>(response.bodyString())
        assertEquals(
            ErrorResponse(
                code = InvalidCardIDException.code,
                name = InvalidCardIDException.javaClass.simpleName,
                description = InvalidCardIDException.description
            ),
            errorResponse
        )
    }

    @Test
    fun `Get to cards(slash)cardID returns a 400 response if invalid auth header`() {
        val response = app(
            Request(Method.GET, "http://localhost:8080/cards/0")
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
    fun `Get to cards(slash)cardID returns a 400 response if invalid token`() {
        val response = app(
            Request(Method.GET, "http://localhost:8080/cards/0")
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
    fun `Get to cards(slash)cardID returns a 401 response if no auth header`() {
        val response = app(
            Request(Method.GET, "http://localhost:8080/cards/0")
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
    fun `Get to cards(slash)cardID returns a 403 response if user doesn't have access to that card`() {
        val response = app(
            Request(Method.GET, "http://localhost:8080/cards/0")
                .header("Authorization", authHeaderB)
        )

        assertEquals(Status.FORBIDDEN, response.status)
        assertEquals("application/json", response.header("content-type"))
        val errorResponse = Json.decodeFromString<ErrorResponse>(response.bodyString())
        assertEquals(
            ErrorResponse(
                code = IllegalCardAccessException.code,
                name = IllegalCardAccessException.javaClass.simpleName,
                description = IllegalCardAccessException.description
            ),
            errorResponse
        )
    }

    @Test
    fun `Get to cards(slash)cardID returns a 404 response if a card with that id doesn't exist`() {
        val response = app(
            Request(Method.GET, "http://localhost:8080/cards/99")
                .header("Authorization", authHeaderA)
        )

        assertEquals(Status.NOT_FOUND, response.status)
        assertEquals("application/json", response.header("content-type"))
        val errorResponse = Json.decodeFromString<ErrorResponse>(response.bodyString())
        assertEquals(
            ErrorResponse(
                code = CardNotFoundException.code,
                name = CardNotFoundException.javaClass.simpleName,
                description = CardNotFoundException.description
            ),
            errorResponse
        )
    }

    @Test
    fun `Post to cards(slash)cardID(slash)move returns a 204 response`() {
        val listID = listB
        val requestBody = Json.encodeToString(MoveCardRequest(listID, 0))
        val response = app(
            Request(Method.POST, "http://localhost:8080/cards/0/move").body(requestBody)
                .header("Authorization", authHeaderA)
        )
        assertEquals(Status.NO_CONTENT, response.status)
    }

    @Test
    fun `Post to cards(slash)cardID(slash)move returns a 400 response if invalid id`() {
        val listID = listB
        val requestBody = Json.encodeToString(MoveCardRequest(listID, 0))
        val response = app(
            Request(Method.POST, "http://localhost:8080/cards/invalidID/move").body(requestBody)
                .header("Authorization", authHeaderA)
        )
        assertEquals(Status.BAD_REQUEST, response.status)
        assertEquals("application/json", response.header("content-type"))
        val errorResponse = Json.decodeFromString<ErrorResponse>(response.bodyString())
        assertEquals(
            ErrorResponse(
                code = InvalidCardIDException.code,
                name = InvalidCardIDException.javaClass.simpleName,
                description = InvalidCardIDException.description
            ),
            errorResponse
        )
    }

    @Test
    fun `Post to cards(slash)cardID(slash)move returns a 400 response if invalid body`() {
        val response = app(
            Request(Method.POST, "http://localhost:8080/cards/0/move").body("ola")
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
    fun `Post to cards(slash)cardID(slash)move returns a 400 response if invalid auth header`() {
        val listID = listB
        val requestBody = Json.encodeToString(MoveCardRequest(listID, 0))
        val response = app(
            Request(Method.POST, "http://localhost:8080/cards/0/move").body(requestBody)
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
    fun `Post to cards(slash)cardID(slash)move returns a 400 response if invalid token`() {
        val listID = listB
        val requestBody = Json.encodeToString(MoveCardRequest(listID, 0))
        val response = app(
            Request(Method.POST, "http://localhost:8080/cards/0/move").body(requestBody)
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
    fun `Post to cards(slash)cardID(slash)move returns a 401 response if no auth header`() {
        val listID = listB
        val requestBody = Json.encodeToString(MoveCardRequest(listID, 0))
        val response = app(
            Request(Method.POST, "http://localhost:8080/cards/0/move").body(requestBody)
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
    fun `Post to cards(slash)cardID(slash)move returns a 403 response if user doesn't have access to that card`() {
        val listID = listB
        val requestBody = Json.encodeToString(MoveCardRequest(listID, 0))
        val response = app(
            Request(Method.POST, "http://localhost:8080/cards/0/move").body(requestBody)
                .header("Authorization", authHeaderB)
        )
        assertEquals(Status.FORBIDDEN, response.status)
        assertEquals("application/json", response.header("content-type"))
        val errorResponse = Json.decodeFromString<ErrorResponse>(response.bodyString())
        assertEquals(
            ErrorResponse(
                code = IllegalCardAccessException.code,
                name = IllegalCardAccessException.javaClass.simpleName,
                description = IllegalCardAccessException.description
            ),
            errorResponse
        )
    }

    @Test
    fun `Post to cards(slash)cardID(slash)move returns a 404 response if a card with that ID doesn't exist`() {
        val listID = listB
        val requestBody = Json.encodeToString(MoveCardRequest(listID, 0))
        val response = app(
            Request(Method.POST, "http://localhost:8080/cards/99/move").body(requestBody)
                .header("Authorization", authHeaderA)
        )
        assertEquals(Status.NOT_FOUND, response.status)
        assertEquals("application/json", response.header("content-type"))
        val errorResponse = Json.decodeFromString<ErrorResponse>(response.bodyString())
        assertEquals(
            ErrorResponse(
                code = CardNotFoundException.code,
                name = CardNotFoundException.javaClass.simpleName,
                description = CardNotFoundException.description
            ),
            errorResponse
        )
    }

    @Test
    fun `Post to cards(slash)cardID(slash)move returns a 404 response if a list with that ID doesn't exist`() {
        val listID = 99
        val requestBody = Json.encodeToString(MoveCardRequest(listID, 0))
        val response = app(
            Request(Method.POST, "http://localhost:8080/cards/0/move").body(requestBody)
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
    fun `Post to cards(slash)cardID(slash)move returns a 422 response if card isn't on the same board as the provided list`() {
        val listID = listC
        val requestBody = Json.encodeToString(MoveCardRequest(listID, 0))
        val response = app(
            Request(Method.POST, "http://localhost:8080/cards/0/move").body(requestBody)
                .header("Authorization", authHeaderA)
        )

        assertEquals(Status.UNPROCESSABLE_ENTITY, response.status)
        assertEquals("application/json", response.header("content-type"))
        val errorResponse = Json.decodeFromString<ErrorResponse>(response.bodyString())
        assertEquals(
            ErrorResponse(
                code = IllegalMoveCardRequestException.code,
                name = IllegalMoveCardRequestException.javaClass.simpleName,
                description = IllegalMoveCardRequestException.description
            ),
            errorResponse
        )
    }
}