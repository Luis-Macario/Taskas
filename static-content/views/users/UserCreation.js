import {form, input, label} from "../../DSL/tags.js";
import {API_BASE_URL} from "../../configs/configs.js";

function createUser(mainContent) {
    function handleSubmit(event) {
        event.preventDefault()

        const username = document.querySelector("#idName").value
        const email = document.querySelector("#idEmail").value

        if (!username.match(/^[a-zA-Z0-9._-]{3,60}$/)) {
            alert("Invalid username")
            return;
        }

        if (username.length < 3 || username.length > 50) {
            alert("Username must be between 3 and 50 letters long")
            return;
        }

        if (!email.match(/^[A-Za-z\d+_.-]+@(.+)$/)) {
            alert("Invalid email")
            return;
        }

        const options = {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json"
            },
            body: JSON.stringify({
                name: username,
                email: email
            })
        }
        fetch(API_BASE_URL + "users/", options)
            .then(res => res.json())
            .then(user => {
                console.log(user)
                window.location.hash = "users/" + user.id
            })
    }

    const myForm = form(
        label("Name"),
        input("text", "idName"),
        label("Email"),
        input("text", "idEmail"),
        input("submit")
    )

    myForm.addEventListener('submit', handleSubmit)
    mainContent.replaceChildren(
        myForm,
    )
}

export default createUser