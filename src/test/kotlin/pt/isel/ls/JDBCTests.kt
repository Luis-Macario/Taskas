package pt.isel.ls

import kotlin.test.assertTrue
import org.junit.Test
import org.postgresql.ds.PGSimpleDataSource
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class JDBCTests {
    val dataSource = PGSimpleDataSource().apply {
        this.setURL(System.getenv("JDBC_DATABASE_URL"))
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

        }
    }

    @Test
    fun `Update data from DB(INSERT)`() {

        dataSource.connection.apply {
            this.autoCommit = false
        }.use {
            it.prepareStatement("insert into students(course, number, name) values (1,47671, 'Luís');")
                .execute()
            it.prepareStatement("insert into students(course, number, name) values (1,47673, 'Ricky');")
                .execute()
            it.prepareStatement("insert into students(course, number, name) values (1,46631, 'Chiquinho');")
                .execute()


            val rs = it.prepareStatement("select * from students where name !='Alice'").executeQuery()
            rs.next()

            assertEquals("Luís", rs.getString("name"))
            rs.next()
            assertEquals("Ricky", rs.getString("name"))
            rs.next()
            assertEquals("Chiquinho", rs.getString("name"))

            it.rollback()
        }

    }
}