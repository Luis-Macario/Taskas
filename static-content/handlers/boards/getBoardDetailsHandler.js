import getBoard from "../../data/boards/getBoard.js";
import showErrorResponse from "../../configs/configs.js";
import boardDetailsView from "../../views/boards/BoardDetailsView.js";

export default async function getBoardDetailsHandler(mainContent,id) {
    try{
        const board = await getBoard(id)
        const view = boardDetailsView(board)
        mainContent.replaceChildren(view)
    } catch (error){
        mainContent.replaceChildren(showErrorResponse(error))
    }

}