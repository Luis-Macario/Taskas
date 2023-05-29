import {API_BASE_URL, getStoredUser} from "../configs/configs.js";

function createList(boardID) {
    function handleSubmit(event) {
        event.preventDefault()

        const user = getStoredUser()
        const token = user.token

        const listName = document.querySelector("#idName").value

        if (listName.length < 5 || listName.length > 100) {
            alert("List Name must be between 6 and 99 letters long")
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
                boardID: boardID,
                name: listName,
            })
        }
        fetch(API_BASE_URL + `lists/`, options)
            .then(res => res.json())
            .then(list => {
                console.log(list)
                window.location.hash = `lists/` + list.id
            })
    }

    return handleSubmit
}

export default createList