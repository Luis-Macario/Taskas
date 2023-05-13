import {a, div, h1, li, p, ul} from "../../DSL/tags.js";
import showErrorResponse, {hardCodedBearer} from "../../configs/configs.js";
import fetchAPI from "../../fetchAPI.js";

function getCard(mainContent, id) {
    fetchAPI(`cards/${id}`,hardCodedBearer)
        .then(card => {
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