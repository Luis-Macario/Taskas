import {br, button, form, input, label} from "../../DSL/tags.js";


function ListForm( handler) {
    return form({onSubmit:handler},
        br(),
        label({for: "listname", class: "col-form-label"}, "Name:"), br(),
        input(({
            type: "text", id: "idName", name: "idName",
            class: "form-control",
            placeholder: "Enter the List name", minlength: "3", maxlength: "60",
            required: true
        })), br(),
        button({type: "submit", class: "btn btn-primary w-100 btn-lg"}, "Create List")
    )
}

export default ListForm