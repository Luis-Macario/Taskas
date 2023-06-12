import {a, br, div, h1, li, ul} from "../../DSL/tags.js";


function cardView(card, moveCardButton) {
    return div({class: "card"},
        div({class: "card-header"},
            h1({class: "card-title"}, "Card Info")
        ),
        div({class: "card-body"},
            div({class: "btn-group"},
                a({class: "btn btn-secondary", href: `#lists/${card.listID}`}, "Return to list"),
                a({class: "btn btn-secondary", href: `#boards/${card.boardID}`}, "Return to board")
            ),
            h1({}, "Card Info"),
            ul({},
                li({}, `Name: ${card.name}`),
                li({}, `Id: ${card.id}`),
                li({}, `Description: ${card.description}`),
                li({}, `initial Date: ${card.initialDate}`),
                li({}, `due Date: ${card.dueDate}`),
                br(),
                moveCardButton
            )
        )
    )
}

export default cardView