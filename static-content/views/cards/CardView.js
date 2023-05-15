import {a, div, h1, li, p, ul} from "../../DSL/tags.js";
import showErrorResponse, {API_BASE_URL, hardCodedBearer} from "../../configs/configs.js";

async function getCard(mainContent, id) {
    const res = await fetch(API_BASE_URL + `cards/${id}`, {
        headers: {
            "Authorization": "Bearer " + hardCodedBearer
        }
    })
    const body = await res.json()
    if (res.status === 200) {
        const card = body
        console.log(card)
        mainContent.replaceChildren(
            div({class: "card"},
                div({class: "card-header"},
                    h1({class: "card-title"}, "Card Info")
                ),
                p({},
                    a(`#boards/${card.boardID}`, "Return to board")
                ),
                p({},
                    a(`#lists/${card.listID}`, "Return to list")
                ),
                h1({}, "Card Info"),
                ul({},
                    li({}, `Name: ${card.name}`),
                    li({}, `Id: ${card.id}`),
                    li({}, `Description: ${card.description}`),
                )
            )
        )
        return
    }
    showErrorResponse(mainContent, body)
}

export default getCard