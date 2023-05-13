import {div, form, input, label} from "../../DSL/tags.js";

async function searchBoard(mainContent, id) {
    /*
    const myForm = form(
        label({class: "form-label"}, "Board Search: "),
        input({class: "form-control", type: "text", id: "nameBoard"}),
        input({class: "btn btn-primary mt-3", type: "submit"})
    )
     */
    const myForm = form(
        label("Board Search: "),
        input("text", "nameBoard"),
        input("submit")
    )

    const resultsContainer = div()
    mainContent.replaceChildren(div({class: "container my-4"}, myForm, resultsContainer))
    myForm.addEventListener('submit', () => {
        const query = document.querySelector("#nameBoard").value
        window.location.hash = `users/${id}/boards/search/${query}`
    })
}

export default searchBoard