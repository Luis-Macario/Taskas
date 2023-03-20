package pt.isel.ls.services.users

import pt.isel.ls.database.AppDatabase
import pt.isel.ls.services.CreateUserResponse

class UserServices (private val database: AppDatabase) {
    /**
     * Create a new user
     *
     * @param name user's name
     * @param email user's email
     *
     * @return user's token and id
     */
    fun createUser(name: String, email: String): CreateUserResponse {
        val createdUser = database.createUser(name, email)
        return CreateUserResponse(createdUser.token, createdUser.id)
    }

    /**
     * Get the details of a user
     *
     * @param uid user's unique identifier
     *
     * @return user object
     */
    fun getUser(uid: Int)/*: User*/ = database.getUserDetails(uid)

    /**
     * Get all users
     *
     * @return List of user objects
     */
    //fun getAllUsers(): List<User> = database.getAllUsers()
}