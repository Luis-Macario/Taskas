import {button, div, h1, p} from "../../DSL/tags.js";


export default function searchBoardResultsView(boards,query,idx,getDetails,getPreviousBoard,getNextBoard) {

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

        return(
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
}