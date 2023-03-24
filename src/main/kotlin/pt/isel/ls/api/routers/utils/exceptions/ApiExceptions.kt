package pt.isel.ls.api.routers.utils.exceptions

import pt.isel.ls.domain.TaskException

object InvalidUserIDException : TaskException() {
    override val code = 9000 // TODO: Figure out what codes to use
    override val description = "Invalid User ID provided in the path"
}

object InvalidBoardIDException : TaskException() {
    override val code = 9000 // TODO: Figure out what codes to use
    override val description = "Invalid Board ID provided in the path"
}

object InvalidListIDException : TaskException() {
    override val code = 9000 // TODO: Figure out what codes to use
    override val description = "Invalid List ID provided in the path"
}

object InvalidCardIDException : TaskException() {
    override val code = 9000 // TODO: Figure out what codes to use
    override val description = "Invalid Card ID provided in the path"
}

object NoAuthenticationException : TaskException() {
    override val code = 9000 // TODO: Figure out what codes to use
    override val description = "This operation requires the user to be authenticated"
}

object InvalidBodyException : TaskException() {
    override val code = 9000 // TODO: Figure out what codes to use
    override val description = "Invalid Request Body"
}
