import showErrorResponse from "../../configs/configs.js";
import UserDetailsView from "../../views/users/userDetailsView.js";
import getUser from "../../data/users/getUserData.js";

async function userDetailsHandler() {
    try {
        const userData = await getUser()
        return UserDetailsView(userData)
    } catch (e) {
        return showErrorResponse(e)
    }
}

export default userDetailsHandler