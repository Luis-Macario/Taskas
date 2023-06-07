import {div, h1} from "../../DSL/tags.js";

export default function noBoardsView(query) {
    return div({class: "card-header"},
        h1({class: "card-title"}, `No Boards found with name: "${query}"`)
    )
}