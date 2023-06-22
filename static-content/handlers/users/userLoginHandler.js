import loginUser from "../../data/users/userLoginData.js";
import showErrorResponse from "../../configs/configs.js";
import UserLoginView from "../../views/users/UserLoginView.js";

async function userLoginHandler() {
    try {
        const loginFunction = loginUser
        return UserLoginView(loginFunction)
    } catch (error) {
        return showErrorResponse(error)
    }
}

export default userLoginHandler