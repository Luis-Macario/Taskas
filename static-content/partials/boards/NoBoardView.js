import {div, h3} from "../../DSL/tags.js";

export default function noBoardsView(query) {
    return div({class: "card-header"},
        h3({class: "card-title"}, `No Boards found with name: "${query}"`)
    )
}