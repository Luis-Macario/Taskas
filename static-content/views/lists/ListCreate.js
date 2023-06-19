import {div} from "../../DSL/tags.js";
import ListForm from "../../partials/lists/ListForm.js";
import Page from "../../partials/Page.js";

function createListView(handleSubmit, boardId) {
    return Page(
        "Create List",
        div({class: "w-50"},
            ListForm(handleSubmit, boardId)
        )
    )
}

export default createListView