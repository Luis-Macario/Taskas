import createCardData from "../../data/cards/createCardData.js";
import createCardView from "../../views/cards/createCardView.js";
import showErrorResponse from "../../configs/configs.js";

async function createCardHandler(mainContent, listID) { //dd-mm-yyyy
    try {
        const today = new Date().toJSON().slice(0, 10)

        const createCardFunction = createCardData(listID, today)
        return createCardView(createCardFunction, today)

    } catch (error) {
        showErrorResponse(error)
    }
}

export default createCardHandler