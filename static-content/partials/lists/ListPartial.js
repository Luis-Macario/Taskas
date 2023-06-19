import {a, br, div, li, p, table, td, th, tr, ul} from "../../DSL/tags.js";

export default function ListPartial(list, deleteListButton, createCardButton) {
    return div({},
        a({class: "btn btn-secondary", href: `#boards/${list.bid}`}, "Return to board"),
        ul({},
            li({}, `ID: ${list.id}`),
        ),
        table({},
            deleteListButton,
            tr({},
                th({}, "Cards")
            ),
            div({class: "card-body"},
                div({class: "btn-group-vertical"},
                    ...(list.cards.length > 0 ? list.cards.map(card => {
                                return a({class: "btn btn-secondary", href: `#cards/${card.id}`},
                                    "Card:" + card.name)
                            })
                            :
                            [tr({},
                                td({}, p({}, "List doesn't have any cards yet"))
                            )]
                    )
                ),
                br(),
                br(),
                createCardButton
            )
        )
    )
}