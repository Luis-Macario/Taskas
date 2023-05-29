import getBoards from "../../views/boards/BoardsView.js";

export default async function getBoardsHandler(mainContent) {
    const view = await getBoards(mainContent)
    mainContent.replaceChildren(view)
}