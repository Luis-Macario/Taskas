import getBoardData from "../../data/boards/getBoardData.js";
import showErrorResponse from "../../configs/configs.js";
import boardDetailsView from "../../views/boards/BoardDetailsView.js";

export default async function getBoardDetailsHandler(mainContent, id) {
    try {
        const board = await getBoardData(id)
        return boardDetailsView(board)
    } catch (error) {
        return showErrorResponse(error)
    }

}