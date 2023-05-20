import {a, br, button, div, h1, li, p, table, td, th, tr, ul} from "../../DSL/tags.js";
import showErrorResponse, {API_BASE_URL, hardCodedBearer} from "../../configs/configs.js";
import listDelete from "./ListDelete.js";
import Modal from "../../partials/Modal.js";

async function getList(mainContent, id) {
    const res = await fetch(API_BASE_URL + `lists/${id}`, {
        headers: {
            "Authorization": "Bearer " + hardCodedBearer
        }
    })
    let list = null
    let cards = null
    const body = await res.json()
    if (res.status === 200) {
        list = body
        cards = list.cards
        console.log(list)
    } else {
        showErrorResponse(mainContent, body)
    }

    //TODO:'Maybe create a seperate directory for handlers'
    function createCard() {
        window.location.hash = `lists/${id}/cards/create`
    }

    function showDeleteModal() {
        mainContent.appendChild(modal);
    }

    function closeModal() {
        mainContent.removeChild(modal)
    }

    function deleteList() {
        mainContent.removeChild(modal);
        listDelete(list.bid, list.id)
    }

    const createCardButton = button({class: "btn btn-primary btn-sm", onClick: createCard}, "Create Card")
    const deleteListButton = button({class: "btn btn-primary btn-sm", onClick: showDeleteModal}, "Delete List")

    const declineButton = button({type: "button", class: "btn btn-secondary", onClick: closeModal}, "Close")
    const confirmButton = button({type: "button", class: "btn btn-primary", onClick:deleteList}, "Confirm Delete")

    const modal = Modal(mainContent,
        {
            title: "Delete List",
            body: "Are you sure you want to DELETE the list?",
            buttons: div({}, declineButton, confirmButton)
        })

    mainContent.replaceChildren(
        div({class: "card"},
            div({class: "card-header"},
                h1({class: "card-title"}, `${list.name}`)
            ),
            div({class: "card-body"},
                a({class: "btn btn-secondary", href: `#boards/${list.bid}`}, "Return to board"),
                ul({},
                    //li({}, `Name: ${list.name}`),
                    li({}, `ID: ${list.id}`),
                ),
                table({},
                    deleteListButton,
                    tr({},
                        th({}, "Cards")
                    ),
                    div({class: "card-body"},
                        div({class: "btn-group-vertical"},
                            ...(cards.length > 0 ? cards.map(card => {
                                        return a({class: "btn btn-secondary", href: `#cards/${card.id}`},
                                            "Card:" + card.name)
                                    })
                                    :
                                    [tr({},
                                        td({}, p({}, "List doesn't have any cards yet"))
                                    )]
                            )
                        ),
                        br(),
                        br(),
                        createCardButton
                    )
                )
            )
        )
    )
}

export default getList