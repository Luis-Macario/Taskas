package pt.isel.ls.services

import org.junit.Test
import pt.isel.ls.database.memory.TasksDataMem
import pt.isel.ls.domain.Board
import pt.isel.ls.services.boards.BoardServices
import pt.isel.ls.services.users.UserServices
import pt.isel.ls.utils.exceptions.IllegalUserAccessException
import java.util.UUID
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class UserServicesTests {
    private val database = TasksDataMem()
    private val services = UserServices(database)
    private val bServices = BoardServices(database)

    // Create new User
    @Test
    fun `Should be able to get student from id after creation`() {
        val user = services.createUser("Service Test", "ServiceTest@isel.pt")

        assertEquals(user, services.getUser(user.id))
    }

    @Test
    fun `Creating new users should increment each id by 1`() {
        val user1 = services.createUser("Francisco Medeiros", "RotulaDoChines@isel.pt")
        val user2 = services.createUser("Luis Macário", "HonorStudent@isel.pt")
        val user3 = services.createUser("Ricardo Pinto", "MegaTwix@isel.pt")

        assertEquals(0, user1.id) // first user created has id 0
        assertEquals(1, user2.id) // second user created has id 1
        assertEquals(2, user3.id) // third user created has id 2
    }

    // getUser
    @Test
    fun `Should be able to get student from id`() {
        val user1 = services.createUser("Francisco Medeiros", "RotulaDoChines@isel.pt")
        val user2 = services.createUser("Luis Macário", "HonorStudent@isel.pt")
        val user3 = services.createUser("Ricardo Pinto", "Batman@isel.pt")

        assertEquals(user1, services.getUser(0))
        assertEquals(user2, services.getUser(1))
        assertEquals(user3, services.getUser(2))
    }

    // getBoardsFromUser
    @Test
    fun `Getting board using valid token should return all boards for given user`() {
        val user = services.createUser("Francisco Medeiros", "RotulaDoChines@isel.pt")
        bServices.createBoard(user.token, "Projetao", "Mini descriçao")
        bServices.createBoard(user.token, "Projetinho", "Mega descriçao")

        val boards = services.getBoardsFromUser(user.token, user.id)

        assertEquals(Board(0, "Projetao", "Mini descriçao"), boards[0])
        assertEquals(Board(1, "Projetinho", "Mega descriçao"), boards[1])
    }

    @Test
    fun `Getting user's boards with an invalid token should throw IllegalUserAccessException`() {
        val user = services.createUser(
            "Francisco Medeiros",
            "RotulaDoChines@isel.pt"
        )
        val invalidToken = UUID.randomUUID().toString()

        assertFailsWith<IllegalUserAccessException> { services.getBoardsFromUser(invalidToken, user.id) }
    }
}
