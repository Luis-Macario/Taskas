package pt.isel.ls.domain

abstract class TaskException : Exception() {
    abstract val code: Int
    abstract val description: String
}
