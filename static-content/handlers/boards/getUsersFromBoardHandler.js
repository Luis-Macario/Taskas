import showErrorResponse from "../../configs/configs.js";
import boardUsersView from "../../views/boards/BoardUsersView.js";
import getBoardUsersData from "../../data/boards/getBoardUsersData.js";

export default async function getUsersFromBoardHandler(mainContent, id) {
    try {
        const users = (await getBoardUsersData(id)).users
        boardUsersView(users, id, mainContent)
    } catch (error) {
        mainContent.replaceChildren(showErrorResponse(error))
    }

}