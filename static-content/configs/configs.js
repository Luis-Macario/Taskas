import {div, h1, p} from "../DSL/tags.js";

export const hardCodedBearer = "160ee838-150b-4ca1-a2ff-2e964383c315"

//TODO stashing this here until I decide where to put it
function showErrorResponse(mainContent, error) {
    console.log(error)
    mainContent.replaceChildren(
        div(
            h1(`${error.code} ${error.name}`),
            p(`${error.description}`)
        )
    )
}

export default showErrorResponse