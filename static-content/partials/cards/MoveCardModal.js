import {button, div, h1, input, label} from "../../DSL/tags.js";
import {getAvailableLists, getCardsFromList, moveCard} from "../../views/cards/MoveCardApiRequests.js";

function MoveCardModal(mainContent, closeButton, boardID, cardID, listID) {

    // MARTELADO HARDCORE
    function selectIndex(resetButton = false) {
        const dropDownMenuIdx = div({class: "dropdown-menu", "aria-labelledby": "dropdownMenuButton"})

        const checkboxInput = input({
            class: "form-check-input",
            type: "checkbox",
            id: "flexSwitchCheckDefault",
        })
        checkboxInput.addEventListener("change", (event) => {
            checkBoxIdx = event.target.checked ? 1 : 0
            console.log(checkBoxIdx)
        })

        const checkBox =
            div({class: "form-check form-switch"},
                checkboxInput,
                label({class: "form-check-label", for: "flexSwitchCheckDefault"}, "Bellow or Above")
            )

        let prevSelected = null
        const buttonDropDownIdx = button({
            class: "btn btn-secondary dropdown-toggle",
            type: "button",
            id: "dropdownMenuButton",
            "data-bs-toggle": "dropdown",
            "aria-haspopup": "true",
            "aria-expanded": "false"
        }, `Choose the index`)
        buttonDropDownIdx.addEventListener("click", () => {
            if (selectedButton != null && prevSelected !== selectedButton) {
                prevSelected = selectedButton
                dropDownMenuIdx.innerHTML = ''   // Clears dropDown Menu
                getCardsFromList(selectedButton)
                    .then(cards => {
                        if (cards.length === 0) {
                            buttonDropDownIdx.textContent = "No Cards"
                            checkboxInput.disabled = true
                        } else {
                            let idx = 0
                            cards.forEach(card => {
                                const cardItem = button({
                                    class: "dropdown-item",
                                    "data-idx": idx
                                }, `${card.name}  (${idx})`)
                                cardItem.addEventListener("click", () => {
                                    selectedIdx = parseInt(cardItem.dataset.idx)
                                    buttonDropDownIdx.textContent = selectedIdx//cardItem.textContent
                                    checkboxInput.disabled = false
                                    confirmButton.disabled = false
                                })
                                idx++
                                dropDownMenuIdx.appendChild(cardItem);
                            })
                        }
                    })
                    .catch(error => {
                        // ...
                    })
            }
        })
        /*if (resetButton) {
            buttonDropDownIdx.textContent = "Choose the index";
        }*/

        return div({style: "display: flex; gap: 10px;"},
            div({class: "dropdown"}, buttonDropDownIdx, dropDownMenuIdx),
            checkBox
        )

    }

    //HAMMER
    let selectedIdx = 0
    let checkBoxIdx = 0
    let selectedButton = null
    let buttonText = "Lists Available"

    const confirmButton = button({type: "button", class: "btn btn-primary", disabled: "true"}, "Confirm Move")
    confirmButton.addEventListener("click", async () => {
        if (selectedButton != null) {
            await moveCard(mainContent, cardID, selectedButton, selectedIdx + checkBoxIdx)
                .then(() => window.location.hash = `lists/${selectedButton}`)
        }
    })

    const dropDownMenu = div({class: "dropdown-menu", "aria-labelledby": "dropdownMenuButton"})

    let buttonDropDownClicked = false
    const buttonDropDown = button({
        class: "btn btn-secondary dropdown-toggle",
        type: "button",
        id: "dropdownMenuButton",
        "data-bs-toggle": "dropdown",
        "aria-haspopup": "true",
        "aria-expanded": "false"
    }, `${buttonText}`)
    buttonDropDown.addEventListener("click", () => {
        if (!buttonDropDownClicked) {           // Para n estar sempre a fzr pedidos à API
            getAvailableLists(mainContent, boardID)
                .then(lists => {
                    buttonDropDownClicked = true
                    lists.forEach(list => {
                        const listId = list.id
                        const listItem = button({class: "dropdown-item"}, list.name)
                        listItem.addEventListener("click", () => {
                            buttonDropDown.textContent = listItem.textContent
                            selectedButton = listId
                            confirmButton.disabled = false
                            //selectIndex( true)
                        })
                        if (listId === listID) {
                            listItem.disabled = true;
                            listItem.textContent = `${list.name} (Current List)`
                        }
                        dropDownMenu.appendChild(listItem);
                    })
                })
                .catch(error => {
                    // ...
                })
        }
    })

    const dropDown = div({class: "dropdown"}, buttonDropDown, dropDownMenu)
    // Caso se queira apagar o que se tinha escolhido
    /*
    closeButton.addEventListener("click", () => {
        if (selectedButton != null) {
            selectedButton = null
            buttonDropDown.textContent = "Lists Available"
            confirmButton.disabled = true
        }
    })*/

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
                div({class: "modal-body", style: "display: flex; gap: 10px;"},
                    dropDown,
                    selectIndex()
                ),
                div({class: "modal-footer"},
                    closeButton,
                    confirmButton
                )
            )
        )
    )
}

export default MoveCardModal