import UserLoginForm from "../../partials/users/UserLoginForm.js";
import Page from "../../partials/Page.js";
import {div} from "../../DSL/tags.js";

export default function UserLoginView(handleSubmit) {
    return Page(
        "Login User",
        div({class: "w-50"},
            UserLoginForm(handleSubmit)
        )
    )
}