import showErrorResponse from "../../configs/configs.js";
import boardUsersView from "../../views/boards/BoardUsersView.js";
import getBoardUsers from "../../data/boards/getBoardUsers.js";

export default async function getUsersFromBoardHandler(mainContent,id) {
    try{
        const users = await getBoardUsers(id)
        const view = boardUsersView(users.users,id)
        mainContent.replaceChildren(view)
    } catch (error){
        mainContent.replaceChildren(showErrorResponse(error))
    }

}