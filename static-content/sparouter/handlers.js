/*
This example creates the students views using directly the DOM Api
But you can create the views in a different way, for example, for the student details you can:
    createElement("ul",
        createElement("li", "Name : " + student.name),
        createElement("li", "Number : " + student.number)
    )
or
    ul(
        li("Name : " + student.name),
        li("Number : " + student.name)
    )
Note: You have to use the DOM Api, but not directly
*/

const hardCodedBearer = "160ee838-150b-4ca1-a2ff-2e964383c315"

function createElement(tagName, ...stringsOrElements) {
    const element = document.createElement(tagName)
    stringsOrElements.forEach(item => {
        const content = (typeof item === "string") ? document.createTextNode(item) : item
        element.appendChild(content)
    })
    return element
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

function getUser(mainContent, id) {
    fetch(API_BASE_URL + "users/" + id, {
        headers: {
            "Authorization": "Bearer " + hardCodedBearer
        }
    }).then(res => {
        if (res.status < 200 || res.status > 299) throw res.json()
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
                    h1("My Boards"),
                    ...boards.boards.map(s => {
                        return li(
                            a("#boards/" + s.id, "Link Example to board" + s.id)
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
                    ul(
                        ...board.lists.map(l =>
                            li(a("#lists/" + l.id, "Link Example to lists/" + l.id))
                        ),
                        li(a(`#boards/${id}/users`, "Get Board Users"))
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
                        ...cards.map(card => {
                                return li(
                                    a(`#cards/${card.id}`, "Card:" + card.name)
                                )
                            })
                        //li(
                        //    a(`#lists/${id}/cards`, `Get Cards from List[${id}]`)  --next phase
                       // )
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
    const skip = 1
    const limit = 3
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