const hardCodedBearer = "160ee838-150b-4ca1-a2ff-2e964383c315"

function createElement(tagName, ...stringsOrElements) {
    const element = document.createElement(tagName)
    stringsOrElements.forEach(item => {
        const content = (typeof item === "string") ? document.createTextNode(item) : item
        element.appendChild(content)
    })
    return element
}

function table(...tr) {
    return createElement("table", ...tr)
}

function tr(...content) { //table row
    return createElement("tr", ...content)
}

function th(tableHeader) { //table header
    return createElement("th", tableHeader)
}

function td(data) { //table data
    return createElement("td", data)
}

function h1(string) {
    return createElement("h1", string)
}

function ul(...listItems) {
    return createElement("ul", ...listItems)
}

function li(stringOrElement) {
    return createElement("li", stringOrElement)
}

function p(stringOrElement) {
    return createElement("p", stringOrElement)
}

function div(...elements) {
    return createElement("div", ...elements)
}

function a(href, string) {
    const a = createElement("a", string)
    a.href = href
    return a
}

const API_BASE_URL = "http://localhost:8080/api/"

function getHome(mainContent) {
    mainContent.replaceChildren(
        h1("Home")
    )
}

function form(...elements) {
    return createElement("form", ...elements)
}

function label(string) {
    const label = createElement("label")
    const text = document.createTextNode(string)
    label.appendChild(text)
    return label
}

function input(type, id) {
    const input = document.createElement("input")
    input.type = type
    if (id !== undefined) input.id = id
    return input
}

function createUser(mainContent) {
    function handleSubmit(e) {
        e.preventDefault()
        const inputName = document.querySelector("#idName")
        const inputEmail = document.querySelector("#idEmail")
        const options = {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json"
            },
            body: JSON.stringify({
                name: inputName.value,
                email: inputEmail.value
            })
        }
        fetch(API_BASE_URL + "users/", options)
            .then(res => res.json())
            .then(student => {
                console.log(student)
                window.location.hash = "students"
            })
    }

    const myForm = form(
        label("Name"),
        input("text", "idName"),
        label("Email"),
        input("text", "idEmail"),
        input("submit")
    )
    myForm.addEventListener('submit', handleSubmit)
    mainContent.replaceChildren(
        myForm
    )
}

function getUser(mainContent, id) {
    fetch(API_BASE_URL + "users/" + id, {
        headers: {
            "Authorization": "Bearer " + hardCodedBearer
        }
    }).then(res => {
        if (res.status !== 200) throw res.json()
        return res.json()
    })
        .then(user => {
            mainContent.replaceChildren(
                div(
                    h1("User Details"),
                    ul(
                        li(`Name: ${user.name}`),
                        li(`Email: ${user.email}`),
                        li(`id: ${user.id}`),
                    )
                )
            )
        }).catch(e => {
        return e
    }).then(error => {
        showErrorResponse(mainContent, error)
    })
}

async function getBoards(mainContent, id) {
    const skip = 0
    const limit = 10
    fetch(API_BASE_URL + `users/${id}/boards?skip=${skip}&limit=${limit}`, {
        headers: {
            "Authorization": "Bearer " + hardCodedBearer
        }
    })
        .then(res => {
            if (res.status < 200 || res.status > 299) throw res.json()
            return res.json()
        })
        .then(boards => {
                mainContent.replaceChildren(
                    div(
                        table(
                            tr(
                                th("My Boards")
                            ),
                            ...(boards.boards.length > 0 ? boards.boards.map(s => {
                                        return tr(
                                            td(a("#boards/" + s.id, "Board " + s.id))
                                        )
                                    }) :
                                    [tr(
                                        td(
                                            p("You aren't a part of any board yet")
                                        )
                                    )]
                            )
                        )
                    )
                )
            }
        ).catch(e => {
        return e
    }).then(error => {
        showErrorResponse(mainContent, error)
    })
}

function searchBoard(mainContent, id) {

}

function getBoardDetails(mainContent, id) {
    fetch(API_BASE_URL + `boards/${id}`, {
        headers: {
            "Authorization": "Bearer " + hardCodedBearer
        }
    }).then(res => {
        if (res.status < 200 || res.status > 299) throw res.json()
        return res.json()
    })
        .then(board => {
            mainContent.replaceChildren(
                div(
                    h1(board.name),
                    p(board.description),
                    table(
                        tr(
                            th("Lists")
                        ),
                        ...(board.lists.length > 0 ? board.lists.map(l => {
                                    return tr(
                                        td(a("#lists/" + l.id, "Link Example to lists/" + l.id))
                                    )
                                }) :
                                [
                                    tr(
                                        td(
                                            p("Board has no lists")
                                        )
                                    )
                                ]
                        ),
                        tr(
                            th("Users"),
                        ),
                        tr(
                            a(`#boards/${id}/users`, "Board Users")
                        )
                    )
                )
            )
        }).catch(e => {
        return e
    }).then(error => {
        showErrorResponse(mainContent, error)
    })
}

function getUsersFromBoard(mainContent, id) {
    const skip = 0
    const limit = 10
    fetch(API_BASE_URL + `boards/${id}/users?skip=${skip}&limit=${limit}`, {
        headers: {
            "Authorization": "Bearer " + hardCodedBearer
        }
    })
        .then(res => {
            if (res.status < 200 || res.status > 299) throw res.json()
            return res.json()
        })
        .then(users => {
            mainContent.replaceChildren(
                div(
                    p(a(`#boards/${id}`, "Return to board")),
                    h1("Board Users"),
                    ...users.users.map(user => {
                        return li(`${user.name}[${user.id}] : ${user.email} `)
                    })
                )
            )
        }).catch(e => {
        return e
    }).then(error => {
        showErrorResponse(mainContent, error)
    })
}

function getListsFromBoard(mainContent, id) {
    const skip = 0
    const limit = 10
    fetch(API_BASE_URL + `boards/${id}/lists?skip=${skip}&limit=${limit}`, {
        headers: {
            "Authorization": "Bearer " + hardCodedBearer
        }
    })
        .then(res => {
            if (res.status < 200 || res.status > 299) throw res.json()
            return res.json()
        })
        .then(lists => {
            mainContent.replaceChildren(
                div(
                    h1("Lists"),
                    ...lists.lists.map(s => {
                        return li(
                            a("#lists/" + s.id, "List " + s.id)
                        )
                    })
                )
            )
        }).catch(e => {
        return e
    }).then(error => {
        showErrorResponse(mainContent, error)
    })
}

function getList(mainContent, id) {
    fetch(API_BASE_URL + `lists/${id}`, {
        headers: {
            "Authorization": "Bearer " + hardCodedBearer
        }
    })
        .then(res => {
            if (res.status < 200 || res.status > 299) throw res.json()
            return res.json()
        })
        .then(list => {
            const cards = list.cards
            mainContent.replaceChildren(
                div(
                    p(a(`#boards/${list.bid}`, "Return to board")),
                    h1("List Info"),
                    ul(
                        li(`Name: ${list.name}`),
                        li(`id: ${list.id}`),
                    ),
                    table(
                        tr(
                            th("Cards")
                        ),
                        ...(cards.length > 0 ? cards.map(card => {
                                    return tr(
                                        td(a(`#cards/${card.id}`, "Card:" + card.name))
                                    )
                                })
                                :
                                [tr(
                                    td(p("List doesn't have any cards yet")) //fallback value to spread, hence the []
                                )]

                            //li(
                            //    a(`#lists/${id}/cards`, `Get Cards from List[${id}]`)  --next phase
                            // )
                        )
                    )
                )
            )
        }).catch(e => {
        return e
    }).then(error => {
        showErrorResponse(mainContent, error)
    })
}

function getCardsFromList(mainContent, id) {
    const skip = 0
    const limit = 10
    fetch(API_BASE_URL + `lists/${id}/cards?skip=${skip}&limit=${limit}`, {
        headers: {
            "Authorization": "Bearer " + hardCodedBearer
        }
    })
        .then(res => {
            if (res.status < 200 || res.status > 299) throw res.json()
            return res.json()
        })
        .then(cards => {
            mainContent.replaceChildren(
                div(
                    p(a(`#lists/${id}`, "Return to list")),
                    h1("Cards"),
                    ...cards.cards.map(s => {
                        return p(
                            a("#cards/" + s.id, "Card " + s.id)
                        )

                    })
                )
            )
        }).catch(e => {
        return e
    }).then(error => {
        showErrorResponse(mainContent, error)
    })
}

function showErrorResponse(mainContent, error) {
    console.log(error)
    mainContent.replaceChildren(
        div(
            h1(`${error.code} ${error.name}`),
            p(`${error.description}`)
        )
    )
}

function getCard(mainContent, id) {
    fetch(API_BASE_URL + `cards/${id}`, {
        headers: {
            "Authorization": "Bearer " + hardCodedBearer
        }
    })
        .then(res => {
            if (res.status < 200 || res.status > 299) throw res.json()
            return res.json()
        })
        .then(card => {
            mainContent.replaceChildren(
                div(
                    p(a(`#lists/${card.lid}`, "Return to list")),
                    //p(a(`#lists/${card.lid}/cards`, "Return to cards")),  --next phase
                    h1("Card Info"),
                    ul(
                        li(`Name: ${card.name}`),
                        li(`Id: ${card.id}`),
                        li(`Description: ${card.description}`),
                    )
                )
            )
        }).catch(e => {
        return e
    }).then(error => {
        showErrorResponse(mainContent, error)
    })
}

export const handlers = {
    getHome,
    createUser,
    getUser,
    getBoards,
    getBoardDetails,
    getUsersFromBoard,
    getListsFromBoard,
    getList,
    getCardsFromList,
    getCard
}

export default handlers