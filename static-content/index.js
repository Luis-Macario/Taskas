import router from "./router.js";
import handlers from "./handlers.js";
import {navBar} from "./views/Nav.js";

window.addEventListener('load', loadHandler)
window.addEventListener('hashchange', hashChangeHandler)

function loadHandler() {
    router.addRouteHandler("", {handler: handlers.getHomeHandler, receives: {}})
    router.addRouteHandler("users/me", {handler: handlers.userDetailsHandler, receives: {}})
    router.addRouteHandler("users/signup", {handler: handlers.userSignupHandler, receives: {}})
    router.addRouteHandler("users/login", {handler: handlers.loginUserHandler, receives: {}})
    router.addRouteHandler("users/boards/search", {handler: handlers.searchBoardHandler, receives: {}})
    router.addRouteHandler("users/boards/search/{query}", {
        handler: handlers.searchBoardResultsHandler,
        receives: {mainContent: {}, query: {index: 3}}
    })
    router.addRouteHandler("users/boards/create", {handler: handlers.createBoardHandler, receives: {}})
    router.addRouteHandler("users/boards", {handler: handlers.getBoardsHandler, receives: {}})
    router.addRouteHandler("boards/{id}", {
        handler: handlers.getBoardDetailsHandler,
        receives: {id: {index: 1}}
    })
    router.addRouteHandler("boards/{id}/users", {
        handler: handlers.getUsersFromBoardHandler,
        receives: {mainContent: {}, id: {index: 1}}
    })
    router.addRouteHandler("boards/{id}/lists/create", {
        handler: handlers.createListHandler,
        receives: {id: {index: 1}}
    })
    router.addRouteHandler("lists/{id}", {
        handler: handlers.listDetailsHandler,
        receives: {mainContent: {}, id: {index: 1}}
    })
    router.addRouteHandler("lists/{id}/cards/create", {
        handler: handlers.createCardHandler,
        receives: {id: {index: 1}}
    })
    router.addRouteHandler("cards/{id}", {
        handler: handlers.cardDetailsHandler,
        receives: {mainContent: {}, id: {index: 1}}
    })
    router.addDefaultNotFoundRouteHandler({handler: handlers.getNotFoundHandler, receives: {}})

    hashChangeHandler()
}

export function hashChangeHandler() {
    const mainContent = document.getElementById("mainContent")
    let path = window.location.hash.replace("#", "")

    const pathTokens = path.split("/")

    const handlerObj = router.getRouteHandler(pathTokens)

    const args = []

    if (handlerObj.receives.mainContent) {
        args.push(mainContent)
    }

    if (handlerObj.receives.id) {
        args.push(pathTokens[handlerObj.receives.id.index])
    }

    if (handlerObj.receives.query) {
        args.push(pathTokens[handlerObj.receives.query.index])
    }

    const navContent = document.getElementById("navContent")
    navBar(navContent)

    const handler = handlerObj.handler

    handler(...args)
        .then(res => mainContent.replaceChildren(res))
}