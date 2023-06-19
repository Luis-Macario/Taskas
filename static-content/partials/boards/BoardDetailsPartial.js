import {a, br, button, div, p, table, td, th, tr} from "../../DSL/tags.js";

export default function BoardDetailsPartial(board, redirectToListCreation) {
    return div({},
        p({}, board.description),
        table({},
            tr({},
                th({}, "Lists")
            ),
            div({class: "card-body"},
                div({class: "btn-group-vertical"},
                    ...(board.lists.length > 0 ? board.lists.map(l => {
                                return a({class: "btn btn-secondary", href: `#lists/${l.id}`}, `${l.name}`)
                            }) :
                            [
                                tr({},
                                    td({},
                                        p({}, "Board has no lists")
                                    )
                                )
                            ]
                    )
                ),

                br(),
                br(),
                button({class: "btn btn-primary btn-sm", onClick: redirectToListCreation}, "Create List"),
            ),
            tr({},
                th({}, "Users"),
            ),
            div({class: "card-body"},
                a({class: "btn btn-secondary", href: `#boards/${board.id}/users`}, "Board Users")
            )
        )
    )
}