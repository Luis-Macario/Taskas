package pt.isel.ls.api.dto

import kotlinx.serialization.Serializable

@Serializable
data class DeletedResponse(val entityID: Int) {
    val message = "Successfully deleted the entity"
}
