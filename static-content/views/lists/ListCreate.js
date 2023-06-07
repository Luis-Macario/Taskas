import {div, h1} from "../../DSL/tags.js";
import ListForm from "../../partials/lists/ListForm.js";

function createListView(handleSubmit, boardId) {
    return div({},
        div({class: "card-header"},
            h1({class: "card-title"}, "Create List")
        ),
        div({class: "card-body w-50 center "},
            ListForm(handleSubmit, boardId)
        )
    )
}

export default createListView