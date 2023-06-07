import userSignupView from "../../views/users/UserSignupView.js";
import userSignup from "../../data/users/userSignup.js";
import showErrorResponse from "../../configs/configs.js";

async function userSignupHandler(mainContent) {
    try {
        const userSignupDataView = userSignupView(userSignup)
        mainContent.replaceChildren(userSignupDataView)
    } catch (error) {
        mainContent.replaceChildren(showErrorResponse(error))
    }

}

export default userSignupHandler