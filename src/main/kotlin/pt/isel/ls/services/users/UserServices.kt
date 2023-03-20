package pt.isel.ls.services.users

import pt.isel.ls.database.AppDatabase
import pt.isel.ls.domain.User

class UserServices(private val database: AppDatabase) {
    /**
     * Create a new user
     *
     * @param name user's name
     * @param email user's email
     *
     * @return user's token and id
     */
    fun createUser(name: String, email: String): User = database.createUser(name, email)

    /**
     * Get the details of a user
     *
     * @param uid user's unique identifier
     *
     * @return user object
     */
    fun getUser(uid: Int): User = database.getUserDetails(uid)

    /**
     * Get all users
     *
     * @return List of user objects
     */
    fun getBoardUsers(bid: Int): List<User> = database.getUsersFromBoard(bid)
}
