import {a, div, p, table, td, th, tr} from "../../DSL/tags.js";
import FetchAPI from "../../fetchAPI.js";
import showErrorResponse, {hardCodedBearer, API_BASE_URL} from "../../configs/configs.js";

async function getBoards(mainContent, id) {
    const skip = 0
    const limit = 10
    fetch(API_BASE_URL + `users/${id}/boards?skip=${skip}&limit=${limit}`, {
        headers: {
            "Authorization": "Bearer " + hardCodedBearer
        }
    }).then(res => {
        if (res.status < 200 || res.status > 299) throw res.json()
        return res.json()
    }).then(boards => {
            mainContent.replaceChildren(
                div(
                    table(
                        tr(
                            th("My Boards")
                        ),
                        ...(boards.boards.length > 0 ? boards.boards.map(s => {
                                    return tr(
                                        td(a("#boards/" + s.id, "Board " + s.id))
                                    )
                                }) :
                                [tr(
                                    td(
                                        p("You aren't a part of any board yet")
                                    )
                                )]
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

