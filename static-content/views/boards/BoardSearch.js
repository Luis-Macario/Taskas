import {a, div, h1, form, li, input, label, p, ul} from "../../DSL/tags.js";
import showErrorResponse, {API_BASE_URL, hardCodedBearer} from "../../configs/configs.js";
function renderResults(boards, searchQuery, resultsContainer) {
    resultsContainer.replaceChildren(
        div({class: "container my-4"},
            h1({class: "mb-3"}, `Boards with name containing: "${searchQuery}"`),
            ul({class: "list-group"},
                ...(boards.boards.length > 0 ? boards.boards.map(s => {
                            return li({class: "list-group-item"},
                                a({class: "text-decoration-none", href: "#boards/" + s.id}, "Board " + s.id)
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
}

async function searchBoard(mainContent, id) {
    mainContent.removeChild(mainContent.firstChild)  // Clears the previous mainContent from the other pages

    const myForm = form(
        label({class: "form-label"}, "Board Search: "),
        input({class: "form-control", type: "text", id: "nameBoard"}),
        input({class: "btn btn-primary mt-3", type: "submit"})
    )

    const resultsContainer = div()
    mainContent.append(div({class: "container my-4"}, myForm, resultsContainer))
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