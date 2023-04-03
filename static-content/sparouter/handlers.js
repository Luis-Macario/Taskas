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

function h1(string) {
    const h1 = document.createElement("h1")
    const text = document.createTextNode(string)
    h1.appendChild(text)
    return h1
}

function ul(...listItems) {
    const ul = document.createElement("ul")
    listItems.forEach(item => {
        ul.appendChild(item)
    })
    return ul
}

function li(string) {
    const li = document.createElement("li")
    const text = document.createTextNode(string)
    li.appendChild(text)
    return li
}

function p(stringOrElement) {
    const p = document.createElement("p")
    const content = (typeof stringOrElement === "string") ? document.createTextNode(stringOrElement) : stringOrElement
    p.appendChild(content)
    return p
}

function div(...elements) {
    const div = document.createElement("div")
    elements.forEach(element => {
        div.appendChild(element)
    })
    return div
}

function a(href, string) {
    const a = document.createElement("a")
    const text = document.createTextNode(string)
    a.appendChild(text)
    a.href = href
    return a
}

const API_BASE_URL = "http://localhost:8080/api/"

function getHome(mainContent) {
    mainContent.replaceChildren(
        h1("Home")
    )
}

function getUser(mainContent) {
    fetch(API_BASE_URL + "users/2", {
        headers: {
            "Authorization": "Bearer 12971dc2-6816-4851-b110-e19065747785"
        }
    }).then(res => res.json())
        .then(user => {
            mainContent.replaceChildren(
                div(
                    h1("User Details"),
                    p(`Name: ${user.name}`),
                    p(`Email: ${user.email}`),
                    p(`id: ${user.id}`)
                )
            )
        })
}

function getBoards(mainContent) {
    fetch(API_BASE_URL + "users/2/boards", {
        headers: {
            "Authorization": "Bearer 12971dc2-6816-4851-b110-e19065747785"
        }
    })
        .then(res => res.json())
        .then(boards => {
            mainContent.replaceChildren(
                div(
                    h1("Boards"),
                    ...boards.boards.map(s => {
                        return p(
                            a("#board/" + s.id, "Link Example to boards/" + s.id)
                        )

                    })
                )
            )
        })
}

function getBoardDetails(mainContent, id) {
    fetch(API_BASE_URL + `boards/${id}`, {
        headers: {
            "Authorization": "Bearer 12971dc2-6816-4851-b110-e19065747785"
        }
    }).then(res => res.json())
        .then(board => {
            mainContent.replaceChildren(
                div(
                    h1(board.name),
                    p(board.description)
                )
            )
        })
}

export const handlers = {
    getHome,
    getUser,
    getBoards,
    getBoardDetails
}

export default handlers