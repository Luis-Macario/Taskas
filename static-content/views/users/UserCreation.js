import {br, button, div, form, h1, input, inputV2, label, labelV2} from "../../DSL/tags.js";
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
        br(),
        labelV2({for: "userName", class: "col-form-label"}, "Name:"), br(),
        inputV2(({
            type: "text", id: "idName", name: "idName",
            class: "form-control",
            placeholder: "Enter your name", minlength: "3", maxlength: "50",
            required: true
        })),
        labelV2({for: "userEmail", class: "col-form-label"}, "Email:"), br(),
        inputV2(({
            type: "text", id: "idEmail", name: "idEmail",
            class: "form-control",
            placeholder: "Enter your email", minlength: "3", maxlength: "60",
            required: true
        })), br(),
        button({type: "submit", class: "btn btn-primary w-100 btn-lg"}, "Register")
    )

    myForm.addEventListener('submit', handleSubmit)
    mainContent.replaceChildren(
        div({class: "card-header"},
            h1({class: "card-title"}, "Register User")
        ),
        myForm
    )
}

export default createUser