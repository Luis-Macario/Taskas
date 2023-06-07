const routes = []
let notFoundRouteHandler = () => {
    throw "Route handler for unknown routes not defined"
}

function addRouteHandler(path, handler) {
    routes.push({path, handler})
}

function addDefaultNotFoundRouteHandler(notFoundRH) {
    notFoundRouteHandler = notFoundRH
}

function getRouteHandler(pathTokens) {
    const route = routes.find(route => {
        const routePathTokens = route.path.split("/")
        if(routePathTokens.length !== pathTokens.length) return false
        let i = -1
        return pathTokens.every(token => {
            i++
            if(routePathTokens[i].startsWith("{")) return true
            return token === routePathTokens[i]
        })
    })
    return route ? route.handler : notFoundRouteHandler
}


const router = {
    addRouteHandler,
    getRouteHandler,
    addDefaultNotFoundRouteHandler
}

export default router