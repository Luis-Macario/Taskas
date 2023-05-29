import {div, h1} from "../../DSL/tags.js";
import boardSearchForm from "../../partials/boards/BoardSearchForm.js";

function searchBoard() {
    let selectedParameter = null
    const myForm = boardSearchForm()

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