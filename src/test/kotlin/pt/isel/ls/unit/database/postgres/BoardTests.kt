package pt.isel.ls.unit.database.postgres

import org.postgresql.ds.PGSimpleDataSource
import pt.isel.ls.database.sql.TasksDataPostgres
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
    private val db = TasksDataPostgres(url)
    private val tokenA = "7d444840-9dc0-11d1-b245-5ffdce74fad1"
    private val passwordA = "132513E5601D28F9DBDEBD2590514E171FEFEC9A6BE60417D79B8D626077C3FB"
    private val boardName = "TODO Test Para Chess App"
    private val boardDescription = "ISEL Project"

    @BeforeTest
    fun cleanAndAddData() {
        dropTableAndAddData(dataSource)
    }

    @Test
    fun `createBoard creates a object board correctly`() {
        val user = db.createUser(tokenA, "Teste RFL", "a45554@alunos.isel.pt", passwordA)
        val newBoard = db.createBoard(user, boardName, boardDescription)

        assertEquals(3, newBoard)

        dataSource.connection.use {
            val stm = it.prepareStatement(
                """
                SELECT name, description FROM boards WHERE  id = ?
                """.trimIndent()
            )
            stm.setInt(1, newBoard)

            val rs = stm.executeQuery()
            assertTrue(rs.next())
            assertEquals(boardName, rs.getString("name"))
            assertEquals(boardDescription, rs.getString("description"))
        }
    }

    @Test
    fun `create board throws SQLException when creating a board with a repeated name`() {
       val user = db.createUser(tokenA, "Teste RFL", "a45554@alunos.isel.pt", passwordA)
        db.createBoard(user, boardName, boardDescription)

        assertFailsWith<SQLException> {
            db.createBoard(user, boardName, boardDescription)
        }
    }

    @Test
    fun `create board throws SQLException when creating a board with a invalid uid`() {
        val uInvalidId = -1

        assertFailsWith<SQLException> {
            db.createBoard(
                uid = uInvalidId,
                name = boardName,
                description = boardDescription
            )
        }
    }


    @Test
    fun `getBoard returns correct Board() after being created`() {
        val user = db.createUser(tokenA, "Teste RFL", "a45554@alunos.isel.pt", passwordA)
        val newBoard = db.createBoard(user, boardName, boardDescription)
        val getBoard = db.getBoardDetails(newBoard)

        assertEquals(newBoard, getBoard.id)
        assertEquals(boardName, getBoard.name)
        assertEquals(boardDescription, getBoard.description)
    }

    @Test
    fun `addUserToBoard successfully adds user to the board`() {
        val user = db.createUser(tokenA, "Teste RFL", "a45554@alunos.isel.pt", passwordA)
        val addToBoardUser = db.createUser(tokenA, "Teste RFL Mil", "a45556@alunos.isel.pt", passwordA)
        val boardId = db.createBoard(user, boardName, boardDescription)
        db.addUserToBoard(addToBoardUser, boardId)

        dataSource.connection.use {
            val stm = it.prepareStatement(
                """
                SELECT * FROM userboards WHERE bid = ? ORDER BY uid DESC LIMIT 1
                """.trimIndent()
            )

            stm.setInt(1, boardId)
            val rs = stm.executeQuery()

            assertTrue(rs.next())
            assertEquals(addToBoardUser, rs.getInt("uid"))
            assertEquals(boardId, rs.getInt("bid"))
        }
    }

    @Test
    fun `getBoardsFromUser returns the correct Users`() {
        val newUser = db.createUser(tokenA, "Teste RFL", "a45554@alunos.isel.pt", passwordA)

        val board = db.createBoard(newUser, boardName, boardDescription)
        val board2 = db.createBoard(newUser, boardName + 'a', boardDescription)
        val board3 = db.createBoard(newUser, boardName + 'b', boardDescription)
        val board4 = db.createBoard(newUser, boardName + 'c', boardDescription)

        val boardList = db.getBoardsFromUser(newUser)

        assertEquals(4, boardList.size)
        assertEquals(db.getBoardDetails(board), boardList[0])
        assertEquals(db.getBoardDetails(board2), boardList[1])
        assertEquals(db.getBoardDetails(board3), boardList[2])
        assertEquals(db.getBoardDetails(board4), boardList[3])
    }

}

