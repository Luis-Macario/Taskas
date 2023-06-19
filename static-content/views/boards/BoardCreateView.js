import createBoardForm from "../../partials/boards/CreateBoardForm.js";
import Page from "../../partials/Page.js";
import {div} from "../../DSL/tags.js";

function boardCreateView(createBoardFunction) {
    return Page(
        "Create Board",
        div({class: "w-50"},
            createBoardForm(createBoardFunction)
        )
    )
}

export default boardCreateView