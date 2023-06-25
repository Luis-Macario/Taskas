import createListData from "../../data/lists/createListData.js";
import createListView from "../../views/lists/ListCreate.js";


async function createListHandler(boardId) {
    return createListView(createListData, boardId)
}

export default createListHandler