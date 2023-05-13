package pt.isel.ls.api.dto.card

import kotlinx.serialization.Serializable
import pt.isel.ls.domain.Card

@Serializable
data class CardDTO(
    val id: Int,
    val name: String,
    val description: String,
    val initialDate: String,
    val dueDate: String,
    val listID: Int?,
    val boardID: Int
)

fun Card.toDTO() = CardDTO(id, name, description, initDate.toString(), finishDate.toString(), lid, bid)
