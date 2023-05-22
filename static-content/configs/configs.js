import {div, h1, p} from "../DSL/tags.js";

export const hardCodedBearer = "160ee838-150b-4ca1-a2ff-2e964383c315"
export const API_BASE_URL = "http://localhost:8080/api/"

//TODO stashing this here until I decide where to put it
function showErrorResponse(mainContent, error) {
    console.log(error)
    mainContent.replaceChildren(
        div({},
            h1({}, `${error.code} ${error.name}`),
            p({}, `${error.description}`)
        )
    )
}

export function getStoredUser() {
    const user = localStorage.getItem("user");

    if (user == null)
        return null;

    return JSON.parse(user);
}

export function storeUser(user) {
    localStorage.setItem("user", JSON.stringify(user));
}

export default showErrorResponse