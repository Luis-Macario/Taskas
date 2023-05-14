import {button, div, h1, p} from "../../DSL/tags.js";
import showErrorResponse, {API_BASE_URL, hardCodedBearer} from "../../configs/configs.js";

let idx = 0

async function searchBoardResults(mainContent, id, query) {

    function showNoBoards() {
        mainContent.replaceChildren(
            div({class: "card-header"},
                h1({class: "card-title"}, `No Boards found with name: "${query}"`)
            )
        )
    }

    function showBoard(boards, prevButton, nextButton) {
        console.log()
        const board = boards[idx]

        const getDetailsButton = button({class: "btn btn-primary btn-sm"}, "Get Board Details")
        getDetailsButton.addEventListener("click", () => {
            window.location.hash = `boards/${board.id}`
        })

        mainContent.replaceChildren(
            div({class: "card-header"},
                h1({class: "card-title"}, `Boards with name: "${query}"    ${idx+1}/${boards.length}`)
            ),
            /*div({class: "card-body w-50 center", style: "width: 18rem"},
                h2({}, board.name),
                p({}, board.description),
                prevButton,
                nextButton
            )
        )*/
            div({class: "card bg-light mb-3  w-25 center", style: "max-width: 18rem"},
                div({class: "card-header"},board.name),
                div({class: "card-body"},
                    p({}, board.description),
                    getDetailsButton
                    )
                ),
            prevButton,
            nextButton
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
        //TODO: MAKE BUTTON DSL AND TIDY UP CODE ----> DONE??
        const nextButton = button({class: "btn btn-outline-primary"}, "Next")
        nextButton.addEventListener("click", () => {
            idx++
            idx = (idx > boards.length - 1) ? 0 : idx
            showBoard(boards, prevButton, nextButton)
        })
        const prevButton = button({class: "btn btn-outline-primary"}, "Prev")
        prevButton.addEventListener("click", () => {
            idx--
            idx = (idx < 0) ? boards.length - 1 : idx
            showBoard(boards, prevButton, nextButton)
        })

        if (boards.length === 0) {
            showNoBoards()
        } else {
            showBoard(boards, prevButton, nextButton)
        }
        return
    }
    console.log(res)
    showErrorResponse(mainContent, await res.json())
}

export default searchBoardResults