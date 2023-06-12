import loginUser from "../../data/users/userLoginData.js";
import showErrorResponse from "../../configs/configs.js";
import UserLoginView from "../../views/users/UserLoginView.js";

async function userLoginHandler(mainContent) {
    try {
        const loginFuncction = loginUser
        const view = await UserLoginView(loginFuncction)
        mainContent.replaceChildren(view)
    } catch (error) {
        mainContent.replaceChildren(showErrorResponse(error))
    }
}

export default userLoginHandler