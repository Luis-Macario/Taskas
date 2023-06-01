import {a, div, h1, h5, p} from "../../DSL/tags.js";

function boardsView(boards) {

        const boardRows = [];

        for (let i = 0; i < boards.length; i += 3) {
            const boardRow = [];
            for (let j = i; j < i + 3 && j < boards.length; j++) {
                const board = boards[j];
                boardRow.push(
                    div({ class: "card" },
                        div({ class: "card-body" },
                            h5({ class: "card-title" }, `${board.name}`),
                            p({ class: "card-text" }, `${board.description}`),
                            a({ class: "card-link btn btn-primary", href: `#boards/${board.id}` }, "Details")
                        )
                    )
                );
            }
            boardRows.push(boardRow);
        }

        return div({ class: "card" },
            div({ class: "card-header" },
                h1({ class: "card-title" }, "My Boards")
            ),
                ...(boards.length > 0 ? boardRows.map((row) => {
                    return div({ class: "card-group" }, ...row);
                }) : [
                    p({ class: "text-muted mb-0" }, "You aren't a part of any board yet")
                ])
        );
}

export default boardsView

