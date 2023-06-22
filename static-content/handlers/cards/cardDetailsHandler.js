import getCardData from "../../data/cards/getCardData.js";
import cardView from "../../views/cards/cardView.js";
import showErrorResponse from "../../configs/configs.js";
import {button} from "../../DSL/tags.js";
import MoveCardModal from "../../partials/cards/MoveCardModal.js";

async function cardDetailsHandler(mainContent, cardID) {
    try {
        const card = await getCardData(cardID)

        const moveCardButton = button({type: "button", class: "btn btn-primary btn-sm"}, "Move Card")
        const closeButton = button({type: "button", class: "btn btn-secondary", "data-dismiss": "modal"}, "Close")

        let modal = MoveCardModal(mainContent, closeButton, card.boardID, card.id, card.listID)

        moveCardButton.addEventListener("click", () => {
            mainContent.appendChild(modal);
        })

        closeButton.addEventListener("click", () => {
            mainContent.removeChild(modal);
        })

        return cardView(card, moveCardButton)
    } catch (e) {
        return showErrorResponse(e)
    }
}

export default cardDetailsHandler