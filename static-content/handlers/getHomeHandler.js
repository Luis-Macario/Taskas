import getHome from "../views/HomeView.js";

function getHomeHandler(mainContent) {
    const view = getHome()
    mainContent.replaceChildren(view)
}

export default getHomeHandler