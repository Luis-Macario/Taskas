import boardCreateView from "../../views/boards/BoardCreateView.js";
import createBoardData from "../../data/boards/createBoardData.js";
import showErrorResponse from "../../configs/configs.js";


export default async function createBoardHandler() {
    try {
        const createBoardFunction = createBoardData
        return boardCreateView(createBoardFunction)
    } catch (error) {
        return showErrorResponse(error)
    }
}