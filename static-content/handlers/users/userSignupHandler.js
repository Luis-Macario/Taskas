import UserSignupView from "../../views/users/UserSignupView.js";
import userSignupData from "../../data/users/userSignupData.js";
import showErrorResponse from "../../configs/configs.js";

async function userSignupHandler(mainContent) {
    try {
        const signupFunction = userSignupData
        const userSignupDataView = UserSignupView(signupFunction)
        mainContent.replaceChildren(userSignupDataView)
    } catch (error) {
        mainContent.replaceChildren(showErrorResponse(error))
    }

}

export default userSignupHandler