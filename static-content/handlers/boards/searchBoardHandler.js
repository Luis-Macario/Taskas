import searchBoardView from "../../views/boards/BoardSearch.js";
import searchBoardData from "../../data/boards/searchBoardData.js";
import showErrorResponse from "../../configs/configs.js";

export default async function searchBoardHandler() {

    try {
        return searchBoardView(searchBoardData)
    } catch (error) {
        return showErrorResponse(error)
    }
}