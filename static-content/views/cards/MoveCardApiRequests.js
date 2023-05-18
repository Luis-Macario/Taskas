import showErrorResponse, {API_BASE_URL, hardCodedBearer} from "../../configs/configs.js";

export async function moveCard(id, lId, cidx) {
    const options = {
        method: "POST",
        headers: {
            "Authorization": "Bearer " + hardCodedBearer,
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
        showErrorResponse(mainContent, await (res.json()))
    }
}

export async function getAvailableLists(id) {

    const options = {
        method: "GET",
        headers: {
            "Authorization": "Bearer " + hardCodedBearer,
        }
    }
    console.log("ENTROU GET")

    const res = await fetch(API_BASE_URL + `boards/${id}/lists`, options)
    if (res.status === 200) {
        return (await res.json()).lists
    } else {
        showErrorResponse(mainContent, await (res.json()))
    }
}

export async function getCardsFromList(id) {
    const options = {
        method: "GET",
        headers: {
            "Authorization": "Bearer " + hardCodedBearer,
        }
    }
    console.log("ENTROU GET")

    const res = await fetch(API_BASE_URL + `lists/${id}/cards`, options)
    if (res.status === 200) {
        return (await res.json()).cards
    } else {
        showErrorResponse(mainContent, await (res.json()))
    }
}