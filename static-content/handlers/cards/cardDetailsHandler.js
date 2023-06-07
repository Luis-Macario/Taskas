import getCard from "../../data/cards/getCard.js";
import cardView from "../../views/cards/cardView.js";
import showErrorResponse from "../../configs/configs.js";

async function cardDetailsHandler(mainContent, cardID) {
    try {
        const card = await getCard(cardID)
        cardView(mainContent, card)
    } catch (e) {
        showErrorResponse(mainContent, e)
    }
}

export default cardDetailsHandler