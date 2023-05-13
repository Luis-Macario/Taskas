import {a, div, h1, li, p, ul} from "../../DSL/tags.js";
import showErrorResponse, {hardCodedBearer, API_BASE_URL} from "../../configs/configs.js";
import fetchAPI from "../../fetchAPI.js";

function getCard(mainContent, id) {
    fetch(API_BASE_URL + `cards/${id}`, {
        headers: {
            "Authorization": "Bearer " + hardCodedBearer
        }
    }).then(res => {
        if (res.status < 200 || res.status > 299) throw res.json()
        return res.json()
    }).then(card => {
        mainContent.replaceChildren(
            div(
                p(a(`#lists/${card.lid}`, "Return to list")),
                //p(a(`#lists/${card.lid}/cards`, "Return to cards")),  --next phase
                h1("Card Info"),
                ul(
                    li(`Name: ${card.name}`),
                    li(`Id: ${card.id}`),
                    li(`Description: ${card.description}`),
                )
            )
        )
    }).catch(e => {
        return e
    }).then(error => {
        showErrorResponse(mainContent, error)
    })
}

export default getCard