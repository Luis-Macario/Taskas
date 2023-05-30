import {API_BASE_URL, getStoredUser} from "../../configs/configs.js";

async function getUser() {
    const user = getStoredUser()
    const id = user.id
    const token = user.token

    const res = await fetch(API_BASE_URL + "users/" + id, {
        headers: {
            "Authorization": "Bearer " + token
        }
    })
    const body = await res.json()
    if (res.status === 200) {
        return body
    } else {
        throw body
    }
}

export default getUser