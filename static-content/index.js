import router from "./router.js";
import handlers from "./handlers.js";
import {navBar} from "./views/Nav.js";

window.addEventListener('load', loadHandler)
window.addEventListener('hashchange', hashChangeHandler)

function loadHandler() {
    router.addRouteHandler("", handlers.getHome)
    router.addRouteHandler("users/me", handlers.getUser)
    router.addRouteHandler("users/create", handlers.createUser)
    router.addRouteHandler("users/login", handlers.loginUser)
    router.addRouteHandler("users/boards/search", handlers.searchBoard)
    router.addRouteHandler("users/boards/search/{query}", handlers.searchBoardResults)
    router.addRouteHandler("users/boards/create", handlers.createBoard)
    router.addRouteHandler("users/boards", handlers.getBoards)
    router.addRouteHandler("boards/{id}", handlers.getBoardDetails)
    router.addRouteHandler("boards/{id}/users", handlers.getUsersFromBoard)
    router.addRouteHandler("boards/{id}/lists/create", handlers.createList)
    router.addRouteHandler("lists/{id}", handlers.getList)
    router.addRouteHandler("lists/{id}/cards/create", handlers.createCard)
    router.addRouteHandler("cards/{id}", handlers.getCard)
    router.addDefaultNotFoundRouteHandler(handlers.getNotFound)

    hashChangeHandler()
}

function hashChangeHandler() {

    const mainContent = document.getElementById("mainContent")
    let path = window.location.hash.replace("#", "")

    const idStr = path.split("/")
    const id = (idStr.length < 2) ? null : Number(idStr[1])
    const isSearch = (idStr.length < 3) ? false : (idStr[2] === "search")
    const query = (idStr.length < 4) ? null : idStr[3]

    console.log(`>> ${query}`)

    path = (id !== null && !isNaN(id)) ? path.replace(RegExp("\/[0-9]+"), "/{id}") : path
    if (isSearch && query !== null) path = path.replace(RegExp(`/${query}\$`), "/{query}")

    console.log(`>> ${id}`)
    console.log(`FINAL PATH >> ${path}`)

    const handler = router.getRouteHandler(path)
    const navContent = document.getElementById("navContent")
    navBar(navContent)
    handler(mainContent, id, query)
}