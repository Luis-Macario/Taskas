import {a, div, h1, li} from "../../DSL/tags.js";
import showErrorResponse, {API_BASE_URL, getStoredUser} from "../../configs/configs.js";

async function getUsersFromBoard(mainContent) {

    const user = getStoredUser()
    const id = user.id
    const token = user.token

    const skip = 0
    const limit = 10
    const res = await fetch(API_BASE_URL + `boards/${id}/users?skip=${skip}&limit=${limit}`, {
        headers: {
            "Authorization": "Bearer " + token
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
                div({class: "card-body"},
                    a({class: "btn btn-secondary", href: `#boards/${id}`}, "Return to board"),
                    ...users.map(user => {
                        return li({}, `${user.name}[${user.id}] : ${user.email} `)
                    })
                )
            )
        )
        return
    }
    showErrorResponse(mainContent, body)
}

export default getUsersFromBoard