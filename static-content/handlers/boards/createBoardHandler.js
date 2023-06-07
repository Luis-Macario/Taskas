import boardCreate from "../../views/boards/BoardCreate.js";
import createBoard from "../../data/boards/createBoard.js";
import showErrorResponse from "../../configs/configs.js";


export default async function createBoardHandler(mainContent) {
    try {
        const createBoardFunction = await createBoard()
        const view = boardCreate(createBoardFunction)
        mainContent.replaceChildren(view)
    } catch (error) {
        mainContent.replaceChildren(showErrorResponse(error))
    }
}