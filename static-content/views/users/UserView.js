/*
function getUser(mainContent) {

    const user = getStoredUser()
    const id = user.id
    const token = user.token

    fetch(API_BASE_URL + "users/" + id, {
        headers: {
            "Authorization": "Bearer " + token
        }
    }).then(res => {
        if (res.status !== 200) throw res.json()
        return res.json()
    }).then(user => {
        mainContent.replaceChildren(
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
    })
        .catch(error => {
            showErrorResponse(mainContent, error);
        });
}

export default getUser*/
