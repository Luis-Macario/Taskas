import router from "./router.js";
import handlers from "./handlers.js";

window.addEventListener('load', loadHandler)
window.addEventListener('hashchange', hashChangeHandler)

function loadHandler() {
    router.addRouteHandler("home", handlers.getHome)
    router.addRouteHandler("users/{id}", handlers.getUser)
    router.addRouteHandler("users/create", handlers.createUser)
    router.addRouteHandler("users/{id}/boards/search", handlers.searchBoard)
    router.addRouteHandler("users/{id}/boards/search/{query}", handlers.searchBoardResults)
    router.addRouteHandler("users/{id}/boards/create", handlers.createBoard)
    router.addRouteHandler("users/{id}/boards", handlers.getBoards)
    router.addRouteHandler("boards/{id}", handlers.getBoardDetails)
    router.addRouteHandler("boards/{id}/users", handlers.getUsersFromBoard)
    router.addRouteHandler("boards/{id}/lists/create", handlers.createList)  // falta o id do user
    router.addRouteHandler("lists/{id}", handlers.getList)
    router.addRouteHandler("lists/{id}/cards/create", handlers.createCard)  // falta o id da board e do user
    router.addRouteHandler("cards/{id}", handlers.getCard)
    router.addDefaultNotFoundRouteHandler(() =>
        window.location.hash = "home"
    )

    hashChangeHandler()
}

function hashChangeHandler() {

    const mainContent = document.getElementById("mainContent")
    let path = window.location.hash.replace("#", "")

    console.log(path)

    const idStr = path.split("/")
    const id = (idStr.length < 2) ? null : Number(idStr[1])
    const query = (idStr.length < 5) ? null : idStr[4]

    path = path.replace(RegExp("\/[0-9]+"), "/{id}")
    if (query !== null) path = path.replace(RegExp(`/${query}\$`), "/{query}")

    console.log("idStr = " + idStr)
    console.log("id = " + id)
    console.log("query = " + query)
    console.log("path = " + path)
    const handler = router.getRouteHandler(path)
    handler(mainContent, id, query)
}