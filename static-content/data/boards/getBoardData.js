import {API_BASE_URL, getStoredUser} from "../../configs/configs.js";

export default async function getBoardData(id) {

    getStoredUser()

    const user = getStoredUser()
    const token = user.token

    const res = await fetch(API_BASE_URL + `boards/${id}`, {
        headers: {
            "Authorization": "Bearer " + token
        }
    })

    if (res.status === 200) {
        return await res.json()
    }
    throw res

}