import {div, h1} from "../../DSL/tags.js";
import boardSearchForm from "../../partials/boards/BoardSearchForm.js";

function searchBoardView(submitHandler) {
    return div({class: "card"},
        div({class: "card-header"},
            h1({class: "card-title"}, "Search Board")
        ),
        div({class: "card-body w-50 center"},
            boardSearchForm(submitHandler),
            div()
        )
    )

}

export default searchBoardView