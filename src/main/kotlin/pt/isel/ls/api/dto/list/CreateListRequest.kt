package pt.isel.ls.api.dto.list

import kotlinx.serialization.Serializable

@Serializable
data class CreateListRequest(val boardID: Int, val name: String)
