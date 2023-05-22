import {br, button, div, form, input, span} from "../../DSL/tags.js";

function UserLoginForm(handler) {
    return form({onSubmit:handler},
        br(),
        div({class: "input-group mb-3"},
            div({class: "input-group-prepend", style: "float:left; width:100px"},
                span({class: "input-group-text", id: "inputGroup-sizing-default"}, "Email")),
            br(),
            input({
                type: "text", id: "idEmail", name: "idEmail",
                class: "form-control",
                placeholder: "Enter your email", minlength: "3", maxlength: "60",
                required: true
            })
        ),
        div({class: "input-group mb-3"},
            div({class: "input-group-prepend", style: "float:left; width:100px"},
                span({class: "input-group-text", id: "inputGroup-sizing-default"}, "Password")),
            br(),
            input({
                type: "text", id: "idPassword", name: "idPassword",
                class: "form-control",
                placeholder: "Enter your password", minlength: "4", maxlength: "30",
                required: true
            })
        ),
        br(),
        button({type: "submit", class: "btn btn-primary w-100 btn-lg"}, "Login")
    )
}

export default UserLoginForm