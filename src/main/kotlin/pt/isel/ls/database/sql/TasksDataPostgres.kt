package pt.isel.ls.database.sql

import org.postgresql.ds.PGSimpleDataSource
import pt.isel.ls.database.AppDatabase
import pt.isel.ls.database.memory.BoardNotFoundException
import pt.isel.ls.database.memory.CardNotFoundException
import pt.isel.ls.database.memory.ListNotFoundException
import pt.isel.ls.database.memory.UserNotFoundException
import pt.isel.ls.domain.Board
import pt.isel.ls.domain.Card
import pt.isel.ls.domain.SimpleBoard
import pt.isel.ls.domain.SimpleList
import pt.isel.ls.domain.User
import java.sql.Date
import java.sql.SQLException
import java.sql.Statement

class TasksDataPostgres(url: String) : AppDatabase {
    private val dataSource = PGSimpleDataSource().apply {
        this.setUrl(url)
    }

    override fun createUser(token: String, name: String, email: String): User {
        dataSource.connection.use {
            val stm = it.prepareStatement(
                " INSERT INTO  users(name, email, token) " +
                    "VALUES (?,?,?)",
                Statement.RETURN_GENERATED_KEYS
            )
            stm.setString(1, name)
            stm.setString(2, email)
            stm.setString(3, token)

            val affectedRows: Int = stm.executeUpdate()
            if (affectedRows == 0) {
                throw SQLException("Creating user failed, no rows affected.")
            }

            val generatedKeys = stm.generatedKeys
            val id = if (generatedKeys.next()) {
                generatedKeys.getInt(1)
            } else {
                throw SQLException("Creating user failed, no ID obtained.")
            }

            return User(id, name, email, token)
        }
    }

    override fun getUserDetails(uid: Int): User {
        dataSource.connection.use {
            val stm = it.prepareStatement(
                """
                SELECT * FROM users where id = ?
                """.trimIndent()
            )
            stm.setInt(1, uid)

            val rs = stm.executeQuery()

            if (rs.next()) {
                return User(
                    id = rs.getInt("id"),
                    name = rs.getString("name"),
                    email = rs.getString("email"),
                    token = rs.getString("token")
                )
            } else {
                throw UserNotFoundException
            }
        }
    }

    override fun getUsersFromBoard(bid: Int): List<User> {
        dataSource.connection.use {
            val stm = it.prepareStatement(
                """
                SELECT id, name, email, token
                FROM userboards
                JOIN users u on u.id = userboards.uid
                where bid = ?
                """.trimIndent()
            )
            stm.setInt(1, bid)

            val rs = stm.executeQuery()
            val userList = mutableListOf<User>()

            while (rs.next()) {
                userList.add(
                    User(
                        id = rs.getInt("id"),
                        name = rs.getString("name"),
                        email = rs.getString("email"),
                        token = rs.getString("token")
                    )
                )
            }
            return userList.toList()
        }
    }

    override fun checkEmailAlreadyExists(email: String): Boolean {
        dataSource.connection.use {
            val stm = it.prepareStatement(
                """
                SELECT EXISTS(SELECT 1 FROM users WHERE email = ?)
                """.trimIndent()
            )
            stm.setString(1, email)

            val rs = stm.executeQuery()
            rs.next()
            return rs.getBoolean(1)
        }
    }

    override fun createBoard(uid: Int, name: String, description: String): Board {
        dataSource.connection.use {
            val stm = it.prepareStatement(
                """
                INSERT INTO boards (name, description) 
                VALUES (?,?) 
                """.trimIndent(),
                Statement.RETURN_GENERATED_KEYS
            )

            stm.setString(1, name)
            stm.setString(2, description)

            val affectedRows: Int = stm.executeUpdate()
            if (affectedRows == 0) {
                throw SQLException("Creating board failed, no rows affected.")
            }

            val generatedKeys = stm.generatedKeys
            val id = if (generatedKeys.next()) {
                generatedKeys.getInt(1)
            } else {
                throw SQLException("Creating board failed, no ID obtained.")
            }

            addUserToBoard(uid, id)

            return Board(id, name, description, emptyList())
        }
    }

    override fun getBoardDetails(bid: Int): SimpleBoard {
        dataSource.connection.use {
            val stm = it.prepareStatement(
                """
                SELECT * FROM boards where id = ?
                """.trimIndent()
            )
            stm.setInt(1, bid)

            val rs = stm.executeQuery()
            if (rs.next()) {
                return SimpleBoard(
                    id = rs.getInt("id"),
                    name = rs.getString("name"),
                    description = rs.getString("description")
                )
            } else {
                throw BoardNotFoundException
            }
        }
    }

    override fun addUserToBoard(uid: Int, bid: Int) {
        dataSource.connection.use {
            val stm = it.prepareStatement(
                """
                INSERT INTO userboards 
                values (?,?)
                """.trimIndent(),
                Statement.RETURN_GENERATED_KEYS
            )
            stm.setInt(1, uid)
            stm.setInt(2, bid)

            val affectedRows: Int = stm.executeUpdate()
            if (affectedRows == 0) {
                throw SQLException("Adding a User to a Board failed, no rows affected.")
            }

            val generatedKeys = stm.generatedKeys
            if (generatedKeys.next()) {
                generatedKeys.getInt(1)
            } else {
                throw SQLException("Adding a User to a Board failed, no ID obtained.")
            }
        }
    }

    override fun getBoardsFromUser(uid: Int): List<SimpleBoard> {
        dataSource.connection.use {
            val stm = it.prepareStatement(
                """
                SELECT id, name, description
                FROM userboards
                JOIN boards b on b.id = userboards.bid
                where uid = ?
                """.trimIndent()
            )
            stm.setInt(1, uid)

            val rs = stm.executeQuery()
            val boardList = mutableListOf<SimpleBoard>()

            while (rs.next()) {
                boardList.add(
                    SimpleBoard(
                        id = rs.getInt("id"),
                        name = rs.getString("name"),
                        description = rs.getString("description")
                    )
                )
            }
            return boardList.toList()
        }
    }

    override fun createList(bid: Int, name: String): SimpleList {
        dataSource.connection.use {
            val stm = it.prepareStatement(
                """
                INSERT INTO tasklists (bid, name)
                VALUES (?,?) 
                """.trimIndent(),
                Statement.RETURN_GENERATED_KEYS
            )

            stm.setInt(1, bid)
            stm.setString(2, name)

            val affectedRows: Int = stm.executeUpdate()
            if (affectedRows == 0) {
                throw SQLException("Creating taskList failed, no rows affected.")
            }

            val generatedKeys = stm.generatedKeys
            val id = if (generatedKeys.next()) {
                generatedKeys.getInt(1)
            } else {
                throw SQLException("Creating taskList failed, no ID obtained.")
            }

            return SimpleList(id, bid, name)
        }
    }

    override fun getListsFromBoard(bid: Int): List<SimpleList> {
        dataSource.connection.use {
            val stm = it.prepareStatement(
                """
                SELECT tk.id , tk.bid , tk.name
                FROM tasklists as tk
                JOIN boards b on b.id = tk.bid
                where tk.bid = ?
                """.trimIndent()
            )
            stm.setInt(1, bid)

            val rs = stm.executeQuery()
            val simpleList = mutableListOf<SimpleList>()

            while (rs.next()) {
                simpleList.add(
                    SimpleList(
                        bid = bid,
                        id = rs.getInt(1),
                        name = rs.getString(3)
                    )
                )
            }
            return simpleList.toList()
        }
    }

    override fun getListDetails(lid: Int): SimpleList {
        dataSource.connection.use {
            val stm = it.prepareStatement(
                """
                SELECT * FROM tasklists WHERE id = ?
                """.trimIndent()
            )

            stm.setInt(1, lid)

            val rs = stm.executeQuery()
            if (rs.next()) {
                return SimpleList(
                    id = rs.getInt("id"),
                    bid = rs.getInt("bid"),
                    name = rs.getString("name")
                )
            } else {
                throw ListNotFoundException
            }
        }
    }

    override fun checkListsFromSameBoard(l1: Int, l2: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun deleteList(lid: Int) {
        dataSource.connection.use {
            val stm = it.prepareStatement(
                """
                DELETE FROM tasklists WHERE id = ?
                """.trimIndent()
            )

            stm.setInt(1, lid)

            val affectedRows: Int = stm.executeUpdate()
            if (affectedRows == 0) {
                throw SQLException("Deleting list failed, no rows affected.")
            }
        }
    }

    override fun createCard(lid: Int, name: String, description: String, initDate: Date, dueDate: Date): Card {
        dataSource.connection.use {
            val stm = it.prepareStatement(
                """
                INSERT INTO cards (bid, lid, indexlist, name, description, initdate, finishdate)
                VALUES (?,?,?,?,?,?,?) 
                """.trimIndent(),
                Statement.RETURN_GENERATED_KEYS
            )

            val stmGetMaxIndexList = it.prepareStatement(
                """
                SELECT max(indexlist) FROM cards where bid = ? and lid = ?  
                """.trimIndent()
            )

            val bid = getListDetails(lid).bid

            stmGetMaxIndexList.setInt(1, bid)
            stmGetMaxIndexList.setInt(2, lid)
            val rs = stmGetMaxIndexList.executeQuery()
            rs.next()
            val indexList = rs.getInt(1) + 1

            stm.setInt(1, bid)
            stm.setInt(2, lid)
            stm.setInt(3, indexList)
            stm.setString(4, name)
            stm.setString(5, description)
            stm.setDate(6, initDate)
            stm.setDate(7, dueDate)

            val affectedRows: Int = stm.executeUpdate()
            if (affectedRows == 0) {
                throw SQLException("Creating card failed, no rows affected.")
            }

            val generatedKeys = stm.generatedKeys
            val id = if (generatedKeys.next()) {
                generatedKeys.getInt(1)
            } else {
                throw SQLException("Creating card failed, no ID obtained.")
            }

            return Card(id, bid, lid, name, description, dueDate)
        }
    }

    override fun getCardsFromList(lid: Int, bid: Int): List<Card> {
        dataSource.connection.use {
            val stm = it.prepareStatement(
                """
                SELECT c.id, c.bid,c.lid, c.name, c.description, c.initdate
                FROM cards as c
                WHERE c.bid = ? and c.lid = ?
                """.trimIndent()
            )
            stm.setInt(1, bid)
            stm.setInt(2, lid)

            val rs = stm.executeQuery()
            val tasksList = mutableListOf<Card>()

            while (rs.next()) {
                tasksList.add(
                    Card(
                        id = rs.getInt(1),
                        bid = rs.getInt(2),
                        lid = rs.getInt(3),
                        name = rs.getString(4),
                        description = rs.getString(5),
                        initDate = rs.getDate(6)
                    )
                )
            }
            return tasksList.toList()
        }
    }

    override fun getCardDetails(cid: Int): Card {
        dataSource.connection.use {
            val stm = it.prepareStatement(
                """
                SELECT * FROM cards WHERE id = ?
                """.trimIndent()
            )

            stm.setInt(1, cid)

            val rs = stm.executeQuery()
            if (rs.next()) {
                return Card(
                    id = rs.getInt("id"),
                    bid = rs.getInt("bid"),
                    lid = rs.getInt("lid"),
                    name = rs.getString("name"),
                    description = rs.getString("description"),
                    initDate = rs.getDate("initDate")
                )
            } else {
                throw CardNotFoundException
            }
        }
    }

    override fun moveCard(cid: Int, lid: Int, cix: Int) {
        dataSource.connection.use {
            val stm = it.prepareStatement(
                """
                CALL "move_card"(?, ? , ?)
                """.trimIndent()
            )

            stm.setInt(1, cid)
            stm.setInt(2, lid)
            stm.setInt(3, cix)

            val affectedRows: Boolean = stm.execute()
            if (affectedRows) {
                throw SQLException("Updating card.lid failed, no rows affected.")
            }
        }
    }
    override fun deleteCard(cid: Int) {
        dataSource.connection.use {
            val stm = it.prepareStatement(
                """
                    BEGIN  transaction;
                 CALL "delete_card"(?);
                 End transaction;
                """.trimIndent()
            )

            stm.setInt(1, cid)

            val affectedRows: Boolean = stm.execute()
            if (affectedRows) {
                throw SQLException("Delete card failed, no rows affected.")
            }
        }
    }

    override fun tokenToId(bToken: String): Int {
        dataSource.connection.use {
            val stm = it.prepareStatement(
                """
                SELECT id
                FROM users
                WHERE token = ?
                LIMIT  1
                """.trimIndent()
            )

            stm.setString(1, bToken)
            val rs = stm.executeQuery()
            if (rs.next()) {
                return rs.getInt(1)
            } else {
                throw UserNotFoundException
            }
        }
    }
}
