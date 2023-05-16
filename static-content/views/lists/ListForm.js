import {br, button, form, inputV2, labelV2} from "../../DSL/tags.js";


function ListForm() {
    return form({},
        br(),
        labelV2({for: "listname", class: "col-form-label"}, "Name:"), br(),
        inputV2(({
            type: "text", id: "idName", name: "idName",
            class: "form-control",
            placeholder: "Enter the List name", minlength: "3", maxlength: "60",
            required: true
        })), br(),
        button({type: "submit", class: "btn btn-primary w-100 btn-lg"}, "Create List")
    )
}

export default ListForm