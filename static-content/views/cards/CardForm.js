import {br, button, form, inputV2, labelV2} from "../../DSL/tags.js";


function CardForm() {
    return form({},
        br(),
        labelV2({for: "cardName", class: "col-form-label"}, "Name:"), br(),
        inputV2(({
            type: "text", id: "idName", name: "idName",
            class: "form-control",
            placeholder: "Enter the Card name", minlength: "3", maxlength: "60",
            required: true
        })), br(),
        labelV2({for: "cardName", class: "col-form-label"}, "Description:"), br(),
        inputV2(({
            type: "text", id: "idDescription", name: "idDescription",
            class: "form-control",
            placeholder: "Enter the Card Description", minlength: "3", maxlength: "60",
            required: true
        })), br(),
        labelV2({for: "cardInitDate", class: "col-form-label"}, "Init Date:"), br(),
        inputV2(({
            type: "date", id: "idInitDate", name: "idInitDate",
            class: "form-control",
            placeholder: "Enter the Card Init Date", minlength: "3", maxlength: "60",
            required: true
        })), br(),
        labelV2({for: "cardDueDate", class: "col-form-label"}, "Due Date:"), br(),
        inputV2(({
            type: "date", id: "idDueDate", name: "idDueDate",
            class: "form-control",
            placeholder: "Enter the Card Due Date", minlength: "3", maxlength: "60",
            required: true
        })), br(),
        button({type: "submit", class: "btn btn-primary w-100 btn-lg"}, "Create Card")
    )
}

export default CardForm