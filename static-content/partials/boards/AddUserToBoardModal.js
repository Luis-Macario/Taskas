import {button, div} from "../../DSL/tags.js";
import {getAllUsers} from "../../data/boards/AddUserToBoardApiRequests.js";

export default function addUserToBoardModal(mainContent, id, callback) {

    let buttonDropDownClicked = false
    const dropDownMenu = div({class: "dropdown-menu", "aria-labelledby": "dropdownMenuButton"})
    const buttonDropDown = button({
        class: "btn btn-secondary dropdown-toggle",
        type: "button",
        id: "dropdownMenuButton",
        "data-bs-toggle": "dropdown",
        "aria-haspopup": "true",
        "aria-expanded": "false"
    }, `Available Users`)

    buttonDropDown.addEventListener("click", () => {
        let selectedUser = 0;
        if (!buttonDropDownClicked) {
            getAllUsers(mainContent, id)
                .then(users => {
                    buttonDropDownClicked = true
                    if (users.length === 0) {
                        buttonDropDown.textContent = 'No Users Available'
                    } else {
                        users.forEach(user => {
                            const listId = user.id
                            const userItem = button({
                                class: "dropdown-item"
                            }, `${user.name}`)
                            userItem.addEventListener('click', () => {
                                buttonDropDown.textContent = userItem.textContent
                                selectedUser = listId
                                callback(selectedUser)
                            });
                            dropDownMenu.appendChild(userItem)
                        })
                    }
                })
        }
    })

    return div({class: "dropdown"}, buttonDropDown, dropDownMenu)
}