import createList from "../../data/lists/createList.js";
import createListView from "../../views/lists/ListCreate.js";


async function createListHandler(mainContent, boardId) {
    const view = createListView(createList, boardId)
    mainContent.replaceChildren(view)
}

export default createListHandler