import {a, div, h1, p} from "../../DSL/tags.js";
import fetchAPI from "../../fetchAPI.js";
import showErrorResponse, {hardCodedBearer} from "../../configs/configs.js";

function getCardsFromList(mainContent, id) {
    const skip = 0
    const limit = 10
    fetchAPI(`lists/${id}/cards?skip=${skip}&limit=${limit}`,hardCodedBearer)
        .then(cards => {
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

export default  getCardsFromList