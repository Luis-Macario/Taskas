package pt.isel.ls.api.dto.board

import kotlinx.serialization.Serializable
import pt.isel.ls.api.dto.list.ListDTO

@Serializable
data class GetBoardListsResponse(val lists: List<ListDTO>)
