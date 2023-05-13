import {div, h1, p} from "../../DSL/tags.js";
import showErrorResponse, {API_BASE_URL, hardCodedBearer} from "../../configs/configs.js";

let idx = 0

async function searchBoardResults(mainContent, id, query) {
    function showBoard(boards) {
        console.log()
        const board = boards[idx]

        mainContent.replaceChildren(
            div({},
                h1({}, board.name),
                p({}, board.description),
            ),
        )
    }

    const options = {
        method: "GET",
        headers: {
            "Authorization": "Bearer " + hardCodedBearer,
            "Content-Type": "application/json",
            "Accept": "application/json",
        }
    }
    const res = await fetch(API_BASE_URL + `users/${id}/boards/search?search_query=${query}`, options)
    if (res.status === 200) {
        idx = 0
        const boards = (await res.json()).boards
        //TODO: MAKE BUTTON DSL AND TIDY UP CODE
        const nextButton = document.createElement("button")
        nextButton.appendChild(document.createTextNode("Next"))
        nextButton.addEventListener("click", () => {
            idx++
            idx = (idx > boards.length - 1) ? 0 : idx
            showBoard(boards)
            mainContent.appendChild(prevButton)
            mainContent.appendChild(nextButton)
        })
        const prevButton = document.createElement("button")
        prevButton.appendChild(document.createTextNode("Prev"))
        prevButton.addEventListener("click", () => {
            idx--
            idx = (idx < 0) ? boards.length - 1 : idx
            showBoard(boards)
            mainContent.appendChild(prevButton)
            mainContent.appendChild(nextButton)
        })
        showBoard(boards)
        mainContent.appendChild(prevButton)
        mainContent.appendChild(nextButton)
        return
    }
    console.log(res)
    showErrorResponse(mainContent, await res.json())
}

export default searchBoardResults