package pt.isel.ls.api.dto.board

import kotlinx.serialization.Serializable
import pt.isel.ls.api.dto.list.SimpleListDTO

@Serializable
data class GetListsFromBoardResponse(val lists: List<SimpleListDTO>)
