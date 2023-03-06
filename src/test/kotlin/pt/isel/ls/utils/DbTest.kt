package pt.isel.ls.utils

import org.postgresql.ds.PGSimpleDataSource
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class DbTest {
    val dataSource = PGSimpleDataSource().apply {
        this.setURL(System.getenv("JDBC_DATABASE_URL"))
    }.connection.apply {
        this.autoCommit = false
    }

    @Test
    fun `insert new student `(){
        dataSource.use {
            val stm = it.prepareStatement("insert into students(course, number, name) values (1, 46634, 'Miguel');").execute()
            val rs = it.prepareStatement("select * from students where number = 46634").executeQuery()

            assertTrue(rs.next())
            assertEquals("Miguel", rs.getString("name") )

            dataSource.rollback()
            dataSource.close()
        }
    }

    @Test
    fun `update a student`(){
        dataSource.use {
            it.prepareStatement("insert into students(course, number, name) values (1, 47784, 'Luis');").execute()
            it.prepareStatement("update students set name = 'Francisco' where number = 47784").execute()

            val rs = it.prepareStatement("select * from students where number = 47784").executeQuery()

            assertTrue(rs.next())
            assertEquals("Francisco", rs.getString("name"))

            dataSource.rollback()
            dataSource.close()
        }
    }
}