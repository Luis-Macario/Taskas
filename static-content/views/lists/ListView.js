import {a, div, h1, li, p, table, td, th, tr, ul} from "../../DSL/tags.js";
import showErrorResponse, {hardCodedBearer} from "../../configs/configs.js";
import fetchAPI from "../../fetchAPI.js";

function getList(mainContent, id) {
    fetchAPI(`lists/${id}`,  hardCodedBearer)
        .then(list => {
            const cards = list.cards
            mainContent.replaceChildren(
                div(
                    p(a(`#boards/${list.bid}`, "Return to board")),
                    h1("List Info"),
                    ul(
                        li(`Name: ${list.name}`),
                        li(`id: ${list.id}`),
                    ),
                    table(
                        tr(
                            th("Cards")
                        ),
                        ...(cards.length > 0 ? cards.map(card => {
                                    return tr(
                                        td(a(`#cards/${card.id}`, "Card:" + card.name))
                                    )
                                })
                                :
                                [tr(
                                    td(p("List doesn't have any cards yet")) //fallback value to spread, hence the []
                                )]

                            //li(
                            //    a(`#lists/${id}/cards`, `Get Cards from List[${id}]`)  --next phase
                            // )
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

export default getList