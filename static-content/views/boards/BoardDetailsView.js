import {a, div, h1, p, table, td, th, tr} from "../../DSL/tags.js";
import FetchAPI from "../../fetchAPI.js";
import showErrorResponse, {hardCodedBearer} from "../../configs/configs.js";

function getBoardDetails(mainContent, id) {
    FetchAPI(`boards/${id}`, hardCodedBearer)
        .then(board => {
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