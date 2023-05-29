import {br, button, div, form, input, li, ul} from "../../DSL/tags.js";

export default  function boardSearchForm() {
    return form({},
        br(),
        div({class: "input-group mb-3 d-flex align-items-center"},
            div({class: "input-group-prepend"},
                div({class: "btn-group flex-grow-1"},
                    button({
                        id: "parameterButton",
                        type: "button",
                        class: "btn btn-secondary dropdown-toggle",
                        'data-bs-toggle': "dropdown",
                        'aria-expanded': "false"
                    }, "Select Parameter"),
                    ul({class: "dropdown-menu", 'aria-labelledby': "dropdownMenuButton1"},
                        li({}, button({class: "dropdown-item", onClick: selectParameter}, "Name")),
                        li({}, button({class: "dropdown-item", disabled: true},
                            "Parameter2")),
                        li({}, button({class: "dropdown-item", disabled: true},
                            "Parameter3")),
                        li({}, button({class: "dropdown-item", disabled: true},
                            "Parameter4"))
                    ),

                    input({
                        type: "text", id: "parameterValue", name: "nameBoard",
                        class: "form-control",
                        placeholder: ` Select a Parameter `, minlength: "3", maxlength: "60",
                        required: true
                    })
                )
            )
        ),
        br(),
        button({type: "submit", class: "btn btn-primary w-100 btn-lg", value: "Submit"}, "Search")
    )
}