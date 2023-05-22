package pt.isel.ls.services.users

import pt.isel.ls.database.AppDatabase
import pt.isel.ls.database.memory.EmailAlreadyExistsException
import pt.isel.ls.domain.SimpleBoard
import pt.isel.ls.domain.User
import pt.isel.ls.domain.checkUserCredentials
import pt.isel.ls.domain.hashPassword
import pt.isel.ls.services.utils.checkToken
import pt.isel.ls.services.utils.exceptions.IllegalUserAccessException
import pt.isel.ls.services.utils.exceptions.InvalidUserCredentialsException
import java.util.*

class UserServices(private val database: AppDatabase) {
    /**
     * Create a new user
     *
     * @param name user's name
     * @param email user's email
     *
     * @return user object
     */
    fun createUser(name: String, email: String, password: String): User {
        checkUserCredentials(name, email)
        if (database.checkEmailAlreadyExists(email)) throw EmailAlreadyExistsException
        val token = UUID.randomUUID().toString()
        val hashedPassword = hashPassword(password)
        val id = database.createUser(token, name, email, hashedPassword)

        return User(id, name, email, token)
    }

    fun loginUser(email: String, password: String): User {
        println("login : $email $password")
        val user = database.loginUser(email)
        if (hashPassword(password) != user.password) throw InvalidUserCredentialsException

        return user.user
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

        val boards = database.getBoardsFromUser(uid)
        val droppedBoards = if (skip != null) boards.drop(skip) else boards
        return if (limit != null) droppedBoards.take(limit) else droppedBoards
        // TODO: MOVE PAGING TO SQL
    }

    fun searchBoardsFromUser(
        token: String,
        uid: Int,
        searchQuery: String?,
        skip: Int? = null,
        limit: Int? = null
    ): List<SimpleBoard> {
        val boards = getBoardsFromUser(token, uid, skip, limit)
        return if (searchQuery == null) {
            boards
        } else {
            boards.filter { it.name.lowercase().contains(searchQuery.lowercase()) }
        }
    }
}
