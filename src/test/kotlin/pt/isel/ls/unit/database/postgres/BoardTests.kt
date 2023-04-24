package pt.isel.ls.unit.database.postgres

import org.postgresql.ds.PGSimpleDataSource
import pt.isel.ls.database.memory.BoardNotFoundException
import pt.isel.ls.database.sql.TasksDataPostgres
import pt.isel.ls.domain.toSimpleBoard
import java.sql.SQLException
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class BoardTests {

    private val url = System.getenv("JDBC_DATABASE_URL")

    private val dataSource = PGSimpleDataSource().apply {
        this.setUrl(url)
    }

    @BeforeTest
    fun cleanAndAddData() {
        dropTableAndAddData(dataSource)
    }

    @Test
    fun `createBoard creates a object board correctly`() {
        val db = TasksDataPostgres(url)
        val boardName = "TODO Test Para Chess App"
        val boardDescription = "ISEL Project"
        val newBoard =
            db.createBoard(
                uid = 1,
                name = boardName,
                description = boardDescription
            )

        assertEquals(3, newBoard.id)
        assertEquals(boardName, newBoard.name)
        assertEquals(boardDescription, newBoard.description)

        dataSource.connection.use {
            val stm = it.prepareStatement(
                """
                SELECT name, description FROM boards WHERE  id = ?
                """.trimIndent()
            )
            stm.setInt(1, newBoard.id)

            val rs = stm.executeQuery()
            assertTrue(rs.next())
            assertEquals(boardName, rs.getString("name"))
            assertEquals(boardDescription, rs.getString("description"))
        }
    }

    @Test
    fun `create board throws BoardNameAlreadyExistsException when creating a board with a repeated name`() {
        val db = TasksDataPostgres(url)
        val name = "TODO Test Para Chess App"

        db.createBoard(
            uid = 1, // User(1,Francisco Medeiros,a46631@alunos.isel.pt,160ee838-150b-4ca1-a2ff-2e964383c315)
            name = name,
            description = "something to do"
        )

        assertFailsWith<SQLException> {
            db.createBoard(
                uid = 1, // User(1,Francisco Medeiros,a46631@alunos.isel.pt,160ee838-150b-4ca1-a2ff-2e964383c315)
                name = name,
                description = "something to do"
            )
        }
    }

    @Test
    fun `create board throws SQLException when creating a board with a invalid uid`() {
        val db = TasksDataPostgres(url)
        val uInvalidId = -1

        assertFailsWith<SQLException> {
            db.createBoard(
                uid = uInvalidId,
                name = "TODO Test Para Chess App",
                description = "Some Description Board"
            )
        }
    }

    @Test
    fun `getBoard returns correct Board() after being created`() {
        val db = TasksDataPostgres(url)
        val newBoard =
            db.createBoard(
                uid = 1,
                name = "TODO Test Para Chess App",
                description = "ISEL Project"
            )
        val getBoard = db.getBoardDetails(newBoard.id)

        assertEquals(newBoard.id, getBoard.id)
        assertEquals(newBoard.name, getBoard.name)
        assertEquals(newBoard.description, getBoard.description)
    }

    @Test
    fun `getBoard throws BoardNotFoundException when used an invalid bid`() {
        val db = TasksDataPostgres(url)

        assertFailsWith<BoardNotFoundException> {
            db.getBoardDetails(-1)
        }
    }

    @Test
    fun `addUserToBoard successfully adds user to the board`() {
        val db = TasksDataPostgres(url)
        val newUser = db.createUser(
            "b8d9ac03-b1cf-4e01-b567-51762b98ec5c",
            "Francisco Ricardo Luis Gods",
            "gods@alunos.isel.pt"
        )
        val boardId = 1
        db.addUserToBoard(newUser.id, boardId)

        dataSource.connection.use {
            val stm = it.prepareStatement(
                """
                SELECT * FROM userboards WHERE bid = ? ORDER BY uid DESC LIMIT 1
                """.trimIndent()
            )

            stm.setInt(1, 1)
            val rs = stm.executeQuery()

            assertTrue(rs.next())
            assertEquals(newUser.id, rs.getInt("uid"))
            assertEquals(boardId, rs.getInt("bid"))
        }
    }

    @Test
    fun `getBoardsFromUser returns the correct Users`() {
        val db = TasksDataPostgres(url)
        val boardName = "TODO Test Para Chess App"
        val boardDescription = "ISEL Project"

        val newUser = db.createUser(
            "b8d9ac03-b1cf-4e01-b567-51762b98ec5c",
            "Francisco R",
            "gods1@alunos.isel.pt"
        )

        val newBoard = db.createBoard(newUser.id, boardName, boardDescription).toSimpleBoard()
        val newBoard2 = db.createBoard(newUser.id, boardName + 'a', boardDescription).toSimpleBoard()
        val newBoard3 = db.createBoard(newUser.id, boardName + 'b', boardDescription).toSimpleBoard()
        val newBoard4 = db.createBoard(newUser.id, boardName + 'c', boardDescription).toSimpleBoard()

        val boardList = db.getBoardsFromUser(newUser.id)

        assertEquals(4, boardList.size)
        assertEquals(newBoard, boardList[0])
        assertEquals(newBoard2, boardList[1])
        assertEquals(newBoard3, boardList[2])
        assertEquals(newBoard4, boardList[3])
    }
}
