import {a, br, button, div, h1, p, table, td, th, tr} from "../../DSL/tags.js";
import showErrorResponse, {getStoredUser, API_BASE_URL} from "../../configs/configs.js";

async function getBoardDetails(mainContent) {
    const user = getStoredUser()
    const id = user.id
    const token = user.token
    const res = await fetch(API_BASE_URL + `boards/${id}`, {
        headers: {
            "Authorization": "Bearer " + token
        }
    })

    function redirectToListCreation() {
        window.location.hash = `boards/${id}/lists/create`
    }

    const body = await res.json()
    if (res.status === 200) {
        const board = body
        console.log(board)
        mainContent.replaceChildren(
            div({class: "card"},
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
                            a({class: "btn btn-secondary", href: `#boards/${id}/users`}, "Board Users")
                        )
                    )
                )
            )
        )
        return
    }
    showErrorResponse(mainContent, body)
}

export default getBoardDetails