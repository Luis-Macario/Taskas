import {a, div, p, table, td, th, tr} from "../../DSL/tags.js";
import FetchAPI from "../../fetchAPI.js";
import showErrorResponse, {hardCodedBearer} from "../../configs/configs.js";

async function getBoards(mainContent, id) {
    const skip = 0
    const limit = 10
    FetchAPI(`users/${id}/boards?skip=${skip}&limit=${limit}`, hardCodedBearer)
        .then(boards => {
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

export  default getBoards

