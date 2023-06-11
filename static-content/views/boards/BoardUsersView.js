import {a, br, button, div, h1, li} from "../../DSL/tags.js";
import Modal from "../../partials/Modal.js";
import addUserToBoardModal from "../../partials/boards/AddUserToBoardModal.js";
import {addUsersBoard} from "../../data/boards/AddUserToBoardApiRequests.js";

function usersFromBoardView(users, id, mainContent) {

    function showAddUserModal() {
        mainContent.appendChild(modal);
    }

    function closeModal() {
        mainContent.removeChild(modal)
    }

    async function confirm() {
        console.log(selectedUserModal)
        if (selectedUserModal != null) {
            await addUsersBoard(mainContent, id, selectedUserModal).then(() => window.location.reload())
        }
    }

    const closeButton = button({type: "button", class: "btn btn-secondary", onClick: closeModal}, "Close")
    const confirmButton = button({type: "button", class: "btn btn-primary", onClick: confirm}, "Confirm Add")

    // Button that activates the modal
    const addUserBoard = button({
        type: "button",
        class: "btn btn-primary btn-sm",
        onClick: showAddUserModal
    }, "Add User")

    let selectedUserModal = null
    const modal = Modal(mainContent,
        {
            title: "Add users to this Board",
            body: addUserToBoardModal(mainContent, id, selected => {
                selectedUserModal = selected
            }),
            buttons: div({}, closeButton, confirmButton)
        })

    mainContent.replaceChildren(
        div({class: "card"},
            div({class: "card-header"},
                h1({class: "card-title"}, "Board Users")
            ),
            div({class: "card-body"},
                a({class: "btn btn-secondary", href: `#boards/${id}`}, "Return to board"),
                ...users.map(user => {
                    return li({}, `${user.name}[${user.id}] : ${user.email} `)
                }),
                br(),
                addUserBoard
            )
        )
    )

}

export default usersFromBoardView