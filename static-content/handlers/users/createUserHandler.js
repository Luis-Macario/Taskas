import userCreateView from "../../views/users/UserCreateView.js";
import createUser from "../../data/users/createUser.js";
import showErrorResponse from "../../configs/configs.js";

async function createUserHandler(mainContent) {
    try {
        const createUserData = await createUser
        const createUserView = userCreateView(createUserData)
        mainContent.replaceChildren(createUserView)
    } catch (e) {
        showErrorResponse(mainContent, e)
    }

}

export  default createUserHandler