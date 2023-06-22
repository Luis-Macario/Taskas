import searchBoardsResultData from "../../data/boards/searchBoardsResultData.js";
import searchBoardResultsView from "../../views/boards/BoardSearchResults.js";
import showErrorResponse from "../../configs/configs.js";
import currentBoardCard from "../../partials/boards/CurrentBoardCard.js";
import noBoardsView from "../../partials/boards/NoBoardView.js";

export default async function searchBoardResultsHandler(mainContent, query) {
    let idx = 0
    let boards = null
    try {
        boards = await searchBoardsResultData(query)
        const firstPartial = (boards.length > 0) ?
            await currentBoardCard(boards[0], getDetails, getPreviousBoard, getNextBoard, idx, boards.length) :
            (() => {
                idx = -1;
                return noBoardsView(query)
            })()

        return searchBoardResultsView(firstPartial, idx, boards.length, query)
    } catch (error) {
        return showErrorResponse(error)
    }

    function getNextBoard() {
        idx++
        idx = (idx > boards.length - 1) ? 0 : idx
        const newBoard = boards[idx]
        document.getElementById("boardCard").replaceChildren(
            currentBoardCard(newBoard, getDetails, getPreviousBoard, getNextBoard, idx, boards.length)
        )
    }

    function getPreviousBoard() {
        idx--
        idx = (idx < 0) ? boards.length - 1 : idx
        const newBoard = boards[idx]
        document.getElementById("boardCard").replaceChildren(
            currentBoardCard(newBoard, getDetails, getPreviousBoard, getNextBoard, idx, boards.length)
        )
    }

    function getDetails() {
        window.location.hash = `boards/${boards[idx].id}`
    }

}

