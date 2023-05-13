import fetchAPI from "../../fetchAPI.js";
import {div, h1, li, ul} from "../../DSL/tags.js";
import showErrorResponse, {API_BASE_URL, hardCodedBearer} from "../../configs/configs.js";

function getUser(mainContent, id) {
    fetch(API_BASE_URL + "users/" + id, {
        headers: {
            "Authorization": "Bearer " + hardCodedBearer
        }
    }).then(res => {
        if (res.status !== 200) throw res.json()
        return res.json()
    }).then(user => {
        mainContent.replaceChildren(
            div(
                h1("User Details"),
                ul(
                    li(`Name: ${user.name}`),
                    li(`Email: ${user.email}`),
                    li(`id: ${user.id}`),
                )
            )
        )
    }).catch(e => {
        return e
    }).then(error => {
        showErrorResponse(mainContent, error)
    })
}

export default getUser
