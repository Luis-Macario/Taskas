package pt.isel.ls.api.dto.card

import kotlinx.serialization.Serializable

@Serializable
data class CreateCardRequest(val listID: Int, val name: String, val description: String, val dueDate: String?)
