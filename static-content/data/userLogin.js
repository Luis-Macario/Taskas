import {API_BASE_URL, storeUser} from "../configs/configs.js";

function loginUser() {
    async function handleSubmit(event) {
        event.preventDefault()

        const email = document.querySelector("#idEmail").value
        const password = document.querySelector("#idPassword").value


        if (!email.match(/^[A-Za-z\d+_.-]+@(.+)$/)) {
            alert("Invalid email")
            return;
        }

        if (password.length < 4 || password.length > 30) {
            alert("Password must be between 5 and 29 letters long")
            return;
        }

        const options = {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json"
            },
            body: JSON.stringify({
                email: email,
                password: password
            })
        }


        const res = await fetch(API_BASE_URL + "users/login", options)
        const body = await res.json()
        if(res.status === 200) {
            storeUser(body)
            window.location.hash = "users/me"
        }
        else {
            throw body
        }
    }
    return handleSubmit()
}

export default loginUser