import getListData from "../../data/lists/getListData.js";
import listView from "../../views/lists/ListView.js";
import showErrorResponse from "../../configs/configs.js";
import listDeleteData from "../../data/lists/listDeleteData.js";
import {button, div} from "../../DSL/tags.js";
import Modal from "../../partials/Modal.js";


async function listDetailsHandler(mainContent, listID) {
    try {

        const list = await getListData(listID)

        function createCard() {
            window.location.hash = `lists/${list.id}/cards/create`
        }

        function showDeleteModal() {
            mainContent.appendChild(modal);
        }

        function closeModal() {
            mainContent.removeChild(modal)
        }

        async function deleteList() {
            mainContent.removeChild(modal);
            await listDeleteData(list.bid, list.id)
        }

        const createCardButton = button({class: "btn btn-primary btn-sm", onClick: createCard}, "Create Card")
        const deleteListButton = button({class: "btn btn-primary btn-sm", onClick: showDeleteModal}, "Delete List")

        const declineButton = button({type: "button", class: "btn btn-secondary", onClick: closeModal}, "Close")
        const confirmButton = button({type: "button", class: "btn btn-primary", onClick: deleteList}, "Confirm Delete")

        const modal = Modal(mainContent,
            {
                title: "Delete List",
                body: "Are you sure you want to DELETE the list?",
                buttons: div({}, declineButton, confirmButton)
            })

        return listView(list, deleteListButton, createCardButton)

    } catch (e) {
        return showErrorResponse(e)
    }
}

export default listDetailsHandler