import searchBoard from "../../views/boards/BoardSearch.js";

export default async function searchBoardResultsHandler(mainContent) {
    const view = await searchBoard(mainContent)
    mainContent.replaceChildren(view)
}