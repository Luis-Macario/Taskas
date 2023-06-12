import {div, h1} from "../../DSL/tags.js";
import UserLoginForm from "../../partials/users/UserLoginForm.js";

export default function UserLoginView(handleSubmit) {
    return div({},
        div({class: "card"},
            div({class: "card-header"},
                h1({class: "card-title"}, "Login User")
            ),
            div({class: "card-body w-50 center"},
                UserLoginForm(handleSubmit)
            )
        )
    )
}