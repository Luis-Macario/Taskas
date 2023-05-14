import {API_BASE_URL, hardCodedBearer} from "../../configs/configs.js";
import {br, button, div, form, h1,  inputV2,  labelV2} from "../../DSL/tags.js";

async function cardCreate(mainContent, id) {
    function handleSubmit(event) {
        event.preventDefault()

        const name = document.querySelector("#idName").value
        const description = document.querySelector("#idDescription").value
        const initDate = "2023-04-28" //document.querySelector("#idInitDate").value
        const dueDate = "2025-04-30"//document.querySelector("#idDuetDate").value

        /*if (!name.match(/^[a-zA-Z0-9._-]{3,60}$/)) {
            alert("Invalid List Name")
            return;
        }*/

        if (name.length < 5 || name.length > 100) {
            alert("Card Name must be between 5 and 100 letters long")
            return;
        }
        // TODO Verificar os dados e lançar os alerts

        const options = {
            method: "POST",
            headers: {
                "Authorization": "Bearer " + hardCodedBearer,
                "Content-Type": "application/json",
                "Accept": "application/json"
            },
            body: JSON.stringify({
                listID: id,
                name: name,
                description: description,
                initDate: initDate,
                dueDate: dueDate
            })
        }
        fetch(API_BASE_URL + `cards/`, options)
            .then(res => res.json())
            .then(card => {
                console.log(card)
                window.location.hash = `cards/` + card.id
            })
    }

    const myForm = form(
        br(),
        labelV2({for: "cardName", class: "col-form-label"}, "Name:"), br(),
        inputV2(({
            type: "text", id: "idName", name: "idName",
            class: "form-control",
            placeholder: "Enter the Card name", minlength: "3", maxlength: "60",
            required: true
        })), br(),
        labelV2({for: "cardName", class: "col-form-label"}, "Description:"), br(),
        inputV2(({
            type: "text", id: "idDescription", name: "idDescription",
            class: "form-control",
            placeholder: "Enter the Card Description", minlength: "3", maxlength: "60",
            required: true
        })), br(),
        labelV2({for: "cardInitDate", class: "col-form-label"}, "Init Date:"), br(),
        inputV2(({
            type: "text", id: "idInitDate", name: "idInitDate",
            class: "form-control",
            placeholder: "Enter the Card Init Date", minlength: "3", maxlength: "60",
            required: true
        })), br(),
        labelV2({for: "cardDueDate", class: "col-form-label"}, "Due Date:"), br(),
        inputV2(({
            type: "text", id: "idDueDate", name: "idDueDate",
            class: "form-control",
            placeholder: "Enter the Card Due Date", minlength: "3", maxlength: "60",
            required: true
        })), br(),
        button({type: "submit", class: "btn btn-primary w-100 btn-lg"}, "Create List")
    )

    myForm.addEventListener('submit', handleSubmit)
    mainContent.replaceChildren(
        div({class: "card-header"},
            h1({class: "card-title"}, "Create Card")
        ),
        div({class: "card-body w-50 center"},
            myForm
        )
    )
}

export default cardCreate