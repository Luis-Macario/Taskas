import getListData from "../../data/lists/getListData.js";
import listView from "../../views/lists/ListView.js";
import showErrorResponse from "../../configs/configs.js";


async function listDetailsHandler(mainContent, listID) {
    try {
        const list = await getListData(listID)
        listView(mainContent, list)
    } catch (e) {
        showErrorResponse(mainContent, e)
    }
}

export default listDetailsHandler