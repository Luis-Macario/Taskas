package pt.isel.ls.api

import pt.isel.ls.domain.TaskError

object InvalidUserID : TaskError() {
    override val code = 9000 // TODO: Figure out what codes to use
    override val description = "Invalid User ID provided in the path"
}

object InvalidBoardID : TaskError() {
    override val code = 9000 // TODO: Figure out what codes to use
    override val description = "Invalid Board ID provided in the path"
}

object InvalidListID : TaskError() {
    override val code = 9000 // TODO: Figure out what codes to use
    override val description = "Invalid List ID provided in the path"
}

object InvalidCardID : TaskError() {
    override val code = 9000 // TODO: Figure out what codes to use
    override val description = "Invalid Card ID provided in the path"
}
