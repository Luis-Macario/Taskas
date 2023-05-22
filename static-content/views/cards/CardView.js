import {a, br, button, div, h1, li, ul} from "../../DSL/tags.js";
import showErrorResponse, {API_BASE_URL} from "../../configs/configs.js";
import MoveCardModal from "../../partials/cards/MoveCardModal.js";

async function getCard(mainContent, id) {

    const user = getStoredUser()
    const token = user.token

    const res = await fetch(API_BASE_URL + `cards/${id}`, {
        headers: {
            "Authorization": "Bearer " + token
        }
    })

    const moveCardButton = button({type: "button", class: "btn btn-primary btn-sm"}, "Move Card")
    const closeButton = button({type: "button", class: "btn btn-secondary", "data-dismiss": "modal"}, "Close")

    const body = await res.json()
    if (res.status === 200) {
        const card = body
        console.log(card)

        let modal = MoveCardModal(mainContent, closeButton, card.boardID, card.id, card.listID)


        moveCardButton.addEventListener("click", () => {
            mainContent.appendChild(modal);
        })

        closeButton.addEventListener("click", () => {
                mainContent.removeChild(modal);
        })

        mainContent.replaceChildren(
            div({class: "card"},
                div({class: "card-header"},
                    h1({class: "card-title"}, "Card Info")
                ),
                div({class: "card-body"},
                    div({class: "btn-group"},
                        a({class:"btn btn-secondary", href:`#lists/${card.listID}`}, "Return to list"),
                        a({class:"btn btn-secondary", href:`#boards/${card.boardID}`}, "Return to board")
                    ),
                    h1({}, "Card Info"),
                    ul({},
                        li({}, `Name: ${card.name}`),
                        li({}, `Id: ${card.id}`),
                        li({}, `Description: ${card.description}`),
                        li({}, `initial Date: ${card.initialDate}`),
                        li({}, `due Date: ${card.dueDate}`),
                        br(),
                        moveCardButton
                    )
                )
            )
        )
        return
    }
    showErrorResponse(mainContent, body)
}

export default getCard