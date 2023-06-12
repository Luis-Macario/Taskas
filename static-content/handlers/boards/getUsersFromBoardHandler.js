import showErrorResponse from "../../configs/configs.js";
import boardUsersView from "../../views/boards/BoardUsersView.js";
import getBoardUsersData from "../../data/boards/getBoardUsersData.js";
import {addUsersBoard} from "../../data/boards/AddUserToBoardApiRequests.js";
import {button, div} from "../../DSL/tags.js";
import Modal from "../../partials/Modal.js";
import addUserToBoardModal from "../../partials/boards/AddUserToBoardModal.js";

export default async function getUsersFromBoardHandler(mainContent, id) {
    try {
        const users = (await getBoardUsersData(id)).users

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

        const view = boardUsersView(users, id, addUserBoard)
        mainContent.replaceChildren(view)
    } catch (error) {
        mainContent.replaceChildren(showErrorResponse(error))
    }

}