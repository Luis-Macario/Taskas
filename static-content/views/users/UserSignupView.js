import {div, h1} from "../../DSL/tags.js";
import UserSignupForm from "../../partials/users/UserSignupForm.js";

function userSignupView(handleSubmit) {
    return div({},
        div({class: "card"},
            div({class: "card-header"},
                h1({class: "card-title"}, "Register User")
            ),
            div({class: "card-body w-50 center"},
                UserSignupForm(handleSubmit)
            )
        )
    )
}

export default userSignupView