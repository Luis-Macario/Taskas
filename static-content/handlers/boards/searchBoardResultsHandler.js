import searchBoardsResult from "../../data/boards/searchBoardsResult.js";
import searchBoardResultsView from "../../views/boards/BoardSearchResults.js";

export default async function searchBoardResultsHandler(mainContent,query) {
    const boards = await searchBoardsResult(query)
    const view = await searchBoardResultsView(boards,query,getDetails)
    mainContent.replaceChildren(view)
}

function getDetails(){
    window.location.hash = `boards/${board.id}`
}