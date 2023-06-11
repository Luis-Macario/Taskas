import {API_BASE_URL, getStoredUser} from "../../configs/configs.js";

export default async function searchBoardsResultData(query) {
    const user = getStoredUser()
    const id = user.id
    const token = user.token


    const options = {
        method: "GET",
        headers: {
            "Authorization": "Bearer " + token,
            "Content-Type": "application/json",
            "Accept": "application/json",
        }
    }

    const res = await fetch(API_BASE_URL + `users/${id}/boards/search?search_query=${query}`, options)
    const body = (await res.json())

    if (res.status === 200) {
        return body.boards
    }
    throw body
}