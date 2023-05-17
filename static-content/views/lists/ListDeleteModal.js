import {button, div, h1, span} from "../../DSL/tags.js";

function ListDeleteModal(declineButton, confirmButton) {
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
                    h1({class: "modal-title", id: "exampleModalLabel"}, "Delete List"),
                    button({type: "button", class: "close", "data-dismiss": "modal", "aria-label": "Close"},
                        span({"aria-hidden": "true"}, "")
                    )
                ),
                div({class: "modal-body"},
                    "Are you sure you want to DELETE the list?"
                ),
                div({class: "modal-footer"},
                    declineButton,
                    confirmButton
                )
            )
        )
    )
}

export default ListDeleteModal