import showErrorResponse from "../configs/configs.js";
import userDetailsView from "../views/users/userDetailsView.js";
import getUser from "../data/userDetails.js";

async function userDetailsHandler(mainContent) {
    try {
        const userData = await getUser()
        userDetailsView(mainContent, userData)
    } catch (e) {
        showErrorResponse(mainContent, e)
    }
}

export default userDetailsHandler