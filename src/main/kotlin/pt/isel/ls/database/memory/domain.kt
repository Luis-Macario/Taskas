package pt.isel.ls.database.memory

import pt.isel.ls.utils.*
import java.sql.*


/**
 * User representation
 *
 * @property id user's unique identifier
 * @property name name of the user
 * @property email email of the user
 * @property token bearer token of the user
 */
data class User(
    val id: Int,
    val name: String,
    val email: String,
    val token: String
) {
    companion object{
        private const val EMAIL_REGEX = "^(.+)@(.+)$"
        private const val MAX_NAME_LENGTH = 50
        private const val MIN_NAME_LENGTH = 3

        /**
         * Checks whether an email is valid or not
         *
         * @param email String with email to check
         *
         * @return true if valid, false if not
         */
        fun validEmail(email: String): Boolean = email.matches(EMAIL_REGEX.toRegex())

        /**
         * Checks whether a username is valid or not
         *
         * @param name String with name of the user
         *
         * @return true if the name is valid, false if not
         */
        fun validName(name: String): Boolean = (name.length in MIN_NAME_LENGTH..MAX_NAME_LENGTH)
    }
    init {
        require(validName(name)) { "Invalid username: $name" }
        require(validEmail(email)) { "Invalid email: $email" }
        require(validId(id)) { "Invalid user id: $id" }
    }
}

/**
 * Board representation
 *
 * @property id board's unique identifier
 * @property description a small description of the project, empty string by default
 * @property name project's name
 */
data class Board(
    val id: Int,
    val name: String,
    val description: String = "",
) {
    companion object{
        private const val MAX_NAME_LENGTH = 100
        private const val MIN_NAME_LENGTH = 20

        /**
         * Checks whether a board name is valid or not
         *
         * @param name String with name of the user
         *
         * @return true if the name is valid, false if not
         */
        fun validName(name: String): Boolean = (name.length in MIN_NAME_LENGTH..MAX_NAME_LENGTH)
    }
    init {
        require(validName(name)) { "Invalid board username: $name" }
        require(validDescription(description)) { "Invalid board description: $description" }
        require(validId(id)) { "Invalid board id: $id" }
    }
}

/**
 * User - Board representation
 *
 * @property uId id of the User
 * @property bId id of the Board
 */
data class UserBoard(
    val uId: Int,
    val bId: Int,
){
    init {
        require(validId(uId)){"Invalid userid: $uId"}
        require(validId(uId)){"Invalid board id: $bId"}
    }
}

/**
 * List representation
 *
 * @property bid id of the Board that created the list
 * @property id List's unique identifier
 * @property name List's unique identifier
 */
data class TaskList(
    val bid: Int,
    val id: Int,
    val name: String
) {
    companion object{
        private const val MAX_NAME_LENGTH = 100
        private const val MIN_NAME_LENGTH = 10

        /**
         * Checks whether a task name is valid or not
         *
         * @param name String with name of the user
         *
         * @return true if the name is valid, false if not
         */
        fun validName(name: String): Boolean = (name.length in MIN_NAME_LENGTH..MAX_NAME_LENGTH)
    }
    init {
        require(validId(id)) { "Invalid task id: $id" }
        //require(validId(bid)) { "Invalid board id: $bid" }
        require(validName(name)) { "Invalid task name: $name" }
    }
}

/**
 * Card representation
 * @property uid id of the user that created the board that contains the card
 * @property bid id of the board that created the card
 * @property lid id of the list that contains the card, null by default
 * @property id card's unique identifier
 * @property name card's name
 * @property description task description, empty string by default
 * @property initDate date the task was started
 * @property finishDate date the task was finished
 */
data class Card(
    val uid: Int, //TODO: ??
    val bid: Int,
    val lid: Int? = null,
    val id: Int,
    val name: String,
    val description: String = "",
    val initDate: Date,
    val finishDate: Date = Date.valueOf("9999-9")
) {
    companion object{
        private const val MAX_NAME_LENGTH = 100
        private const val MIN_NAME_LENGTH = 10

        /**
         * Checks whether a card name is valid or not
         *
         * @param name String with name of the user
         *
         * @return true if the name is valid, false if not
         */
        fun validName(name: String): Boolean = (name.length in MIN_NAME_LENGTH..MAX_NAME_LENGTH)

        /**
         * Checks occurs finish date occurs after initial date
         *
         * @param initDate Date to start the task
         * @param finishDate Date to finish the task
         *
         * @return true if the name is valid, false if not
         */
        fun validDates(initDate: Date, finishDate: Date): Boolean = finishDate > initDate
    }
    init {
        require(validId(id)) { "Invalid card id: $id" }
        require(validId(uid)) { "Invalid user id: $uid" }
        require(validId(bid)) { "Invalid board id: $bid" }
        if (lid != null) require(validId(bid)) { "Invalid list id: $lid" }
        require(validName(name)) { "Invalid card name: $name" }
        require(validDescription(description)) { "Invalid description: $description" }
        require(validDates(initDate, finishDate)) { "Invalid dates: $finishDate happens before $initDate" }

    }
}