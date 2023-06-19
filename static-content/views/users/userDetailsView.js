import {li, ul} from "../../DSL/tags.js";
import Page from "../../partials/Page.js";

function UserDetailsView(user) {
    return Page(
        "User Details",
        ul({class: "list-group"},
            li({class: "list-group-item"}, `Name: ${user.name}`),
            li({class: "list-group-item"}, `Email: ${user.email}`),
            li({class: "list-group-item"}, `ID: ${user.id}`),
        )
    )
}

export default UserDetailsView