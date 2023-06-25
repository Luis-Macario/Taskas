package pt.isel.ls.services.users

import pt.isel.ls.database.AppDatabase
import pt.isel.ls.database.memory.EmailAlreadyExistsException
import pt.isel.ls.domain.SimpleBoard
import pt.isel.ls.domain.User
import pt.isel.ls.domain.checkUserCredentials
import pt.isel.ls.domain.hashPassword
import pt.isel.ls.services.utils.LIMIT_DEFAULT
import pt.isel.ls.services.utils.SKIP_DEFAULT
import pt.isel.ls.services.utils.checkToken
import pt.isel.ls.services.utils.exceptions.IllegalUserAccessException
import pt.isel.ls.services.utils.exceptions.InvalidUserCredentialsException
import java.util.UUID

class UserServices(private val database: AppDatabase) {
    /**
     * Create a new user
     *
     * @param name user's name
     * @param email user's email
     * @param password user's password
     *
     * @return user object
     */
    fun createUser(name: String, email: String, password: String): User {
        checkUserCredentials(name, email)
        if (database.checkEmailAlreadyExists(email)) throw EmailAlreadyExistsException
        val token = UUID.randomUUID().toString()
        val hashedPassword = hashPassword(password)
        val id = database.createUser(token, name, email, hashedPassword)

        return User(id, name, email, token, hashedPassword)
    }

    fun loginUser(email: String, password: String): User {
        val user = database.loginUser(email)
        if (hashPassword(password) != user.password) throw InvalidUserCredentialsException

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
    fun getBoardsFromUser(token: String, uid: Int, skip: Int? = null, limit: Int? = null): List<SimpleBoard> {
        checkToken(token)
        val user = database.getUserDetails(uid)
        if (user.token != token) throw IllegalUserAccessException

        return database.getBoardsFromUser(uid, skip ?: SKIP_DEFAULT, limit ?: LIMIT_DEFAULT)
    }

    fun searchBoardsFromUser(
        token: String,
        uid: Int,
        searchQuery: String?,
        skip: Int? = null,
        limit: Int? = null
    ): List<SimpleBoard> {
        checkToken(token)
        val user = database.getUserDetails(uid)
        if (user.token != token) throw IllegalUserAccessException

        return if (searchQuery == null) {
            database.getBoardsFromUser(uid, skip ?: SKIP_DEFAULT, limit ?: LIMIT_DEFAULT)
        } else {
            database.searchBoardsFromUser(uid, skip ?: SKIP_DEFAULT, limit ?: LIMIT_DEFAULT, searchQuery)
        }
    }
}
