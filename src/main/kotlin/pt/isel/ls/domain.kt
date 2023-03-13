package pt.isel.ls

import java.sql.*

class domain {

    /**
     * User representation
     *
     * @property id user's unique identifier
     * @property name name of the user
     * @property email email of the user
     * @property token bearer token of the user
     */
    data class user(
        val id: Int,
        val name: String,
        val email: String,
        val token: String
    )

    /**
     * Board representation
     *
     * @property uid id of the user that created the board
     * @property id boards' unique identifier
     * @property description a small description of the project, empty string by default
     * @property name project's name
     */
    data class board(
        val uid: Int,
        val id: Int,
        val description: String = "",
        val name: String
    )

    /**
     * List representation
     *
     * @property bid id of the board that created the list
     * @property id user's unique identifier
     * @property name user's unique identifier
     */
    data class list(
        var bid: Int,
        val id: Int,
        val name: String
    )

    /**
     * Card representation
     * @property uid id of the user that created the board that contains the card
     * @property bid id of the board that created the card
     * @property lid id of the list that contains the card
     * @property id card's unique identifier
     * @property name card's name
     * @property description task description, empty string by default
     * @property initDate date the task was started
     * @property finishDate date the task was finished
     */
    data class card(
        val uid: Int,
        val bid: Int,
        val lid: Int? = null,
        val id: Int,
        val name: String,
        val description: String = "",
        val initDate: Date,
        val finishDate: Date
    )

}