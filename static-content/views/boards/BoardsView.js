import {a, div, h1, p, table, td, th, tr} from "../../DSL/tags.js";

import showErrorResponse, {API_BASE_URL, hardCodedBearer} from "../../configs/configs.js";

async function getBoards(mainContent, id) {
    const skip = 0
    const limit = 10
    fetch(API_BASE_URL + `users/${id}/boards?skip=${skip}&limit=${limit}`, {
        headers: {
            "Authorization": "Bearer " + hardCodedBearer
        }
    })
        .then(res => {
            if (res.status !== 200) throw res.json()
            return res.json()
        })
        .then(boards => {
                mainContent.replaceChildren(
                    div({class: "card"},
                        div({class: "card-header"},
                            h1({class: "card-title"}, "My Boards")
                        ),
                        div({class: "card-body"},
                            table({class: "table"},
                                tr({class: "table-primary"},
                                    th({class: "text-center"}, "Board ID")
                                ),
                                ...(boards.boards.length > 0 ? boards.boards.map(s => {
                                            return tr({class: "table-light"},
                                                td({class: "text-center"},
                                                    a("#boards/" + s.id, "Board " + s.id)
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
                )
            }
        ).catch(e => {
        return e
    }).then(error => {
        showErrorResponse(mainContent, error)
    })
}


export default getBoards

