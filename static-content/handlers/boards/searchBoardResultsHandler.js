import searchBoardsResult from "../../data/boards/searchBoardsResult.js";
import searchBoardResultsView from "../../views/boards/BoardSearchResults.js";
import showErrorResponse from "../../configs/configs.js";
import currentBoardView from "../../partials/boards/CurrentBoardView.js";
import noBoardsView from "../../partials/boards/NoBoardView.js";

export default async function searchBoardResultsHandler(mainContent, query) {
    let idx = 0
    let boards = null
    let boardCard = null
    try {
        boards = await searchBoardsResult(query)
        const firstPartial = (boards.length > 0) ?
            await currentBoardView(boards[0], getDetails, getPreviousBoard, getNextBoard, idx, boards.length) :
            (() => {
                idx = -1;
                return noBoardsView(query)
            })()

        const view = await searchBoardResultsView(firstPartial, idx, boards.length, query)
        mainContent.replaceChildren(view)
        boardCard = document.getElementById("boardCard")
    } catch (error) {
        mainContent.replaceChildren(showErrorResponse(error))
    }

    function getNextBoard() {
        idx++
        idx = (idx > boards.length - 1) ? 0 : idx
        const newBoard = boards[idx]
        boardCard.replaceChildren(
            currentBoardView(newBoard, getDetails, getPreviousBoard, getNextBoard, idx, boards.length)
        )
    }

    function getPreviousBoard() {
        idx--
        idx = (idx < 0) ? boards.length - 1 : idx
        const newBoard = boards[idx]
        boardCard.replaceChildren(
            currentBoardView(newBoard, getDetails, getPreviousBoard, getNextBoard, idx, boards.length)
        )
    }

    function getDetails() {
        window.location.hash = `boards/${boards[idx].id}`
    }

}

