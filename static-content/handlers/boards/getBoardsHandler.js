import boardsView from "../../views/boards/BoardsView.js";
import getBoardsData from "../../data/boards/getBoardsData.js";
import showErrorResponse from "../../configs/configs.js";

export default async function getBoardsHandler(mainContent) {
    try {
        const boardRows = await getBoardsData()
        const view = boardsView(boardRows)
        mainContent.replaceChildren(view)
    } catch (error) {
        mainContent.replaceChildren(showErrorResponse(error))
    }

}