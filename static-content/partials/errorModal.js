import Modal from "./Modal.js";
import {button} from "../DSL/tags.js";

export default function errorModal(target, error) {

    const modal = Modal(target,
        {
            title: `${error.name}`,
            body: `${error.description}`,
            buttons: button({type: "button", class: "btn btn-secondary", onClick: closeModal}, "Close")
        }
    )

    target.appendChild(modal)

    function closeModal() {
        target.removeChild(modal)
    }
}