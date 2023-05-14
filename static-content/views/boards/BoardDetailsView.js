import {a, br, button, div, h1, p, table, td, th, tr} from "../../DSL/tags.js";
import showErrorResponse, {API_BASE_URL, hardCodedBearer} from "../../configs/configs.js";

async function getBoardDetails(mainContent, id) {
    const res = await fetch(API_BASE_URL + `boards/${id}`, {
        headers: {
            "Authorization": "Bearer " + hardCodedBearer
        }
    })

    const createListButton = button({class: "btn btn-primary btn-sm"}, "Create List")
    createListButton.addEventListener("click", () => {
        window.location.hash = `boards/${id}/lists/create`
    })

    const body = await res.json()
    if (res.status === 200) {
        const board = body
        console.log(board)
        mainContent.replaceChildren(
            div({},
                h1({}, board.name),
                p({}, board.description),
                table({},
                    tr({},
                        th({}, "Lists")
                    ),
                    ...(board.lists.length > 0 ? board.lists.map(l => {
                                return tr({},
                                    td({},
                                        a("#lists/" + l.id, "Link Example to lists/" + l.id)
                                    )
                                )
                            }) :
                            [
                                tr({},
                                    td({},
                                        p({}, "Board has no lists")
                                    )
                                )
                            ]
                    ), br(),
                    createListButton,
                    tr({},
                        th({}, "Users"),
                    ),
                    tr({},
                        a(`#boards/${id}/users`, "Board Users")
                    )
                )
            )
        )
        return
    }
    showErrorResponse(mainContent, body)
}

export default getBoardDetails