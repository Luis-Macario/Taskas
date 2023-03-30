package pt.isel.ls.database.postgres

import org.postgresql.ds.PGSimpleDataSource
import java.io.File

fun dropTableAndAddData(dataSource: PGSimpleDataSource) {
    dataSource.connection.use {
        it.createStatement().execute(
            File("src/main/kotlin/pt/isel/ls/database/sql/createSchema.sql")
                .readText()
        )

        it.createStatement().execute(
            File("src/main/kotlin/pt/isel/ls/database/sql/addData.sql")
                .readText()
        )
    }
}
