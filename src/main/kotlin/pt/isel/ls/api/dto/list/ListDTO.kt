package pt.isel.ls.api.dto.list

import kotlinx.serialization.Serializable
import pt.isel.ls.domain.TaskList

@Serializable
data class ListDTO(val id: Int, val name: String)

fun TaskList.toDTO() = ListDTO(this.id, this.name)
