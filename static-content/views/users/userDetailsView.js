import {div, h1, li, ul} from "../../DSL/tags.js";

function UserDetailsView(mainContent, user) {
    return mainContent.replaceChildren(
        div({class: "card"},
            div({class: "card-header"},
                h1({class: "card-title"}, "User Details")
            ),
            div({class: "card-body text-primary"},
                ul({class: "list-group"},
                    li({class: "list-group-item"}, `Name: ${user.name}`),
                    li({class: "list-group-item"}, `Email: ${user.email}`),
                    li({class: "list-group-item"}, `ID: ${user.id}`),
                )
            )
        )
    )
}

export default UserDetailsView