import {button, div, h1, p} from "../../DSL/tags.js";
import showErrorResponse, {API_BASE_URL, getStoredUser} from "../../configs/configs.js";


async function searchBoardResults(mainContent, _, query) {

    let idx = 0

    function showNoBoards() {
        mainContent.replaceChildren(
            div({class: "card-header"},
                h1({class: "card-title"}, `No Boards found with name: "${query}"`)
            )
        )
    }


    function showBoard(boards) {
        console.log()
        const board = boards[idx]

        function getDetails(){
            window.location.hash = `boards/${board.id}`
        }

        mainContent.replaceChildren(
            div({class: "card"},
                div({class: "card-header"},
                    h1({class: "card-title"}, `Boards with name: "${query}"    ${idx + 1}/${boards.length}`)
                )
            ),
            div({class: "row justify-content-center"},
                div({class: "card bg-light mb-3  w-25"},
                    div({class: "card-header"}, board.name),
                    div({class: "card-body"},
                        p({}, board.description),
                        button({class: "btn btn-primary btn-sm", onClick:getDetails}, "Get Board Details")
                    ),
                    div({class:"row"},
                        div({class: "col-6"},
                            button({class: "btn btn-outline-primary w-100", onClick:getPreviousBoard}, "Prev")
                        ),
                        div({class: "col-6"},
                            button({class: "btn btn-outline-primary w-100", onClick:getNextBoard}, "Next")
                        )
                    )
                ),
            )
        )
    }

    const user = getStoredUser()
    const id = user.id
    const token = user.token

    const options = {
        method: "GET",
        headers: {
            "Authorization": "Bearer " + token,
            "Content-Type": "application/json",
            "Accept": "application/json",
        }
    }

    const res = await fetch(API_BASE_URL + `users/${id}/boards/search?search_query=${query}`, options)
    const boards = (await res.json()).boards

    //TODO:'in every view subfolder, create a utils.js to store these functions'
    function getNextBoard(){
        idx++
        idx = (idx > boards.length - 1) ? 0 : idx
        showBoard(boards)
    }

    function getPreviousBoard(){
        idx--
        idx = (idx < 0) ? boards.length - 1 : idx
        showBoard(boards)
    }

    if (res.status === 200) {
        idx = 0
        if (boards.length === 0) {
            showNoBoards()
        } else {
            showBoard(boards,

            )
        }
        return
    }
    console.log(res)
    showErrorResponse(mainContent, await res.json())
}

export default searchBoardResults