import userLoginView from "../views/users/userLoginView.js";
import loginUser from "../data/userLogin.js";
import showErrorResponse from "../configs/configs.js";

function userLoginHandler(mainContent) {
    try {
        const loginUserData = loginUser()
        const view = userLoginView(loginUserData)
        mainContent.replaceChildren(view)
    } catch (e) {
        showErrorResponse(mainContent, e)
    }
}

export  default userLoginHandler