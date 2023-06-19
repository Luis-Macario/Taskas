import boardSearchForm from "../../partials/boards/BoardSearchForm.js";
import Page from "../../partials/Page.js";

function searchBoardView(submitHandler) {
    return Page(
        "Search Board",
        boardSearchForm(submitHandler),
    )
}

export default searchBoardView