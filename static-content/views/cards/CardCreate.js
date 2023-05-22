import {API_BASE_URL, hardCodedBearer} from "../../configs/configs.js";
import { div, h1} from "../../DSL/tags.js";
import CardForm from "../../partials/cards/CardForm.js";

async function cardCreate(mainContent, id) {
    function handleSubmit(event) {
        event.preventDefault()

        const name = document.querySelector("#idName").value
        const description = document.querySelector("#idDescription").value
        const initDate = document.querySelector("#idInitDate").value
        const dueDate = document.querySelector("#idDueDate").value

        if(initDate >= dueDate){
            alert("Due Date can't happen before, or be equal to, Init Date")
            return
        }

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

    mainContent.replaceChildren(
        div({class: "card-header"},
            h1({class: "card-title"}, "Create Card")
        ),
        div({class: "card-body w-50 center"},
            CardForm(handleSubmit)
        )
    )
}

export default cardCreate