import {API_BASE_URL, hardCodedBearer} from "../../configs/configs.js";
import {br, button, div, form, h1,  inputV2,  labelV2} from "../../DSL/tags.js";

async function listcreate(mainContent, id) {
    function handleSubmit(event) {
        event.preventDefault()

        const name = document.querySelector("#idName").value

        console.log(`${name}`)
        /*if (!name.match(/^[a-zA-Z0-9._-]{3,60}$/)) {
            alert("Invalid List Name")
            return;
        }*/

        if (name.length < 5 || name.length > 100) {
            alert("List Name must be between 5 and 100 letters long")
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
                boardID: id,
                name: name,
            })
        }
        fetch(API_BASE_URL + `lists/`, options)
            .then(res => res.json())
            .then(list => {
                console.log(list)
                window.location.hash = `lists/` + list.id
            })
    }

    const myForm = form(
        br(),
        labelV2({for: "listname", class: "col-form-label"}, "Name:"), br(),
        inputV2(({
            type: "text", id: "idName", name: "idName",
            class: "form-control",
            placeholder: "Enter the List name", minlength: "3", maxlength: "60",
            required: true
        })), br(),
        button({type: "submit", class: "btn btn-primary w-100 btn-lg"}, "Create List")
    )

    myForm.addEventListener('submit', handleSubmit)
    mainContent.replaceChildren(
        div({class: "card-header"},
            h1({class: "card-title"}, "Create List")
        ),
        div({class: "card-body w-50 center "},
            myForm
        )
    )
}

export default listcreate