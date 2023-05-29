import getList from "../data/getList.js";
import listView from "../views/lists/listView.js";
import showErrorResponse from "../configs/configs.js";


async function listDetailsHandler(mainContent, listID) {
    try {
        console.log("listDetailsHandler Called")
        const list = await getList(listID)
        listView(mainContent, list)
    } catch(e) {
        showErrorResponse(mainContent, e)
    }
}

export default listDetailsHandler