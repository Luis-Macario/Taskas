import searchBoardView from "../../views/boards/BoardSearch.js";
import searchBoardData from "../../data/boards/searchBoardData.js";
import showErrorResponse from "../../configs/configs.js";

export default function searchBoardHandler(mainContent) {

    try {
        const view = searchBoardView(searchBoardData)
        mainContent.replaceChildren(view)
    } catch (error) {
        showErrorResponse(error)
    }
}