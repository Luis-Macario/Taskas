package pt.isel.ls.unit.database.postgres

import org.postgresql.ds.PGSimpleDataSource
import java.io.File

fun dropTableAndAddData(dataSource: PGSimpleDataSource) {
    dataSource.connection.use {
        it.createStatement().execute(
            File("src/main/sql/createSchema.sql")
                .readText()
        )

        it.createStatement().execute(
            File("src/main/sql/addData.sql")
                .readText()
        )
    }
}
