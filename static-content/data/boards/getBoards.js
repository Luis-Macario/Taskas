import {API_BASE_URL, getStoredUser} from "../../configs/configs.js";

export default async function getBoards() {
    const user = getStoredUser()
    const id = user.id
    const token = user.token

    const skip = 0
    const limit = 10
    const res = await fetch(API_BASE_URL + `users/${id}/boards?skip=${skip}&limit=${limit}`, {
        headers: {
            "Authorization": "Bearer " + token
        }
    })

    if (res.status === 200) {
        return await res.json()
    }
    throw res

}