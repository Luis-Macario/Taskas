import {API_BASE_URL, getStoredUser} from "../../configs/configs.js";


async function getCard(cardID) {
    const user = getStoredUser()
    const token = user.token

    const res = await fetch(API_BASE_URL + `cards/${cardID}`, {
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

export default getCard