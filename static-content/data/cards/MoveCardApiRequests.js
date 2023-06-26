import {API_BASE_URL, getStoredUser} from "../../configs/configs.js";

export async function moveCard(mainContent, id, lId, cidx) {

    const user = getStoredUser()
    const token = user.token

    const options = {
        method: "POST",
        headers: {
            "Authorization": "Bearer " + token,
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            listID: lId,
            cix: cidx
        })
    }

    const res = await fetch(API_BASE_URL + `cards/${id}/move`, options)
    if (res.status === 204) {
        window.location.hash = `lists/` + lId
    } else {
        throw (await (res.json()))
    }
}

export async function getAvailableLists(mainContent, id) {

    const user = getStoredUser()
    const token = user.token

    const options = {
        method: "GET",
        headers: {
            "Authorization": "Bearer " + token
        }
    }

    const res = await fetch(API_BASE_URL + `boards/${id}/lists`, options)
    const body = (await res.json())
    if (res.status === 200) {
        return body.lists
    } else {
        throw body
    }
}

export async function getCardsFromList(id) {

    const user = getStoredUser()
    const token = user.token

    const options = {
        method: "GET",
        headers: {
            "Authorization": "Bearer " + token,
        }
    }
    const res = await fetch(API_BASE_URL + `lists/${id}/cards`, options)
    const body = (await res.json())
    if (res.status === 200) {
        return body.cards
    }
    throw body
}