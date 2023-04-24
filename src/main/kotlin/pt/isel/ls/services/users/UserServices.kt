package pt.isel.ls.services.users

import pt.isel.ls.database.AppDatabase
import pt.isel.ls.database.memory.EmailAlreadyExistsException
import pt.isel.ls.domain.SimpleBoard
import pt.isel.ls.domain.User
import pt.isel.ls.domain.checkUserCredentials
import pt.isel.ls.services.utils.checkToken
import pt.isel.ls.services.utils.exceptions.IllegalUserAccessException
import java.sql.SQLException
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
        try {
            database.checkEmailAlreadyExists(email)
        } catch (sqlE: SQLException) {
            throw EmailAlreadyExistsException
        }

        val id = database.getNextId()
        val token = UUID.randomUUID().toString()
        val user = User(id, name, email, token)

        try {
            database.createUser(user)
        }catch (sqlE : SQLException){
            throw sqlE
        }
        return user
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
