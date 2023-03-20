package pt.isel.ls.api.dto.board

import kotlinx.serialization.Serializable

@Serializable
data class CreateBoardRequest(val name: String, val description: String)
