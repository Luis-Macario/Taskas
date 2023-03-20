package pt.isel.ls.api.dto

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(val code: Int, val name: String, val description: String)
