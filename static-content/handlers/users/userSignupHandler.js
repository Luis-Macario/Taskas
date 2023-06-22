import UserSignupView from "../../views/users/UserSignupView.js";
import userSignupData from "../../data/users/userSignupData.js";
import showErrorResponse from "../../configs/configs.js";

async function userSignupHandler() {
    try {
        const signupFunction = userSignupData
        return UserSignupView(signupFunction)
    } catch (error) {
        showErrorResponse(error)
    }

}

export default userSignupHandler