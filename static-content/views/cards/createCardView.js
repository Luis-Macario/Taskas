import {div} from "../../DSL/tags.js";
import CardForm from "../../partials/cards/CardForm.js";
import Page from "../../partials/Page.js";

function createCardView(handleSubmit) {
    return Page(
        "Create Card",
        div({class: "w-50"},
            CardForm(handleSubmit)
        )
    )
}

export default createCardView