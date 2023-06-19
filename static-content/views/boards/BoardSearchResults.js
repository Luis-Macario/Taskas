import {div} from "../../DSL/tags.js";
import Page from "../../partials/Page.js";


export default function searchBoardResultsView(boardPartial, idx, max, query) {
    return Page(
        `Boards with name: "${query}"`,
        div({class: "row justify-content-center"},
            div({class: "card bg-light mb-3  w-25", id: "boardCard"},
                boardPartial
            )
        )
    )
}

