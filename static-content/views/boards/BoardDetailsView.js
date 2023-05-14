import {a, div, h1, p, table, td, th, tr} from "../../DSL/tags.js";
import showErrorResponse, {API_BASE_URL, hardCodedBearer} from "../../configs/configs.js";

async function getBoardDetails(mainContent, id) {
    const res = await fetch(API_BASE_URL + `boards/${id}`, {
        headers: {
            "Authorization": "Bearer " + hardCodedBearer
        }
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
                    ),
                    //<a href="#users/1/boards/search" class="btn btn-primary" classNamerch Board </a>
                    a(`#boards/${id}/lists/create`, "Create List"),
                    //aV2({class: "btn btn-primary", href: `#boards/${id}/lists/create`}, "Create List"),
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