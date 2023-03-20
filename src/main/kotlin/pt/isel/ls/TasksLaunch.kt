package pt.isel.ls

import pt.isel.ls.database.memory.TasksDataMem

const val PORT_ENV = "PORT"
const val DEFAULT_PORT = 8080
const val JDBC_DATABASE_URL_ENV = "JDBC_DATABASE_URL"

/**
 * Sports API application's entry point.
 */
fun main() {
    val jdbcDatabaseURL: String? = System.getenv(JDBC_DATABASE_URL_ENV)
    val port = System.getenv(PORT_ENV)?.toIntOrNull() ?: DEFAULT_PORT
    /*
    val database: AppDatabase = if (jdbcDatabaseURL != null)
        TasksDataPG(jdbcDatabaseURL)
    else
        TasksDataMem()
        */
    val database = TasksDataMem()

    TasksServer(port, database).also { it.start() }
}
