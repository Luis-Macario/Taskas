import {a, br, div, li} from "../../DSL/tags.js";
import Page from "../../partials/Page.js";

function usersFromBoardView(users, id, addUserBoard) {


    return Page(
        "Board Users",
        div({},
            a({class: "btn btn-secondary", href: `#boards/${id}`}, "Return to board"),
            ...users.map(user => {
                return li({}, `${user.name}[${user.id}] : ${user.email} `)
            }),
            br(),
            addUserBoard
        )
    )
}

export default usersFromBoardView