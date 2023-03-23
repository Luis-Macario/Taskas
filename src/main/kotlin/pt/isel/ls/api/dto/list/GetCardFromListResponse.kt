package pt.isel.ls.api.dto.list

import kotlinx.serialization.Serializable
import pt.isel.ls.api.dto.card.CardDTO

@Serializable
data class GetCardFromListResponse(val cards: List<CardDTO>)
