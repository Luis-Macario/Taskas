package pt.isel.ls.api.dto.board

import kotlinx.serialization.Serializable
import pt.isel.ls.domain.SimpleBoard

@Serializable
data class SimpleBoardDTO(
    val id: Int,
    val name: String,
    val description: String
)

fun SimpleBoard.toDTO() = SimpleBoardDTO(id, name, description)
