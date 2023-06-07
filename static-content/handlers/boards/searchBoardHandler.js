import searchBoardView from "../../views/boards/BoardSearch.js";
import searchBoard from "../../data/boards/searchBoard.js";
import showErrorResponse from "../../configs/configs.js";

export default function searchBoardHandler(mainContent) {

    try {
        const view = searchBoardView(searchBoard)
        mainContent.replaceChildren(view)
    } catch (error) {
        showErrorResponse(error)
    }
}