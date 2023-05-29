import {div, h1} from "../../DSL/tags.js";
import UserCreateForm from "../../partials/users/UserCreateForm.js";

function userCreateView(handleSubmit){
    return div({},
            div({class: "card"},
                div({class: "card-header"},
                    h1({class: "card-title"}, "Register User")
             ),
                div({class: "card-body w-50 center"},
                   UserCreateForm(handleSubmit)
                )
            )
        )
}

export default userCreateView