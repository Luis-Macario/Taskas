import {div} from "../../DSL/tags.js";
import CardForm from "../../partials/cards/CardForm.js";
import Page from "../../partials/Page.js";

function createCardView(handleSubmit, today) {
    return Page(
        "Create Card",
        div({class: "w-50"},
            CardForm(handleSubmit, today)
        )
    )
}

export default createCardView