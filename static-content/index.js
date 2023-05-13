import router from "./router.js";
import handlers from "./handlers.js";

window.addEventListener('load', loadHandler)
window.addEventListener('hashchange', hashChangeHandler)

function loadHandler() {

    router.addRouteHandler("home", handlers.getHome)
    router.addRouteHandler("users/{id}", handlers.getUser)
    router.addRouteHandler("users/create", handlers.createUser)
    router.addRouteHandler("users/{id}/boards/search", handlers.searchBoard)
    router.addRouteHandler("users/{id}/boards", handlers.getBoards)
    router.addRouteHandler("users/{id}/boards", handlers.getBoards)
    router.addRouteHandler("boards/{id}", handlers.getBoardDetails)
    router.addRouteHandler("boards/{id}/users", handlers.getUsersFromBoard)
    router.addRouteHandler("boards/{id}/lists", handlers.getListsFromBoard)
    router.addRouteHandler("lists/{id}", handlers.getList)
    router.addRouteHandler("lists/{id}/cards", handlers.getCardsFromList)
    router.addRouteHandler("cards/{id}", handlers.getCard)
    router.addDefaultNotFoundRouteHandler(() =>
        window.location.hash = "home"
    )

    hashChangeHandler()
}

function hashChangeHandler() {

    const mainContent = document.getElementById("mainContent")
    let path = window.location.hash.replace("#", "")

    const idStr = path.split("/")
    const id = (idStr.length < 2) ? null : Number(idStr[1])
    path = path.replace(RegExp("\/[0-9]+"), "/{id}")
    const handler = router.getRouteHandler(path)
    handler(mainContent, id)
}