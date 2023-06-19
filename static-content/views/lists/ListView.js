import Page from "../../partials/Page.js";
import ListPartial from "../../partials/lists/ListPartial.js";

function listView(list, deleteListButton, createCardButton) {
    return Page(
        `${list.name}`,
        ListPartial(list, deleteListButton, createCardButton)
    )
}

export default listView