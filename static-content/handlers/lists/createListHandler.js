import createList from "../../data/lists/createList.js";
import createListView from "../../views/lists/ListCreate.js";


function createListHandler(mainContent, boardID) {
    const createListFunction = createList(boardID)
    const view = createListView(createListFunction)
    mainContent.replaceChildren(view)
}

export default createListHandler