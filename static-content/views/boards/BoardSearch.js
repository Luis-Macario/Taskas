import {br, button, div, form, h1,  inputV2,  labelV2} from "../../DSL/tags.js";

async function searchBoard(mainContent, id) {

    const myForm = form(
        br(),
        labelV2({for: "boardName", class: "col-form-label"}, "Name:"), br(),
        inputV2(({
            type: "text", id: "nameBoard", name: "nameBoard",
            class: "form-control",
            placeholder: "Enter the board name you want to search", minlength: "3", maxlength: "60",
            required: true
        })), br(),
        button({type: "submit", class: "btn btn-primary", value: "Submit"}, "Search")
    )

    const resultsContainer = div()
    mainContent.replaceChildren(
        div({class: "card-header"},
            h1({class: "card-title"}, "Search Board")
        ),
        div({class: "card-body w-50 center"},
            myForm, resultsContainer
        )
        //div({class: "container my-4"}, myForm, resultsContainer)
    )
    myForm.addEventListener('submit', () => {
        const query = document.querySelector("#nameBoard").value
        window.location.hash = `users/${id}/boards/search/${query}`
    })
}

export default searchBoard