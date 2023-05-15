import {br, button, div, form, h1, inputV2, span} from "../../DSL/tags.js";

async function searchBoard(mainContent, id) {

    const myForm = form({},
        br(),
        div({class: "input-group mb-3"},
            div({class: "input-group-prepend", style: "float:left; width:100px;"},
                span({class: "input-group-text", id: "inputGroup-sizing-default"}, "Name")),
            inputV2({
                type: "text", id: "nameBoard", name: "nameBoard",
                class: "form-control",
                placeholder: "Enter the board name you want to search", minlength: "3", maxlength: "60",
                required: true
            })
        ),
        br(),
        button({type: "submit", class: "btn btn-primary w-100 btn-lg", value: "Submit"}, "Search")
    )

    const resultsContainer = div()
    mainContent.replaceChildren(
        div({class: "card"},
            div({class: "card-header"},
                h1({class: "card-title"}, "Search Board")
            ),
            div({class: "card-body w-50 center"},
                myForm, resultsContainer
            )
        )
    )
    myForm.addEventListener('submit', () => {
        const query = document.querySelector("#nameBoard").value
        window.location.hash = `users/${id}/boards/search/${query}`
    })
}

export default searchBoard