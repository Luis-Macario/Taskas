import showErrorResponse, {API_BASE_URL, getStoredUser} from "../../configs/configs.js";

export async function addUsersBoard(mainContent, bid, uid) {

    const user = getStoredUser()
    const token = user.token

    const options = {
        method: "POST",
        headers: {
            "Authorization": "Bearer " + token,
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            userID: uid
        })
    }

    const res = await fetch(API_BASE_URL + `boards/${bid}/users`, options)
    if (res.status === 204) {
        console.log("User added")
        window.location.reload()
    } else {
        showErrorResponse(mainContent, await (res.json()))
    }
}

export async function getAllUsers(mainContent, boardId) {

    const user = getStoredUser()
    const token = user.token

    const options = {
        method: "GET",
        headers: {
            "Authorization": "Bearer " + token,
        }
    }

    const res = await fetch(API_BASE_URL + `boards/${boardId}/otherUsers`, options)
    if (res.status === 200) {
        return (await res.json()).users
    } else {
        showErrorResponse(mainContent, await (res.json()))
    }
}
