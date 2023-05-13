import {a, div, h1, p} from "../../DSL/tags.js";
import fetchAPI from "../../fetchAPI.js";
import showErrorResponse, {hardCodedBearer, API_BASE_URL} from "../../configs/configs.js";

function getCardsFromList(mainContent, id) {
    const skip = 0
    const limit = 10
    fetch(API_BASE_URL + `lists/${id}/cards?skip=${skip}&limit=${limit}`, {
        headers: {
            "Authorization": "Bearer " + hardCodedBearer
        }
    }).then(res => {
        if (res.status < 200 || res.status > 299) throw res.json()
        return res.json()
    }).then(cards => {
        mainContent.replaceChildren(
            div(
                p(a(`#lists/${id}`, "Return to list")),
                h1("Cards"),
                ...cards.cards.map(s => {
                    return p(
                        a("#cards/" + s.id, "Card " + s.id)
                    )

                })
            )
        )
    }).catch(e => {
        return e
    }).then(error => {
        showErrorResponse(mainContent, error)
    })
}

export default getCardsFromList