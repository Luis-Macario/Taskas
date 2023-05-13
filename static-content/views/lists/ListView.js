import {a, div, h1, li, p, table, td, th, tr, ul} from "../../DSL/tags.js";
import showErrorResponse, {API_BASE_URL, hardCodedBearer} from "../../configs/configs.js";

async function getList(mainContent, id) {
    const res = await fetch(API_BASE_URL + `lists/${id}`, {
        headers: {
            "Authorization": "Bearer " + hardCodedBearer
        }
    })
        const body = await res.json()
            if(res.status === 200) {
                const list = body
                const cards = list.cards
                mainContent.replaceChildren(
                    div({},
                        a(`#boards/${list.bid}`, "Return to board"),
                        h1({},"List Info"),
                        ul({},
                            li({},`Name: ${list.name}`),
                            li({},`id: ${list.id}`),
                        ),
                        table({},
                            tr({},
                                th({},"Cards")
                            ),
                            ...(cards.length > 0 ? cards.map(card => {
                                        return tr({},
                                            td({},a(`#cards/${card.id}`, "Card:" + card.name))
                                        )
                                    })
                                    :
                                    [tr({},
                                        td({},p({},"List doesn't have any cards yet")) //fallback value to spread, hence the []
                                    )]
                            )
                        )
                    )
                )
                return
            }
    showErrorResponse(mainContent, body)
}

export default getList