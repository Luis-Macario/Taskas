package pt.isel.ls

import pt.isel.ls.database.AppDatabase
import pt.isel.ls.database.memory.TasksDataMem
import pt.isel.ls.database.sql.TasksDataPostgres

const val PORT_ENV = "PORT"
const val DEFAULT_PORT = 8080
const val JDBC_DATABASE_URL_ENV = "JDBC_DATABASE_URL"

/**
 * Tasks API application's entry point.
 */
fun main() {
    val jdbcDatabaseURL: String? = System.getenv(JDBC_DATABASE_URL_ENV)
    val port = System.getenv(PORT_ENV)?.toIntOrNull() ?: DEFAULT_PORT
    val database: AppDatabase = if (jdbcDatabaseURL != null) {
        TasksDataPostgres(jdbcDatabaseURL)
    } else {
        TasksDataMem()
    }

    TasksServer(port, database).also {
        println("Starting the server. Listening at port $port.\nDatabase used is ${database::class.simpleName}")
        it.start()
    }
}
