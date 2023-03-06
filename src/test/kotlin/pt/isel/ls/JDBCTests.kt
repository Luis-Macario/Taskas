package pt.isel.ls

import org.postgresql.ds.PGSimpleDataSource
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class JDBCTests {

    private val dataSource = PGSimpleDataSource().apply {
        this.setUrl("jdbc:postgresql://localhost/postgres?user=postgres&password=superuser")
    }

    @Test
    fun `DELETE test`() {
        dataSource.connection.apply {
            this.autoCommit = false
        }.use {
            it.prepareStatement("INSERT INTO students(course, number, name) values (1, 8080, 'Test')")
            val rsA = it.prepareStatement("SELECT * FROM students").executeQuery()
            assertTrue(rsA.next())

            it.prepareStatement("DELETE FROM students").execute()
            val rsB = it.prepareStatement("SELECT * FROM students").executeQuery()

            assertFalse(rsB.next())

            it.rollback()
        }
    }
}