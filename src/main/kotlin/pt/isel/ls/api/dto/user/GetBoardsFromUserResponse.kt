package pt.isel.ls.api.dto.user

import kotlinx.serialization.Serializable
import pt.isel.ls.api.dto.board.SimpleBoardDTO

@Serializable
data class GetBoardsFromUserResponse(val boards: List<SimpleBoardDTO>)
