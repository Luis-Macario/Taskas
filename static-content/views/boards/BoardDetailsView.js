import Page from "../../partials/Page.js";
import BoardDetailsPartial from "../../partials/boards/BoardDetailsPartial.js";

function boardDetailsView(board) {

    function redirectToListCreation() {
        window.location.hash = `boards/${board.id}/lists/create`
    }

    return Page(
        `${board.name}`,
        BoardDetailsPartial(board, redirectToListCreation)
    )
}

export default boardDetailsView