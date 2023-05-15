import {API_BASE_URL, hardCodedBearer} from "../../configs/configs.js";
import {br, button, div, form, h1, inputV2,  span} from "../../DSL/tags.js";

async function boardCreate(mainContent) {
    function handleSubmit(event) {
        event.preventDefault()

        const name = document.querySelector("#idName").value
        const description = document.querySelector("#idDescritpion").value

        console.log(`${name} --- ${description}`)
        /*if (!name.match(/^[a-zA-Z0-9._-]{3,60}$/)) {
            alert("Invalid Board Name")
            return;
        }*/

        if (name.length < 5 || name.length > 100) {
            alert("Board Name must be between 5 and 100 letters long")
            return;
        }

        /*if (!description.match(/^[a-zA-Z0-9._-]{3,60}$/)) {
            alert("Invalid Board Description")
            return;
        }*/

        if (description.length < 0 || description.length > 1000) {
            alert("Description must be between 0 and 1000 letters long")
            return;
        }

        const options = {
            method: "POST",
            headers: {
                "Authorization": "Bearer " + hardCodedBearer,
                "Content-Type": "application/json",
                "Accept": "application/json"
            },
            body: JSON.stringify({
                name: name,
                description: description
            })
        }
        fetch(API_BASE_URL + `boards/`, options)
            .then(res => res.json())
            .then(board => {
                console.log(board)
                window.location.hash = "boards/" + board.id
            })
    }

    const myForm = form({},
        br(),
        div({class: "input-group mb-3"},
            div({class: "input-group-prepend", style: "float:left; background: red; width:100px"},
                span({class: "input-group-text", id: "inputGroup-sizing-default"}, "Name")),
            inputV2(({
                type: "text", id: "idName", name: "idName",
                class: "form-control",
                placeholder: "Enter the board name", minlength: "3", maxlength: "60",
                required: true
            }))
        ),
        div({class: "input-group mb-3"},
            div({class: "input-group-prepend", style: "float:left; background: red; width:100px;"},
                span({class: "input-group-text", id: "inputGroup-sizing-default"}, "Descritpion")),
            inputV2(({
                type: "text", id: "idDescritpion", name: "idDescritpion",
                class: "form-control",
                placeholder: "Enter the board description", minlength: "3", maxlength: "60",
                required: true
            }))
        ),
        // TODO Question -> Preferem como?
        /*labelV2({for: "boardDescritpion", class: "col-form-label"}, "Description:"), br(),
        inputV2(({
            type: "text", id: "idDescritpion", name: "idDescritpion",
            class: "form-control",
            placeholder: "Enter the board description", minlength: "3", maxlength: "60",
            required: true
        })),*/
        br(),
        button({type: "submit", class: "btn btn-primary w-100 btn-lg"}, "Create Board")
    )

    myForm.addEventListener('submit', handleSubmit)
    mainContent.replaceChildren(
        div({class: "card-header"},
            h1({class: "card-title"}, "Create Board")
        ),
        div({class: "card-body w-50 center"},
            myForm
        )
    )
}

export default boardCreate