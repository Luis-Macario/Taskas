import {API_BASE_URL, getStoredUser} from "../../configs/configs.js";

export default async function searchBoardsResult(query) {
    const user = getStoredUser()
    const id = user.id
    const token = user.token


    const options = {
        method: "GET",
        headers: {
            "Authorization": "Bearer " + token,
            "Content-Type": "application/json",
            "Accept": "application/json",
        }
    }

    const res = await fetch(API_BASE_URL + `users/${id}/boards/search?search_query=${query}`, options)
    const boards = (await res.json()).boards

    function getNextBoard(){
        idx++
        idx = (idx > boards.length - 1) ? 0 : idx
        showBoard(boards)
    }

    function getPreviousBoard(){
        idx--
        idx = (idx < 0) ? boards.length - 1 : idx
        showBoard(boards)
    }

    if (res.status === 200) {
        idx = 0
        if (boards.length === 0) {
            showNoBoards()
        } else {
            showBoard(boards,

            )
        }
        return
    }
    throw body
}