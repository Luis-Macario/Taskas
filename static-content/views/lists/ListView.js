import {a, br, button, div, h1, li, p, table, td, th, tr, ul} from "../../DSL/tags.js";
import showErrorResponse, {API_BASE_URL, hardCodedBearer} from "../../configs/configs.js";
import listDelete from "./ListDelete.js";
import ListDeleteModal from "./ListDeleteModal.js";

async function getList(mainContent, id) {
    const res = await fetch(API_BASE_URL + `lists/${id}`, {
        headers: {
            "Authorization": "Bearer " + hardCodedBearer
        }
    })


    const createCardButton = button({class: "btn btn-primary btn-sm"}, "Create Card")
    createCardButton.addEventListener("click", () => {
        window.location.hash = `lists/${id}/cards/create`
    })

    let modalVisible = false;

    const deleteListButton =
        button(
            {class: "btn btn-primary btn-sm", "data-toggle": "model", "data-target": "#exampleModal"},
            "Delete List"
        )

    deleteListButton.addEventListener("click", () => {
        if (modalVisible) {
            mainContent.removeChild(modal);
            modalVisible = false;
        } else {
            //const newModal = modal.cloneNode(true);
            mainContent.appendChild(modal);
            modalVisible = true;
        }
    });

    const declineButton = button({type: "button", class: "btn btn-secondary", "data-dismiss": "modal"}, "Close")
    const confirmButton = button({type: "button", class: "btn btn-primary"}, "Confirm Delete")

    declineButton.addEventListener("click", () => {
        mainContent.removeChild(modal);
    })

    const modal = ListDeleteModal(declineButton, confirmButton)
        /*div({
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
                        h1({class: "modal-title", id: "exampleModalLabel"}, "Delete List?"),
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
        )*/


    const body = await res.json()
    if (res.status === 200) {
        const list = body
        const cards = list.cards

        confirmButton.addEventListener("click", () => {
            mainContent.removeChild(modal);
            listDelete(list.bid, list.id)
        })
        mainContent.replaceChildren(
            div({},
                div({class: "card"},
                    div({class: "card-header"},
                        h1({class: "card-title"}, "List Info")
                    ),
                    a(`#boards/${list.bid}`, "Return to board"),
                    ul({},
                        li({}, `Name: ${list.name}`),
                        li({}, `id: ${list.id}`),
                    ),
                    table({},
                        br(),
                        deleteListButton,
                        tr({},
                            th({}, "Cards")
                        ),
                        ...(cards.length > 0 ? cards.map(card => {
                                    return tr({},
                                        td({}, a(`#cards/${card.id}`, "Card:" + card.name))
                                    )
                                })
                                :
                                [tr({},
                                    td({}, p({}, "List doesn't have any cards yet")) //fallback value to spread, hence the []
                                )]
                        ), br(),
                        createCardButton
                    )
                )
            )
        )
        return
    }

    showErrorResponse(mainContent, body)
}

export default getList