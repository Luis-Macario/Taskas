import createListData from "../../data/lists/createListData.js";
import createListView from "../../views/lists/ListCreate.js";


async function createListHandler(mainContent, boardId) {
    const view = createListView(createListData, boardId)
    mainContent.replaceChildren(view)
}

export default createListHandler