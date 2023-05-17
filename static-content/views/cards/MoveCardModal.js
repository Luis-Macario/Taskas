import { button, div, h1} from "../../DSL/tags.js";

function MoveCardModal() {

    const declineButton = button({type: "button", class: "btn btn-secondary", "data-dismiss": "modal"}, "Close")
    const confirmButton = button({type: "button", class: "btn btn-primary"}, "Confirm Move")

    /**
     * <div class="dropdown">
     *   <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
     *     Dropdown button
     *   </button>
     *   <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
     *     <a class="dropdown-item" href="#">Action</a>
     *     <a class="dropdown-item" href="#">Another action</a>
     *     <a class="dropdown-item" href="#">Something else here</a>
     *   </div>
     * </div>
     */
    let selectedList = null
    let buttonText = "Lists Available"

    const b1 = button({class: "dropdown-item"}, "List 1")
    b1.addEventListener("click", () => {
     //...
    })

    const buttonDropDown = button({
        class: "btn btn-secondary dropdown-toggle",
        type: "button",
        id: "dropdownMenuButton",
        "data-bs-toggle": "dropdown",
        "aria-haspopup": "true",
        "aria-expanded": "false"
    }, `${buttonText}`)

    const dropDownMenu =
        div({class: "dropdown"},
            buttonDropDown,
            div({class: "dropdown-menu", "aria-labelledby": "dropdownMenuButton"},
                //a({class: "dropdown-item", href: "#home"}, "Something")
                //button({class: "dropdown-item"}, "List 1"),
                b1 ,
                button({class: "dropdown-item"}, "List 2"),
                button({class: "dropdown-item"}, "List 3"),
            )
        )

    return div({
            class: "modal-fade",
            id: "exampleModal",
            tabindex: "-1",
            role: "dialog",
            "aria-labelledby": "exampleModalLabel",
            "aria-hidden": "true",
            style: "position: fixed; z-index: 1000; top: 50%; left: 50%; transform: translate(-50%, -50%);"
        },
        div({class: "modal-dialog", role: "document"},
            div({class: "modal-content"},
                div({class: "modal-header"},
                    h1({class: "modal-title", id: "exampleModalLabel"}, "Move Card to another List"),
                ),
                div({class: "modal-body"},
                    dropDownMenu
                ),
                div({class: "modal-footer"},
                    declineButton,
                    confirmButton
                )
            )
        )
    )
}

export default MoveCardModal