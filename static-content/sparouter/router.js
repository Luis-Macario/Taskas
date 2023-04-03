const routes = []
let notFoundRouteHandler = () => { throw "Route handler for unknown routes not defined" }

function addRouteHandler(path, handler){
    routes.push({path, handler})
}
function addDefaultNotFoundRouteHandler(notFoundRH) {
    notFoundRouteHandler = notFoundRH
}

function getRouteHandler(path){
    const myPath = path.replace(/[0-9]+/g, '')
    const route = routes.find(r => r.path === myPath)
    return route ? route.handler : notFoundRouteHandler
}


const router = {
    addRouteHandler,
    getRouteHandler,
    addDefaultNotFoundRouteHandler
}

export default router