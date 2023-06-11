import boardCreate from "../../views/boards/BoardCreate.js";
import createBoardData from "../../data/boards/createBoardData.js";
import showErrorResponse from "../../configs/configs.js";


export default async function createBoardHandler(mainContent) {
    try {
        const createBoardFunction = await createBoardData()
        const view = boardCreate(createBoardFunction)
        mainContent.replaceChildren(view)
    } catch (error) {
        mainContent.replaceChildren(showErrorResponse(error))
    }
}