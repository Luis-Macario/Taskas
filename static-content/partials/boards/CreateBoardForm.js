import {br, button, div, form, input, span} from "../../DSL/tags.js";

export default function createBoardForm(handler) {
    return form({onSubmit: handler()},
        br(),
        div({class: "input-group mb-3"},
            div({class: "input-group-prepend", style: "float:left; width:100px"},
                span({class: "input-group-text", id: "inputGroup-sizing-default"}, "Name")),
            input({
                type: "text", id: "idName", name: "idName",
                class: "form-control",
                placeholder: "Enter the board name", minlength: "3", maxlength: "60",
                required: true
            })
        ),
        div({class: "input-group mb-3"},
            div({class: "input-group-prepend", style: "float:left; width:100px;"},
                span({class: "input-group-text", id: "inputGroup-sizing-default"}, "Description")),
            input({
                type: "text", id: "idDescription", name: "idDescription",
                class: "form-control",
                placeholder: "Enter the board description", minlength: "3", maxlength: "60",
                required: true
            })
        ),
        br(),
        button({type: "submit", class: "btn btn-primary w-100 btn-lg"}, "Create Board")
    )

}