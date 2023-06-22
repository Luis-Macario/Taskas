import boardsView from "../../views/boards/BoardsView.js";
import getBoardsData from "../../data/boards/getBoardsData.js";
import showErrorResponse from "../../configs/configs.js";

export default async function getBoardsHandler() {
    try {
        const boardRows = await getBoardsData()
        return boardsView(boardRows)
    } catch (error) {
        return showErrorResponse(error)
    }

}