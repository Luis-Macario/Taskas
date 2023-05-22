import {div,h1} from "../../DSL/tags.js";
import {API_BASE_URL, storeUser} from "../../configs/configs.js";
import UserLoginForm from "../../partials/users/UserLoginForm.js";

function loginUser(mainContent) {
    function handleSubmit(event) {
        event.preventDefault()

        const email = document.querySelector("#idEmail").value
        const password = document.querySelector("#idPassword").value


        if (!email.match(/^[A-Za-z\d+_.-]+@(.+)$/)) {
            alert("Invalid email")
            return;
        }

        if (password.length < 4 || password.length > 30) {
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
                email: email,
                password: password
            })
        }


        fetch(API_BASE_URL + "users/login", options)
            .then(res => res.json())
            .then(user => {
                console.log(user)
                storeUser(user)
                window.location.hash = "users/" + user.id
            })
    }

    mainContent.replaceChildren(
        div({class: "card"},
            div({class: "card-header"},
                h1({class: "card-title"}, "Login User")
            ),
            div({class: "card-body w-50 center"},
                UserLoginForm(handleSubmit)
            )
        )
    )
}

export default loginUser