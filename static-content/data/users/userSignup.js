import {API_BASE_URL, storeUser} from "../../configs/configs.js";
import errorModal from "../../partials/errorModal.js";
import UserSignupForm from "../../partials/users/UserSignupForm.js";

export default function userSignup() {
    async function handleSignup(event) {
        event.preventDefault()
        const form = event.target

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
            //console.log(body)
            storeUser(body)
            window.location.hash = "users/me"
        } else {
            errorModal(form, body)
        }
    }

    return UserSignupForm(handleSignup)
}