import {a, div, h1, li} from "../../DSL/tags.js";
import showErrorResponse, {API_BASE_URL, hardCodedBearer} from "../../configs/configs.js";

async function getUsersFromBoard(mainContent, id) {
    const skip = 0
    const limit = 10
    const res = await fetch(API_BASE_URL + `boards/${id}/users?skip=${skip}&limit=${limit}`, {
        headers: {
            "Authorization": "Bearer " + hardCodedBearer
        }
    })
    const body = await res.json()
    if (res.status === 200) {
        const users = body.users
        mainContent.replaceChildren(
            div({class: "card"},
                div({class: "card-header"},
                    h1({class: "card-title"}, "Board Users")
                ),
                a(`#boards/${id}`,"Return to board"),
                ...users.map(user => {
                    return li({}, `${user.name}[${user.id}] : ${user.email} `)
                })
            )
        )
        return
    }
    showErrorResponse(mainContent, body)
}

export default getUsersFromBoard