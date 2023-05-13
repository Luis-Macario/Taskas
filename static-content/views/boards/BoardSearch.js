import {a, div, h1, form, li, input, label, tr, td, p} from "../../DSL/tags.js";
import showErrorResponse, {API_BASE_URL, hardCodedBearer} from "../../configs/configs.js";
function renderResults(boards, searchQuery, resultsContainer) {
    resultsContainer.replaceChildren(
        div(
            h1(`Boards with name containing: "${searchQuery}"`),
            ...boards.boards.length > 0 ? boards.boards.map(s => {
                    return li(
                        a("#boards/" + s.id, "Board " + s.id)
                    )
                }) :
                [
                    tr(
                        td(
                            p("No boards Found with that name")
                        )
                    )
                ]
        )
    )
}

async function searchBoard(mainContent, id) {
    mainContent.removeChild(mainContent.firstChild)  // Clears the previous mainContent from the other pages

    const myForm = form(
        label("Board Search: "),
        input("text", "nameBoard"),
        input("submit")
    )

    const resultsContainer = div()
    mainContent.append(myForm, resultsContainer)
    myForm.addEventListener('submit', handleSubmit)

    function handleSubmit(e) {
        e.preventDefault()
        const inputName = document.querySelector("#nameBoard")
        console.log(inputName.value)
        const options = {
            method: "GET",
            headers: {
                "Authorization": "Bearer " + hardCodedBearer,
                "Content-Type": "application/json",
                "Accept": "application/json",
            }
        }
        fetch(API_BASE_URL + `users/${id}/boards/search?search_query=${inputName.value}`, options)
            .then(res => res.json())
            .then(boards => {
                console.log(boards)
                renderResults(boards, inputName.value, resultsContainer)
            }).catch(e => {
            return e
        }).then(error => {
            showErrorResponse(mainContent, error)
        })
    }
}



export default searchBoard