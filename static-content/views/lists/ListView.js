import {a, br, div, h1, li, p, table, td, th, tr, ul} from "../../DSL/tags.js";

function listView(list, deleteListButton, createCardButton) {
    return div({class: "card"},
        div({class: "card-header"},
            h1({class: "card-title"}, `${list.name}`)
        ),
        div({class: "card-body"},
            a({class: "btn btn-secondary", href: `#boards/${list.bid}`}, "Return to board"),
            ul({},
                //li({}, `Name: ${list.name}`),
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
    )
}

export default listView