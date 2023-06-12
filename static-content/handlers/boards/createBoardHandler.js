import boardCreateView from "../../views/boards/BoardCreateView.js";
import createBoardData from "../../data/boards/createBoardData.js";
import showErrorResponse from "../../configs/configs.js";


export default async function createBoardHandler(mainContent) {
    try {
        const createBoardFunction = createBoardData
        const view = boardCreateView(createBoardFunction)
        mainContent.replaceChildren(view)
    } catch (error) {
        mainContent.replaceChildren(showErrorResponse(error))
    }
}