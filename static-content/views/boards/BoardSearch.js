import {br, button, div, form, h1, input, li, ul} from "../../DSL/tags.js";

function searchBoard() {
    let selectedParameter = null

    const myForm = form({},
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

    function selectParameter() {
        //TODO:Review Implementations
        selectedParameter = this.innerText
        document.querySelector("#parameterButton").innerText = selectedParameter;
        document.querySelector("#parameterValue").placeholder = `Insert desired ${selectedParameter}`;
    }

    const resultsContainer = div()

    myForm.addEventListener('submit', (event) => {
        event.preventDefault(); // Prevent form submission
        const inputValue = document.querySelector("#parameterValue").value;

        switch (selectedParameter) {
            case 'Name':
                window.location.hash = `users/boards/search/${inputValue}`
                break
            case 'id':
                window.location.hash = undefined
                break
            case 'Number of Users': //TODO deviamos inserir o criterio de busca numa query string
                // Queries vêm antes de um hash, nao faz muito sentido ter 8080/?q=123#users/etc/etc
                window.location.hash = `users/boards/search/${inputValue}`;
                break;
            default:
                alert("Invalid Parameter");
        }
    });

    return div({class: "card"},
            div({class: "card-header"},
                h1({class: "card-title"}, "Search Board")
            ),
            div({class: "card-body w-50 center"},
                myForm, resultsContainer
            )
        )

}

export default searchBoard