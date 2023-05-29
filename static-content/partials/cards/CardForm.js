import {br, button, form, input, label} from "../../DSL/tags.js";


function CardForm(handler) {
    return form({onSubmit: handler},
        br(),
        label({for: "cardName", class: "col-form-label"}, "Name:"), br(),
        input(({
            type: "text", id: "idName", name: "idName",
            class: "form-control",
            placeholder: "Enter the Card name", minlength: "3", maxlength: "60",
            required: true
        })), br(),
        label({for: "cardName", class: "col-form-label"}, "Description:"), br(),
        input(({
            type: "text", id: "idDescription", name: "idDescription",
            class: "form-control",
            placeholder: "Enter the Card Description", minlength: "3", maxlength: "60",
            required: true
        })), br(),
        label({for: "cardInitDate", class: "col-form-label"}, "Init Date:"), br(),
        input(({
            type: "date", id: "idInitDate", name: "idInitDate",
            class: "form-control",
            placeholder: "Enter the Card Init Date", minlength: "3", maxlength: "60",
            required: true
        })), br(),
        label({for: "cardDueDate", class: "col-form-label"}, "Due Date:"), br(),
        input(({
            type: "date", id: "idDueDate", name: "idDueDate",
            class: "form-control",
            placeholder: "Enter the Card Due Date", minlength: "3", maxlength: "60",
            required: true
        })), br(),
        button({type: "submit", class: "btn btn-primary w-100 btn-lg"}, "Create Card")
    )
}

export default CardForm