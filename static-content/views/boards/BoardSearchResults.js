import {a, div, h1, li, p, ul} from "../../DSL/tags.js";
import showErrorResponse, {API_BASE_URL, hardCodedBearer} from "../../configs/configs.js";

async function searchBoardResults(mainContent, id, query) {
    const options = {
        method: "GET",
        headers: {
            "Authorization": "Bearer " + hardCodedBearer,
            "Content-Type": "application/json",
            "Accept": "application/json",
        }
    }
    const res = await fetch(API_BASE_URL + `users/${id}/boards/search?search_query=${query}`, options)
    if (res.status === 200) {
        const boards = (await res.json()).boards
        console.log(boards)
        mainContent.replaceChildren(
            div({class: "container my-4"},
                h1({class: "mb-3"}, `Boards with name containing: "${query}"`),
                ul({class: "list-group"},
                    ...(boards.length > 0 ? boards.map(s => {
                                return li({class: "list-group-item"},
                                    //a({class: "text-decoration-none", href: "#boards/" + s.id}, s.name)
                                    a(`#users/${id}/boards/` + s.id, s.name)
                                )
                            }) :
                            [
                                li({class: "list-group-item"},
                                    p({class: "mb-0"}, "No boards found with that name")
                                )
                            ]
                    )
                )
            )
        )
        return
    }
    showErrorResponse(mainContent, await res.json())
}

export default searchBoardResults