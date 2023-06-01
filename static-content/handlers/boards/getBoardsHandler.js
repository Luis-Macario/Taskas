import boardsView from "../../views/boards/BoardsView.js";
import getBoards from "../../data/boards/getBoards.js";
import showErrorResponse from "../../configs/configs.js";

export default async function getBoardsHandler(mainContent) {
    try{
        const boards = await getBoards()
        const view = boardsView(boards.boards)
        mainContent.replaceChildren(view)
    } catch (error){
        mainContent.replaceChildren(showErrorResponse(error))
    }

}