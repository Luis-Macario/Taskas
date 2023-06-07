import {API_BASE_URL, getStoredUser} from "../../configs/configs.js";

export default async function getBoardsData() {
    const user = getStoredUser()
    const id = user.id
    const token = user.token

    const skip = 0
    const limit = 10
    const res = await fetch(API_BASE_URL + `users/${id}/boards?skip=${skip}&limit=${limit}`, {
        headers: {
            "Authorization": "Bearer " + token
        }
    })

    if (res.status === 200) {
        const boards = (await res.json()).boards

        const boardRows = []
        const boardsPerRow = 3

        for (let i = 0; i < boards.length; i += boardsPerRow) {
            const boardRow = []
            for (let j = i; j < i + boardsPerRow && j < boards.length; j++) {
                boardRow.push(boards[j])
            }
            boardRows.push(boardRow)
        }
        return boardRows
    }
    throw res

}