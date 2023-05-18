import {a, br, button, div, form, h1, input, li, ul} from "../../DSL/tags.js";

async function searchBoard(mainContent, id) {

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
                        li({}, a({class: "dropdown-item", onClick: selectParameter}, "Name")),
                        li({}, a({class: "dropdown-item", onClick: selectParameter}, "Number of Users"))
                    ),

                    input({
                        type: "text", id: "parameterValue", name: "nameBoard",
                        class: "form-control",
                        placeholder: "Enter the board name you want to search", minlength: "3", maxlength: "60",
                        required: true
                    })
                )
            )
        ),
        input({type: "hidden", id: "selectedParameter", name: "selectedParameter"}),
        br(),
        button({type: "submit", class: "btn btn-primary w-100 btn-lg", value: "Submit"}, "Search")
    )

    function selectParameter() {
        //TODO:Review Implementations
        selectedParameter = this.innerText
        document.querySelector("#parameterButton").innerText = selectedParameter;
        switch (selectedParameter) {
            case 'Name':
                document.querySelector("#selectedParameter").value = selectedParameter;
                break
            case 'Number of Users':
                document.querySelector("#selectedParameter").value = 'NrUsers';
                break
        }

    }

    const resultsContainer = div()
    mainContent.replaceChildren(
        div({class: "card"},
            div({class: "card-header"},
                h1({class: "card-title"}, "Search Board")
            ),
            div({class: "card-body w-50 center"},
                myForm, resultsContainer
            )
        )
    )
    myForm.addEventListener('submit', (event) => {
        event.preventDefault(); // Prevent form submission
        const inputValue = document.querySelector("#parameterValue").value;

        switch (selectedParameter) {
            case 'Name':
                window.location.hash = `users/${id}/boards/search/${inputValue}`
                break
            case 'id':
                window.location.hash = undefined
                break
            case 'NrUsers': //TODO deviamos inserir o criterio de busca numa query string
                window.location.hash = `users//boards/search/${inputValue}`;
                break;
            default:
                alert("Invalid Parameter");
        }
    });

}

export default searchBoard