package pt.isel.ls.unit.services

import org.junit.jupiter.api.Test
import pt.isel.ls.database.memory.TasksDataMem
import pt.isel.ls.domain.SimpleBoard
import pt.isel.ls.services.boards.BoardServices
import pt.isel.ls.services.users.UserServices
import pt.isel.ls.services.utils.exceptions.IllegalUserAccessException
import java.util.UUID
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class UserServicesTests {
    private val database = TasksDataMem()
    private val services = UserServices(database)
    private val bServices = BoardServices(database)

    private val userA = services.createUser("Francisco Medeiros", "RotulaDoChines@isel.pt", "teste12345")
    private val userB = services.createUser("Luis Macário", "HonorStudent@isel.pt", "teste12345")
    private val userC = services.createUser("Ricardo Pinto", "MegaTwix@isel.pt", "teste12345")

    // Create new User
    @Test
    fun `Should be able to get student from id after creation`() {
        val user = userA

        assertEquals(user, database.users[0])
    }

    @Test
    fun `Creating new users should increment each id by 1`() {
        val user1 = userA
        val user2 = userB
        val user3 = userC

        assertEquals(0, user1.id)
        assertEquals(1, user2.id)
        assertEquals(2, user3.id)
    }

    // getUser
    @Test
    fun `Should be able to get student from id`() {
        val user0 = userA
        val user1 = userB
        val user2 = userC

        assertEquals(user0, database.getUserDetails(0))
        assertEquals(user1, database.getUserDetails(1))
        assertEquals(user2, database.getUserDetails(2))
    }

    // getBoardsFromUser
    @Test
    fun `Getting board using valid token should return all boards for given user`() {
        val user = userA
        bServices.createBoard(user.token, "Projetao", "Mini descriçao")
        bServices.createBoard(user.token, "Projetinho", "Mega descriçao")

        val boards = services.getBoardsFromUser(user.token, user.id)

        assertEquals(SimpleBoard(0, "Projetao", "Mini descriçao"), boards[0])
        assertEquals(SimpleBoard(1, "Projetinho", "Mega descriçao"), boards[1])
    }

    @Test
    fun `Getting user's boards with an invalid token should throw IllegalUserAccessException`() {
        val user = userA
        val invalidToken = UUID.randomUUID().toString()

        assertFailsWith<IllegalUserAccessException> { services.getBoardsFromUser(invalidToken, user.id) }
    }
}
