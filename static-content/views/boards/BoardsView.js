import {a, div, h1, p, table, td, th, tr} from "../../DSL/tags.js";

import showErrorResponse, {API_BASE_URL, getStoredUser} from "../../configs/configs.js";

async function getBoards() {

    const user = getStoredUser()
    const id = user.id
    const token = user.token

    const skip = 0
    const limit = 10
    const res = await fetch(API_BASE_URL + `users/${id}/boards?skip=${skip}&limit=${limit}`, {
        headers: {
            "Authorization": "Bearer " + token
        }
    })

    if (res.status === 200) {
        const boards = (await res.json()).boards
        return div({class: "card"},
            div({class: "card-header"},
                h1({class: "card-title"}, "My Boards")
            ),
            div({class: "card-body"},
                table({class: "table"},
                    tr({class: "table-primary"},
                        th({class: "text-center"}, "Boards")
                    ),
                    ...(boards.length > 0 ? boards.map(s => {
                                return tr({},
                                    td({class: "text-center"},
                                        a({href: `#boards/${s.id}`}, s.name)
                                    )
                                )
                            }) :
                            [tr({class: "table-light"},
                                td({class: "text-center"},
                                    p({class: "text-muted mb-0"}, "You aren't a part of any board yet")
                                )
                            )]
                    )
                )
            )
        )
    }
    return showErrorResponse(await res.json())
}

export default getBoards

