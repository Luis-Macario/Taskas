import showErrorResponse from "../../configs/configs.js";
import UserDetailsView from "../../views/users/userDetailsView.js";
import getUser from "../../data/users/getUserData.js";

async function userDetailsHandler(mainContent) {
    try {
        const userData = await getUser()
        const view = UserDetailsView(userData)
        mainContent.replaceChildren(view)
    } catch (e) {
        showErrorResponse(mainContent, e)
    }
}

export default userDetailsHandler