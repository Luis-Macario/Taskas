import {API_BASE_URL, getStoredUser} from "../../configs/configs.js";

function createCardData(listID, today) {
    function handleSubmit(event) {
        event.preventDefault()

        const name = document.querySelector("#idName").value
        const description = document.querySelector("#idDescription").value
        const initDate = document.querySelector("#idInitDate").value
        const dueDate = document.querySelector("#idDueDate").value

        if (initDate < today) {
            alert("Init Date cannot be lower than current day")
            return
        }

        if (initDate >= dueDate) {
            alert("Due Date can't happen before, or be equal to, Init Date")
            return
        }

        if (name.length < 5 || name.length > 100) {
            alert("Card Name must be between 5 and 100 letters long")
            return;
        }
        // TODO Verificar os dados e lançar os alerts

        const user = getStoredUser()
        const token = user.token

        const options = {
            method: "POST",
            headers: {
                "Authorization": "Bearer " + token,
                "Content-Type": "application/json",
                "Accept": "application/json"
            },
            body: JSON.stringify({
                listID: listID,
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

    return handleSubmit
}

export default createCardData