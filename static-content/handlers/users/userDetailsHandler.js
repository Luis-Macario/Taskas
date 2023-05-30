import showErrorResponse from "../../configs/configs.js";
import UserDetailsView from "../../views/users/userDetailsView.js";
import getUser from "../../data/users/userDetails.js";

async function userDetailsHandler(mainContent) {
    try {
        const userData = await getUser()
        UserDetailsView(mainContent, userData)
    } catch (e) {
        showErrorResponse(mainContent, e)
    }
}

export default userDetailsHandler