package pt.isel.ls.domain

import pt.isel.ls.utils.MAX_DATE
import pt.isel.ls.utils.validDescription
import pt.isel.ls.utils.validId
import java.sql.Date

/**
 * Card representation
 * @property bid id of the board that created the card
 * @property lid id of the list that contains the card, null by default
 * @property id card's unique identifier
 * @property name card's name
 * @property description task description, empty string by default
 * @property initDate date the task was started
 * @property finishDate date the task was finished
 */
data class Card(
    val id: Int,
    val bid: Int,
    val lid: Int? = null,
    val name: String,
    val description: String = "",
    val initDate: Date,
    val finishDate: Date = Date.valueOf(MAX_DATE)
) {
    companion object {
        const val MAX_NAME_LENGTH = 100
        const val MIN_NAME_LENGTH = 10

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
        // require(validId(uid)) { "Invalid user id: $uid" }
        require(validId(bid)) { "Invalid board id: $bid" }
        if (lid != null) require(validId(lid)) { "Invalid list id: $lid" }
        require(validName(name)) { "Invalid card name: $name" }
        require(validDescription(description)) { "Invalid description: $description" }
        require(validDates(initDate, finishDate)) { "Invalid dates: $finishDate happens before $initDate" }
    }
}
