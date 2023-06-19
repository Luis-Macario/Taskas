import {a, br, div, li, ul} from "../../DSL/tags.js";

export default function CardPartial(card, moveCardButton) {
    return div({},
        div({class: "btn-group"},
            a({class: "btn btn-secondary", href: `#lists/${card.listID}`}, "Return to list"),
            a({class: "btn btn-secondary", href: `#boards/${card.boardID}`}, "Return to board")
        ),
        ul({},
            li({}, `Id: ${card.id}`),
            li({}, `Description: ${card.description}`),
            li({}, `initial Date: ${card.initialDate}`),
            li({}, `due Date: ${card.dueDate}`),
            br(),
            moveCardButton
        )
    )
}