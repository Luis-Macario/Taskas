import router from "./router.js";
import handlers from "./handlers.js";
import {navBar} from "./views/Nav.js";

window.addEventListener('load', loadHandler)
window.addEventListener('hashchange', hashChangeHandler)

function loadHandler() {
    router.addRouteHandler("", handlers.getHomeHandler)
    router.addRouteHandler("users/me", handlers.userDetailsHandler)
    router.addRouteHandler("users/create", handlers.createUserHandler)
    router.addRouteHandler("users/login", handlers.loginUserHandler)
    //router.addRouteHandler("users/boards/search", handlers.searchBoard)
    //router.addRouteHandler("users/boards/search/{query}", handlers.searchBoardResults)
    router.addRouteHandler("users/boards/create", handlers.createBoardHandler)
    router.addRouteHandler("users/boards", handlers.getBoardsHandler)
    router.addRouteHandler("boards/{id}", handlers.getBoardDetailsHandler)
    //router.addRouteHandler("boards/{id}/users", handlers.getUsersFromBoard)
    router.addRouteHandler("boards/{id}/lists/create", handlers.createListHandler)
    router.addRouteHandler("lists/{id}", handlers.listDetailsHandler)
    router.addRouteHandler("lists/{id}/cards/create", handlers.createCardHandler)
    router.addRouteHandler("cards/{id}", handlers.cardDetailsHandler)
    router.addDefaultNotFoundRouteHandler(handlers.getNotFoundHandler)

    hashChangeHandler()
}

function hashChangeHandler() {
    console.log("HASH HAS CHANGED")
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