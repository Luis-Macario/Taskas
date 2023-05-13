import {div, h1, li, ul} from "../../DSL/tags.js";
import showErrorResponse, {API_BASE_URL, hardCodedBearer} from "../../configs/configs.js";

function getUser(mainContent, id) {
    fetch(API_BASE_URL + "users/" + id, {
        headers: {
            "Authorization": "Bearer " + hardCodedBearer
        }
    }).then(res => {
        if (res.status !== 200) throw res.json()
        return res.json()
    }).then(user => {
            mainContent.replaceChildren(
                div({ class: "container mt-5" },
                    div({ class: "card border-primary mb-3" },
                        div({ class: "card-header" },
                            h1({ class: "h3 mb-0" }, "User Details")
                        ),
                        div({ class: "card-body text-primary" },
                            ul({ class: "list-group" },
                                li({ class: "list-group-item" }, `Name: ${user.name}`),
                                li({ class: "list-group-item" }, `Email: ${user.email}`),
                                li({ class: "list-group-item" }, `ID: ${user.id}`),
                            )
                        )
                    )
                )
            );
        })
        .catch(error => {
            showErrorResponse(mainContent, error);
        });
}

export default getUser
