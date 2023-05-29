import getNotFound from "../views/NotFoundView.js";

function getNotFoundHandler(mainContent) {
    const view = getNotFound()
    mainContent.replaceChildren(view)
}

export default getNotFoundHandler