import {a, div, h1, li, p} from "../../DSL/tags.js";
import showErrorResponse, {hardCodedBearer} from "../../configs/configs.js";
import fetchAPI from "../../fetchAPI.js";

function getUsersFromBoard(mainContent, id) {
    const skip = 0
    const limit = 10
    fetchAPI(`boards/${id}/users?skip=${skip}&limit=${limit}`, hardCodedBearer)
        .then(users => {
            mainContent.replaceChildren(
                div(
                    p(a(`#boards/${id}`, "Return to board")),
                    h1("Board Users"),
                    ...users.users.map(user => {
                        return li(`${user.name}[${user.id}] : ${user.email} `)
                    })
                )
            )
        }).catch(e => {
        return e
    }).then(error => {
        showErrorResponse(mainContent, error)
    })
}

export default getUsersFromBoard