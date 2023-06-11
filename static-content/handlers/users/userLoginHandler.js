import loginUser from "../../data/users/userLoginData.js";
import showErrorResponse from "../../configs/configs.js";

async function userLoginHandler(mainContent) {
    try {
        const view = await loginUser()
        mainContent.replaceChildren(view)
    } catch (error) {
        mainContent.replaceChildren(showErrorResponse(error))
    }
}

export default userLoginHandler