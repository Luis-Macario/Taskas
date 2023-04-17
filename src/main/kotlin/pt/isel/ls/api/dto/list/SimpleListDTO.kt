package pt.isel.ls.api.dto.list

import kotlinx.serialization.Serializable
import pt.isel.ls.domain.SimpleList

@Serializable
data class SimpleListDTO(val id: Int, val name: String)

fun SimpleList.toDTO() = SimpleListDTO(this.id, this.name)
