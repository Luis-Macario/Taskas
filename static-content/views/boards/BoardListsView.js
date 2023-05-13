import {a, div, h1, li} from "../../DSL/tags.js";
import showErrorResponse, {hardCodedBearer} from "../../configs/configs.js";

function getListsFromBoard(mainContent, id) {
    const skip = 0
    const limit = 10
    fetchAPI(`boards/${id}/lists?skip=${skip}&limit=${limit}`,hardCodedBearer)
        .then(lists => {
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