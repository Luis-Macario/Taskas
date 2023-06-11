import getCardData from "../../data/cards/getCardData.js";
import cardView from "../../views/cards/cardView.js";
import showErrorResponse from "../../configs/configs.js";

async function cardDetailsHandler(mainContent, cardID) {
    try {
        const card = await getCardData(cardID)
        cardView(mainContent, card)
    } catch (e) {
        showErrorResponse(mainContent, e)
    }
}

export default cardDetailsHandler