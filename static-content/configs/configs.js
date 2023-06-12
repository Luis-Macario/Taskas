import {div, h1, p} from "../DSL/tags.js";

export const hardCodedBearer = "160ee838-150b-4ca1-a2ff-2e964383c315"
export const API_BASE_URL = window.location.protocol + '//' + window.location.host + '/api/'

//TODO stashing this here until I decide where to put it
export default function showErrorResponse(error) {
    console.log("Show error message:" + error)
    return div({},
        h1({}, `${error.code} ${error.name}`),
        p({}, `${error.description}`)
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