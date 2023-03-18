package pt.isel.ls.database

import java.sql.Date

data class DataSimpleUser(val id: Int, val name: String, val email: String)
data class DataUser(val id: Int, val name: String, val email: String, val boards: List<DataSimpleBoard>)

data class DataSimpleBoard(val id: Int, val name: String, val description: String)
data class DataBoard(
    val id: Int,
    val name: String,
    val description: String,
    val users: List<DataSimpleUser>,
    val lists: List<DataSimpleList>
)

data class DataSimpleList(val id: Int, val name: String)
data class DataList(val id: Int, val name: String, val cards: List<DataSimpleCard>)

data class DataSimpleCard(val id: Int, val name: String, val description: String, val dueDate: Date)
data class DataCard(
    val id: Int,
    val name: String,
    val description: String,
    val dueDate: Date,
    val list: DataSimpleList
)

data class DataUserCreated(val id: Int, val token: String)

data class DataUserDetails(val id: Int, val name: String, val email: String, val boards: List<DataSimpleBoard>)

data class DataBoardCreated(val id: Int)

object DataUserAdded

data class DataUserBoards(val boards: List<DataSimpleBoard>)

data class DataListCreated(val id: Int)

data class DataBoardLists(val lists: List<DataSimpleList>)

data class DataCardCreated(val id: Int)

data class DataListCards(val cards: List<DataSimpleCard>)

object DataCardMoved

interface DataError



