import {API_BASE_URL} from "../configs/configs.js";

function createUser() {
    async function handleSubmit(event) {
        event.preventDefault()

        const username = document.querySelector("#idName").value
        const email = document.querySelector("#idEmail").value
        const password = document.querySelector("#idPassword").value

        if (username.length < 3 || username.length > 50) {
            alert("Username must be between 3 and 50 letters long")
            return;
        }

        if (!email.match(/^[A-Za-z\d+_.-]+@(.+)$/)) {
            alert("Invalid email")
            return;
        }

        if (password.length < 6 || password.length > 30) {
            alert("Password must be between 6 and 30 letters long")
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
                email: email,
                password: password
            })
        }

        const res = await fetch(API_BASE_URL + "users/", options)
        const body = await res.json()

        if (res.status === 201) {
            console.log(body)
            window.location.hash = "users/me"
        } else {
            throw body
        }
    }

    return handleSubmit()
}

export default createUser