package pt.isel.ls.api.routers.utils.exceptions

import pt.isel.ls.domain.TaskException

sealed class ApiException : TaskException()

object InvalidUserIDException : ApiException() {
    override val code = 7000
    override val description = "Invalid User ID provided in the path"
}

object InvalidBoardIDException : ApiException() {
    override val code = 7001
    override val description = "Invalid Board ID provided in the path"
}

object InvalidListIDException : ApiException() {
    override val code = 7002
    override val description = "Invalid List ID provided in the path"
}

object InvalidCardIDException : ApiException() {
    override val code = 7003
    override val description = "Invalid Card ID provided in the path"
}

object NoAuthenticationException : ApiException() {
    override val code = 7004
    override val description = "This operation requires the user to be authenticated"
}

object InvalidBodyException : ApiException() {
    override val code = 7005
    override val description = "Invalid Request Body"
}

object InvalidQueryException : ApiException() {
    override val code = 7006
    override val description = "Invalid Request Query"
}

object InvalidAuthHeaderException : ApiException() {
    override val code = 7007
    override val description = "Authorization header is not valid"
}
