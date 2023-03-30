package pt.isel.ls.database.postgres

import org.postgresql.ds.PGSimpleDataSource
import pt.isel.ls.database.*
import pt.isel.ls.database.sql.TasksDataPostgres
import kotlin.test.*

class UserTests {
    private val dataSource = PGSimpleDataSource().apply {
        this.setUrl(System.getenv("JDBC_DATABASE_URL"))
    }

    @BeforeTest
    fun cleanAndAddData() {
        dropTableAndAddData(dataSource)
    }

    @Test
    fun `createUser creates User successfully`() {
        dataSource.connection.use { connection ->
            connection.autoCommit = false

            val db = TasksDataPostgres()
            val newUser = db.createUser(
                "b8d9ac03-b1cf-4e01-b567-51762b98ec5c",
                "Francisco Ricardo Luis",
                "gods@alunos.isel.pt"
            )

            val stm = connection.prepareStatement(
                """
                SELECT * FROM users where id = ?
                """.trimIndent()
            )
            stm.setInt(1, newUser.id)

            val rs = stm.executeQuery()
            assertTrue(rs.next())
            assertEquals(newUser.id, rs.getInt("id"))
            assertEquals(newUser.name, rs.getString("name"))
            assertEquals(newUser.email, rs.getString("email"))
            assertEquals(newUser.token, rs.getString("token"))
        }
    }

    @Test
    fun `getUser returns the User() successfully after beingCreated`() {
        dataSource.connection.use { connection ->
            connection.autoCommit = false

            val db = TasksDataPostgres()
            val newUser = db.createUser(
                "b8d9ac03-b1cf-4e01-b567-51762b98ec5c",
                "Francisco Ricardo Luis",
                "gods@alunos.isel.pt"
            )

            val getUser = db.getUserDetails(newUser.id)

            assertEquals(newUser.id, getUser.id)
            assertEquals(newUser.name, getUser.name)
            assertEquals(newUser.email, getUser.email)
            assertEquals(newUser.token, getUser.token)
        }
    }
}
