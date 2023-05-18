import {a, br, button, div, h1, li, ul} from "../../DSL/tags.js";
import showErrorResponse, {API_BASE_URL, hardCodedBearer} from "../../configs/configs.js";
import MoveCardModal from "./MoveCardModal.js";

async function getCard(mainContent, id) {
    const res = await fetch(API_BASE_URL + `cards/${id}`, {
        headers: {
            "Authorization": "Bearer " + hardCodedBearer
        }
    })

    let modalVisible = false
    const moveCardButton = button({type: "button", class: "btn btn-primary btn-sm"}, "Move Card")
    const closeButton = button({type: "button", class: "btn btn-secondary", "data-dismiss": "modal"}, "Close")

    const body = await res.json()
    if (res.status === 200) {
        const card = body
        console.log(card)

        let modal = null;
        // Move Card Modal, button to Open it
        moveCardButton.addEventListener("click", () => {
            if (!modal) {
                modal = MoveCardModal(mainContent, closeButton, card.boardID, card.id, card.listID);
                mainContent.appendChild(modal);
                modalVisible = true;
            } else if (!modalVisible) {
                mainContent.appendChild(modal);
                modalVisible = true;
            } else {
                mainContent.removeChild(modal);
                modalVisible = false;
            }
        });

        closeButton.addEventListener("click", () => {
            mainContent.removeChild(modal);
            modalVisible = false;
        });

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