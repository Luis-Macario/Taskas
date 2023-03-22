package pt.isel.ls.domain

abstract class TaskError : Exception() {
    abstract val code: Int
    abstract val description: String
}
