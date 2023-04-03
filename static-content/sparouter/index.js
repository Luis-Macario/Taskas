import router from "./router.js";
import handlers from "./handlers.js";

window.addEventListener('load', loadHandler)
window.addEventListener('hashchange', hashChangeHandler)

function loadHandler(){

    router.addRouteHandler("home", handlers.getHome)
    router.addRouteHandler("user", handlers.getUser)
    router.addRouteHandler("boards", handlers.getBoards)
    router.addRouteHandler("boards:", handlers.getBoardDetails)
    router.addDefaultNotFoundRouteHandler(() => window.location.hash = "home")

    hashChangeHandler()
}

function hashChangeHandler(){

    const mainContent = document.getElementById("mainContent")
    const path =  window.location.hash.replace("#", "")

    const handler = router.getRouteHandler(path)

    const idStr = path.replace(/\D/g, '')
    const id = (idStr === '') ? null : Number(idStr)
    handler(mainContent, id)
}