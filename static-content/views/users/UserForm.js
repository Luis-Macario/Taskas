import {br, button, div, form, inputV2, span} from "../../DSL/tags.js";

function UserForm() {
    return form({},
        br(),
        div({class: "input-group mb-3"},
            div({class: "input-group-prepend", style: "float:left; width:100px"},
                span({class: "input-group-text", id: "inputGroup-sizing-default"}, "Name")),
            br(),
            inputV2({
                type: "text", id: "idName", name: "idName",
                class: "form-control",
                placeholder: "Enter your name", minlength: "3", maxlength: "50",
                required: true
            })
        ),
        div({class: "input-group mb-3"},
            div({class: "input-group-prepend", style: "float:left; width:100px"},
                span({class: "input-group-text", id: "inputGroup-sizing-default"}, "Email")),
            br(),
            inputV2({
                type: "text", id: "idEmail", name: "idEmail",
                class: "form-control",
                placeholder: "Enter your email", minlength: "3", maxlength: "60",
                required: true
            })
        ),
        br(),
        button({type: "submit", class: "btn btn-primary w-100 btn-lg"}, "Register")
    )
}
export default UserForm