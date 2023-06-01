import {a, div, h1, li} from "../../DSL/tags.js";
function usersFromBoardView(users,id) {

    return div({class: "card"},
        div({class: "card-header"},
            h1({class: "card-title"}, "Board Users")
        ),
        div({class: "card-body"},
            a({class: "btn btn-secondary", href: `#boards/${id}`}, "Return to board"),
            ...users.map(user => {
                return li({}, `${user.name}[${user.id}] : ${user.email} `)
            })
        )
    )

}

export default usersFromBoardView