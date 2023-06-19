import {div} from "../../DSL/tags.js";
import UserSignupForm from "../../partials/users/UserSignupForm.js";
import Page from "../../partials/Page.js";

function UserSignupView(handleSubmit) {
    return Page(
        "Register User",
        div({class: "w-50"},
            UserSignupForm(handleSubmit)
        )
    )
}

export default UserSignupView