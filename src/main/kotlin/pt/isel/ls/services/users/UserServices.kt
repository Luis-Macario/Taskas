package pt.isel.ls.services.users

import pt.isel.ls.database.AppDatabase
import pt.isel.ls.database.memory.EmailAlreadyExistsException
import pt.isel.ls.domain.SimpleBoard
import pt.isel.ls.domain.User
import pt.isel.ls.domain.checkUserCredentials
import pt.isel.ls.services.utils.checkToken
import pt.isel.ls.services.utils.exceptions.IllegalUserAccessException
import java.util.UUID

class UserServices(private val database: AppDatabase) {
    /**
     * Create a new user
     *
     * @param name user's name
     * @param email user's email
     *
     * @return user object
     */
    fun createUser(name: String, email: String): User {
        checkUserCredentials(name, email)
        if (database.checkEmailAlreadyExists(email)) throw EmailAlreadyExistsException
        val token = UUID.randomUUID().toString()
        val id = database.createUser(token, name, email)

        return User(id, name, email, token)
    }

    /**
     * Get the details of a user
     *
     * @param uid user's unique identifier
     *
     * @return user object
     */
    fun getUser(uid: Int): User = database.getUserDetails(uid)

    /**
     * Get all boards from a given user
     *
     * @param token user's token
     * @param uid the unique user identifier
     *
     * @return List of board objects
     */
    fun getBoardsFromUser(token: String, uid: Int): List<SimpleBoard> {
        checkToken(token)
        val user = database.getUserDetails(uid)
        if (user.token != token) throw IllegalUserAccessException

        return database.getBoardsFromUser(uid)
    }
}
