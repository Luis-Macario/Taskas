package pt.isel.ls.integration.api

/*
class UsersTests {
    private val database = TasksDataMem()
    private val services = TasksServices(database)
    private val app = TasksWebApi(services).routes
    private val tokenA = "7d444840-9dc0-11d1-b245-5ffdce74fad2"
    private val authHeaderA = "Bearer $tokenA"
    private val nameA = "Ricardo"
    private val emailA = "A47673@alunos.isel.pt"
    private val tokenB = "7d444840-9dc0-11d1-b245-5ffdce74fad1"
    private val authHeaderB = "Bearer $tokenB"
    private val nameB = "Luis"
    private val emailB = "A47671@alunos.isel.pt"

    private val userA: User = User(database.createUser(tokenA, nameA, emailA,), nameA, emailA, tokenA)

    init {
        database.createUser(tokenB, nameB, emailB,)
    }

    @Test
    fun `POST to users returns a 201 response with the correct response`() {
        val name = "Francisco"
        val email = "A46631@alunos.isel.pt"
        val requestBody = Json.encodeToString(CreateUserRequest(name, email))
        val response = app(
            Request(Method.POST, "http://localhost:8080/users").body(requestBody)
        )
        assertEquals(Status.CREATED, response.status)
        assertEquals("application/json", response.header("content-type"))
        assertEquals("/users/2", response.header("Location"))
        val userResponse = Json.decodeFromString<CreateUserResponse>(response.bodyString())
        assertEquals(2, userResponse.id)
        assertEquals(name, database.getUserDetails(2).name)
        assertEquals(email, database.getUserDetails(2).email)
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
        val name = "anotherRicardo"
        val email = "A47673@alunos.isel.pt"
        val requestBody = Json.encodeToString(CreateUserRequest(name, email))
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
        val response = app(Request(Method.GET, "http://localhost:8080/users/0"))
        assertEquals(Status.OK, response.status)
        assertEquals("application/json", response.header("content-type"))
        val userResponse = Json.decodeFromString<UserDTO>(response.bodyString())
        assertEquals(0, userResponse.id)
        assertEquals(userA.toDTO(), userResponse)
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
    fun `GET to users(slash)userID returns a 404 response if no user with that id exists`() {
        val response = app(Request(Method.GET, "http://localhost:8080/users/99"))

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
        val response = app(
            Request(Method.GET, "http://localhost:8080/users/0/boards")
                .header("Authorization", authHeaderA)
        )
        assertEquals(Status.OK, response.status)
        assertEquals("application/json", response.header("content-type"))
        val boards = Json.decodeFromString<GetBoardsFromUserResponse>(response.bodyString())
        assertEquals(emptyList(), boards.boards)
    }

    @Test
    fun `GET to users(slash)userID(slash)boards returns a 400 response for an invalid id`() {
        val response = app(
            Request(Method.GET, "http://localhost:8080/users/invalidID/boards")
                .header("Authorization", authHeaderA)
        )
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
    fun `GET to users(slash)userID(slash)boards returns a 400 response if an invalid Authentication header is present`() {
        val response = app(
            Request(Method.GET, "http://localhost:8080/users/0/boards")
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
    fun `GET to users(slash)userID(slash)boards returns a 400 response if an invalid token is present`() {
        val response = app(
            Request(Method.GET, "http://localhost:8080/users/0/boards")
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
    fun `GET to users(slash)userID(slash)boards returns a 403 response if the user doesn't have access to that user`() {
        val response = app(
            Request(Method.GET, "http://localhost:8080/users/0/boards")
                .header("Authorization", authHeaderB)
        )

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
}*/
