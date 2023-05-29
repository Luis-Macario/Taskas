import {div, h1} from "../../DSL/tags.js";
import CardForm from "../../partials/cards/CardForm.js";

function createCardView(handleSubmit) {
    return div({},
        div({class: "card-header"},
            h1({class: "card-title"}, "Create Card")
        ),
        div({class: "card-body w-50 center"},
            CardForm(handleSubmit)
        )
    )
}

export default createCardView