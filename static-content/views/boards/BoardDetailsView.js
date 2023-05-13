import {a, div, h1, p, table, td, th, tr} from "../../DSL/tags.js";
import FetchAPI from "../../fetchAPI.js";
import showErrorResponse, {API_BASE_URL, hardCodedBearer} from "../../configs/configs.js";

function getBoardDetails(mainContent, id) {
    fetch(API_BASE_URL + `boards/${id}`, {
        headers: {
            "Authorization": "Bearer " + hardCodedBearer
        }
    }).then(res => {
        if (res.status < 200 || res.status > 299) throw res.json()
        return res.json()
    }).then(board => {
        mainContent.replaceChildren(
            div(
                h1(board.name),
                p(board.description),
                table(
                    tr(
                        th("Lists")
                    ),
                    ...(board.lists.length > 0 ? board.lists.map(l => {
                                return tr(
                                    td(a("#lists/" + l.id, "Link Example to lists/" + l.id))
                                )
                            }) :
                            [
                                tr(
                                    td(
                                        p("Board has no lists")
                                    )
                                )
                            ]
                    ),
                    tr(
                        th("Users"),
                    ),
                    tr(
                        a(`#boards/${id}/users`, "Board Users")
                    )
                )
            )
        )
    }).catch(e => {
        return e
    }).then(error => {
        showErrorResponse(mainContent, error)
    })
}

export default getBoardDetails