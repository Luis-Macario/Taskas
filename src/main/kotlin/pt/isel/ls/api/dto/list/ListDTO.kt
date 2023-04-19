package pt.isel.ls.api.dto.list

import kotlinx.serialization.Serializable
import pt.isel.ls.api.dto.card.CardDTO
import pt.isel.ls.api.dto.card.toDTO
import pt.isel.ls.domain.TaskList

@Serializable
data class ListDTO(val id: Int, val bid: Int, val name: String, val cards: List<CardDTO>)

fun TaskList.toDTO() = ListDTO(this.id, this.bid, this.name, this.cards.map { it.toDTO() })
