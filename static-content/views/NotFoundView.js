import {div, h1, p} from "../DSL/tags.js";

function getNotFound(mainContent) {
    mainContent.replaceChildren(
        div({class: "card"},
            div({class: "card-header"},
                h1({class: "card-title"}, "404 - Not Found")
            )
        ),
        div({class: "card-body row align-items-center"},
            p({},"This page isn't available. Sorry about that."),
        )
    )
}

export default getNotFound