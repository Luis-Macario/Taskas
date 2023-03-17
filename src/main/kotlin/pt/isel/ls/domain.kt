package pt.isel.ls

import java.sql.*
import kotlin.collections.List


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
    val bId: List<Int>,
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
data class Board(
    var uid: List<Int>,
    val id: Int,
    val lId: List<Int>,
    val name: String,
    val description: String = "",
)

/**
 * List representation
 *
 * @property bid id of the board that created the list
 * @property id user's unique identifier
 * @property name user's unique identifier
 */
data class List(
    val bid: List<Int>,
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
data class Card(
    val uid: Int,
    val bid: Int,
    var lid: Int? = null,
    val id: Int,
    val name: String,
    val description: String = "",
    val initDate: Date,
    val finishDate: Date
)

