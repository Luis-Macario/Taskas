package pt.isel.ls.unit.database.postgres

import org.postgresql.ds.PGSimpleDataSource
import pt.isel.ls.database.memory.UserNotFoundException
import pt.isel.ls.database.sql.TasksDataPostgres
import pt.isel.ls.domain.User
import java.util.UUID
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class UserTests {
    private val url = System.getenv("JDBC_DATABASE_URL")
    private val db = TasksDataPostgres(url)
    private val tokenA = "7d444840-9dc0-11d1-b245-5ffdce74fad1"
    private val passwordA = "132513E5601D28F9DBDEBD2590514E171FEFEC9A6BE60417D79B8D626077C3FB"
    private val nameA = "Francisco Goat"
    private val emailA = "franciscoGoat@gmail.com"
    private val dataSource = PGSimpleDataSource().apply {
        this.setUrl(url)
    }

    @BeforeTest
    fun cleanAndAddData() {
        dropTableAndAddData(dataSource)
    }

    @Test
    fun `createUser creates User successfully`() {
        val newUser = db.createUser(tokenA, nameA, emailA, passwordA)

        dataSource.connection.use { connection ->
            connection.autoCommit = false
            val stm = connection.prepareStatement(
                """
                SELECT * FROM users where id = ?
                """.trimIndent()
            )
            stm.setInt(1, newUser)

            val rs = stm.executeQuery()
            assertTrue(rs.next())
            assertEquals(newUser, rs.getInt("id"))
            assertEquals(nameA, rs.getString("name"))
            assertEquals(emailA, rs.getString("email"))
            assertEquals(tokenA, rs.getString("token"))
        }
    }

    @Test
    fun `getUser returns the User() successfully after beingCreated`() {
        val newUser = db.createUser(tokenA, nameA, emailA, passwordA)

        val getUser = db.getUserDetails(newUser)

        assertEquals(newUser, getUser.id)
        assertEquals(nameA, getUser.name)
        assertEquals(emailA, getUser.email)
        assertEquals(tokenA, getUser.token)
    }

    @Test
    fun `getUser throws UserNotFoundException if given wrong id`() {
        val db = TasksDataPostgres(url)

        val msg = assertFailsWith<UserNotFoundException> {
            db.getUserDetails(1000)
        }

        assertEquals(UserNotFoundException.description, msg.description)
    }

    @Test
    fun `check if emailAlreadyExists returns true for repeted email`() {
        val user = db.getUserDetails(1)
        val sut = db.checkEmailAlreadyExists(user.email)

        assertTrue(sut)

        val sut2 = db.checkEmailAlreadyExists("randomEmail@sapo.pt")
        assertTrue(!sut2)
    }

    @Test
    fun `getUsersFromBoard returns correct users list`() {
        val listUser = listOf<User>(
            User(1, "Francisco Medeiros", "a46631@alunos.isel.pt", "160ee838-150b-4ca1-a2ff-2e964383c315"),
            User(2, "Ricardo Pinto", "a47673@alunos.isel.pt", "12971dc2-6816-4851-b110-e19065747785"),
            User(3, "Luis Macario", "a47671@alunos.isel.pt", "658baaa9-4035-415e-9674-6957704600ba")

        )
        val sut = db.getUsersFromBoard(1)

        assertEquals(3, sut.size)
        assertEquals(listUser, sut)
    }

    @Test
    fun `getUsersFromBoard returns empty list for wrong board id`() {
        val sut = db.getUsersFromBoard(100)
        assertEquals(0, sut.size)
        assertEquals(emptyList(), sut)
    }

    @Test
    fun `tokenToId returns the correct id given the token`() {
        val tokenUser1 = "160ee838-150b-4ca1-a2ff-2e964383c315" // User1.token
        val tokenUser3 = "658baaa9-4035-415e-9674-6957704600ba" // User3.token

        val sut = db.tokenToId(tokenUser1)
        val sut2 = db.tokenToId(tokenUser3)

        assertEquals(1, sut)
        assertEquals(3, sut2)
    }

    @Test
    fun `tokenToId throws UserNotFoundException given an non existent user token`() {
        val msg = assertFailsWith<UserNotFoundException> {
            db.tokenToId("${UUID.randomUUID()}")
        }

        assertEquals(
            UserNotFoundException.description,
            msg.description

        )
    }
}
