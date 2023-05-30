import createCard from "../../data/cards/createCard.js";
import createCardView from "../../views/lists/createCardView.js";

function createCardHandler(mainContent, listID) {
    const createCardFunction = createCard(listID)
    const view = createCardView(createCardFunction)
    mainContent.replaceChildren(view)
}

export default createCardHandler