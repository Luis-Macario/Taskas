package pt.isel.ls.api.routers.utils.exceptions

import pt.isel.ls.domain.TaskException

sealed class ApiException : TaskException()

object InvalidUserIDException : ApiException() {
    override val code = 9000 // TODO: Figure out what codes to use
    override val description = "Invalid User ID provided in the path"
}

object InvalidBoardIDException : ApiException() {
    override val code = 9000 // TODO: Figure out what codes to use
    override val description = "Invalid Board ID provided in the path"
}

object InvalidListIDException : ApiException() {
    override val code = 9000 // TODO: Figure out what codes to use
    override val description = "Invalid List ID provided in the path"
}

object InvalidCardIDException : ApiException() {
    override val code = 9000 // TODO: Figure out what codes to use
    override val description = "Invalid Card ID provided in the path"
}

object NoAuthenticationException : ApiException() {
    override val code = 9000 // TODO: Figure out what codes to use
    override val description = "This operation requires the user to be authenticated"
}

object InvalidBodyException : ApiException() {
    override val code = 9000 // TODO: Figure out what codes to use
    override val description = "Invalid Request Body"
}

/*object InvalidQuerySkipException : ApiException() {
    override val code = 9000 // TODO: Figure out what codes to use
    override val description = "Invalid Request Query - Skip"
}

object InvalidQueryLimitException : ApiException() {
    override val code = 9000 // TODO: Figure out what codes to use
    override val description = "Invalid Request Query - Limit"
}*/

object InvalidAuthHeaderException : ApiException() {
    override val code = 9000 // TODO: Figure out what code to use
    override val description = "Authorization header is not valid"
}
