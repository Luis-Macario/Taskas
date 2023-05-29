import {a, br, button, div, h1, p, table, td, th, tr} from "../../DSL/tags.js";

function getBoardDetails(board) {

    function redirectToListCreation() {
        window.location.hash = `boards/${board.id}/lists/create`
    }

    return div({class: "card"},
        div({class: "card-header"},
            h1({class: "card-title"}, `${board.name}`)
        ),
        div({class: "card-body"},
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
    )
}

export default getBoardDetails