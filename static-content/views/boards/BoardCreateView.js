import {div, h1} from "../../DSL/tags.js";
import createBoardForm from "../../partials/boards/CreateBoardForm.js";

function boardCreateView(createBoardFunction) {
    return (
        div({class: "card"},
            div({class: "card-header"},
                h1({class: "card-title"}, "Create Board")
            ),
            div({class: "card-body w-50 center"},
                createBoardForm(createBoardFunction)
            )
        )
    )
}

export default boardCreateView