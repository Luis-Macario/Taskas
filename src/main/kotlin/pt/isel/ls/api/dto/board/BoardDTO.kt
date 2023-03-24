package pt.isel.ls.api.dto.board

import kotlinx.serialization.Serializable
import pt.isel.ls.domain.Board

@Serializable
data class BoardDTO(
    val id: Int,
    val name: String,
    val description: String
)

fun Board.toDTO() = BoardDTO(id, name, description)
