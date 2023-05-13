import {a, div, h1, li} from "../../DSL/tags.js";
import showErrorResponse, {API_BASE_URL, hardCodedBearer} from "../../configs/configs.js";

function getListsFromBoard(mainContent, id) {
    const skip = 0
    const limit = 10
    fetch(API_BASE_URL + `boards/${id}/lists?skip=${skip}&limit=${limit}`, {
        headers: {
            "Authorization": "Bearer " + hardCodedBearer
        }
    }).then(res => {
        if (res.status < 200 || res.status > 299) throw res.json()
        return res.json()
    }).then(lists => {
        mainContent.replaceChildren(
            div(
                h1("Lists"),
                ...lists.lists.map(s => {
                    return li(
                        a("#lists/" + s.id, "List " + s.id)
                    )
                })
            )
        )
    }).catch(e => {
        return e
    }).then(error => {
        showErrorResponse(mainContent, error)
    })
}

export default getListsFromBoard