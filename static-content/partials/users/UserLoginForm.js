import {br, button, div, form, h1, input, span} from "../../DSL/tags.js";

function UserLoginForm(handler){
    console.log("UserLoginForm")
    return div({},
        div({class: "card"},
            div({class: "card-header"},
                h1({class: "card-title"}, "Login User")
            ),
            div({class: "card-body w-50 center"},
                form({onSubmit:handler},
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
                            type: "password", id: "idPassword", name: "idPassword",
                            class: "form-control",
                            placeholder: "Enter your password", minlength: "4", maxlength: "30",
                            required: true
                        })
                    ),
                    br(),
                    button({type: "submit", class: "btn btn-primary w-100 btn-lg"}, "Login")
                )
            )
        )
    )
}

export default UserLoginForm