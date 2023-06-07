import {button, div, h5, p} from "../../DSL/tags.js";

export default function currentBoardView(board, getDetails, getPreviousBoard, getNextBoard, idx, size) {
    return div({},
        div({class: "card-header"},
            div({class: "row"},
                div({class: "col-9"}, h5({}, board.name)),
                div({class: "col-3"}, `${idx + 1}/${size}`)
            )
        ),
        div({class: "card-body"},
            p({}, board.description),
            button({class: "btn btn-primary btn-sm", onClick: getDetails}, "Get Board Details")
        ),
        div({class: "row"},
            div({class: "col-6"},
                button({class: "btn btn-outline-primary w-100", onClick: getPreviousBoard}, "Prev")
            ),
            div({class: "col-6"},
                button({class: "btn btn-outline-primary w-100", onClick: getNextBoard}, "Next")
            )
        )
    )
}