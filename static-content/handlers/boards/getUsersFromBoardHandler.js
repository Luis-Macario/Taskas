import showErrorResponse from "../../configs/configs.js";
import boardUsersView from "../../views/boards/BoardUsersView.js";
import getBoardUsers from "../../data/boards/getBoardUsers.js";

export default async function getUsersFromBoardHandler(mainContent, id) {
    try {
        const users = (await getBoardUsers(id)).users
        boardUsersView(users, id, mainContent)
    } catch (error) {
        mainContent.replaceChildren(showErrorResponse(error))
    }

}