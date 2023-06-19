import Page from "../../partials/Page.js";
import CardPartial from "../../partials/cards/CardPartial.js";


function cardView(card, moveCardButton) {
    return Page(
        `${card.name}`,
        CardPartial(card, moveCardButton)
    )
}

export default cardView