import {API_BASE_URL, getStoredUser} from "../../configs/configs.js";

export default function createBoardData() {
    function handleSubmit(event) {
        event.preventDefault()

        const user = getStoredUser()
        const token = user.token

        const name = document.querySelector("#idName").value
        const description = document.querySelector("#idDescription").value

        if (name.length <= 5 || name.length >= 100) {
            alert("Board Name must be between 6 and 100 letters long")
            return;
        }

        if (description.length <= 0 || description.length >= 1000) {
            alert("Description must be between 0 and 1000 letters long")
            return;
        }

        const options = {
            method: "POST",
            headers: {
                "Authorization": "Bearer " + token,
                "Content-Type": "application/json",
                "Accept": "application/json"
            },
            body: JSON.stringify({
                name: name,
                description: description
            })
        }
        console.log(name + " " + description)
        fetch(API_BASE_URL + `boards/`, options)
            .then(res => res.json())
            .then(board => {
                console.log(board)
                window.location.hash = "boards/" + board.id
            })
    }

    return handleSubmit
}