import {API_BASE_URL, hardCodedBearer} from "../../configs/configs.js";

async function listDelete(bid, lid) {
    console.log(`Bid: ${bid} -- Lid: ${lid}`)
    const options = {
        method: "DELETE",
        headers: {
            "Authorization": "Bearer " + hardCodedBearer,
            "Content-Type": "application/json",
            "Accept": "application/json"
        }
    }

    fetch(API_BASE_URL + `lists/${lid}`, options)
        .then(res => res.json())
        .then(deletedOkay => {
            console.log(deletedOkay)
            window.location.hash = "boards/" + bid
        })
}

export default listDelete