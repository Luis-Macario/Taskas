import {a, div, h5, p} from "../../DSL/tags.js";
import Page from "../../partials/Page.js";

function boardsView(boardRows) {

    return Page(
        "My Boards",
        ...boardRows.length > 0 ? boardRows.map((row) => {
                return div(
                    {class: "card-group"},
                    ...row.map((board) => {
                        return div({class: "card text-dark bg-light mb-3"},
                            div({class: "card-body"},
                                h5({class: "card-title"}, `${board.name}`),
                                p({class: "card-text"}, `${board.description}`),
                                a({class: "card-link btn btn-primary", href: `#boards/${board.id}`}, "Details")
                            )
                        )
                    })
                )
            })
            : [
                div({class: "card-body"},
                    h5({class: "text-muted mb-0"}, "You aren't a part of any board yet")
                )
            ]
    )
}

export default boardsView

