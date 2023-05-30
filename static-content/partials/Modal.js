import {div, h1} from "../DSL/tags.js";

function Modal(mainContent, parts) {

    const {title, body, buttons} = parts;

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
                    div({class: "row"},
                        div({class: "col-10"},
                            h1({}, title)
                        )
                    )
                ),
                div({class: "modal-body", style: "display: flex; gap: 10px;"},
                    body
                ),
                div({class: "modal-footer"},
                    buttons
                )
            )
        )
    )
}

export default Modal