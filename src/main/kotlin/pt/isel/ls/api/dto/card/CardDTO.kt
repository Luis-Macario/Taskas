package pt.isel.ls.api.dto.card

import kotlinx.serialization.Serializable

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
