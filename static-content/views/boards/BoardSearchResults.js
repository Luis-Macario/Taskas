import {div, h1} from "../../DSL/tags.js";


export default function searchBoardResultsView(boardPartial, idx, max, query) {
    return div({},
        div({class: "card"},
            div({class: "card-header"},
                h1({class: "card-title"}, `Boards with name: "${query}"`)
            )
        ),
        div({class: "row justify-content-center"},
            div({class: "card bg-light mb-3  w-25", id: "boardCard"},
                boardPartial
            )
        )
    )
}

