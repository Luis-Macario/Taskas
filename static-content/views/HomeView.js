import {div, h1, p} from "../DSL/tags.js";

function getHome(mainContent) {
    mainContent.replaceChildren(
        div({class: "card"},
            div({class: "card-header"},
                h1({class: "card-title"}, "Home")
            )
        ),
        div({class: "card-body row align-items-center"},
            p({},"Developed for software laboratory course @ ISEL"),
        )
    )
}

export default getHome