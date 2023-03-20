package pt.isel.ls.api.dto.board

import kotlinx.serialization.Serializable

@Serializable
data class BoardDTO(
    val id: Int,
    val name: String,
    val description: String
)
