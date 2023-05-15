import {a, br, button, div, h1, li, p, table, td, th, tr, ul} from "../../DSL/tags.js";
import showErrorResponse, {API_BASE_URL, hardCodedBearer} from "../../configs/configs.js";

async function getList(mainContent, id) {
    const res = await fetch(API_BASE_URL + `lists/${id}`, {
        headers: {
            "Authorization": "Bearer " + hardCodedBearer
        }
    })

    const createCardButton = button({class: "btn btn-primary btn-sm"}, "Create Card")
    createCardButton.addEventListener("click", () => {
        window.location.hash = `lists/${id}/cards/create`
    })

    const body = await res.json()
    if (res.status === 200) {
        const list = body
        const cards = list.cards
        mainContent.replaceChildren(
            div({class: "card"},
                div({class: "card-header"},
                    h1({class: "card-title"}, "List Info")
                ),
                a(`#boards/${list.bid}`, "Return to board"),
                ul({},
                    li({}, `Name: ${list.name}`),
                    li({}, `id: ${list.id}`),
                ),
                table({},
                    tr({},
                        th({}, "Cards")
                    ),
                    ...(cards.length > 0 ? cards.map(card => {
                                return tr({},
                                    td({}, a(`#cards/${card.id}`, "Card:" + card.name))
                                )
                            })
                            :
                            [tr({},
                                td({}, p({}, "List doesn't have any cards yet")) //fallback value to spread, hence the []
                            )]
                    ), br(),
                    createCardButton
                )
            )
        )
        return
    }
    showErrorResponse(mainContent, body)
}

export default getList