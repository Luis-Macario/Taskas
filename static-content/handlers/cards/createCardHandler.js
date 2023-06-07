import createCard from "../../data/cards/createCard.js";
import createCardView from "../../views/cards/createCardView.js";

function createCardHandler(mainContent, listID) {

    const today = new Date().toJSON().slice(0, 10)

    const createCardFunction = createCard(listID, today)
    const view = createCardView(createCardFunction)

    mainContent.replaceChildren(view)
    document.getElementById("idInitDate").value = today
}

export default createCardHandler