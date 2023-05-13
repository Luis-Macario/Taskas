import fetchAPI from "../../fetchAPI.js";
import {div, h1, li, ul} from "../../DSL/tags.js";
import showErrorResponse, {hardCodedBearer} from "../../configs/configs.js";

function getUser(mainContent, id) {
    fetchAPI("users/" + id, hardCodedBearer)
        .then(user => {
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
