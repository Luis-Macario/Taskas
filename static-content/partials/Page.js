import {div, h1} from "../DSL/tags.js";

export default function Page(title, ...content) {
    return (
        div({class: "card"},
            div({class: "card-header"},
                h1({class: "card-title"}, title)
            ),
            div({class: "card-body center"},
                ...content
            )
        )
    )
}