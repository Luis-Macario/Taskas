import {p} from "../DSL/tags.js";
import Page from "../partials/Page.js";

function HomeView() {
    return Page(
        "Home",
        p({}, "Developed for software laboratory course @ ISEL"),
    )
}

export default HomeView