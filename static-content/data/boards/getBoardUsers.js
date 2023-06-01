import {API_BASE_URL, getStoredUser} from "../../configs/configs.js";

export default async function getBoardUsers(id){

    const user = getStoredUser()
    const token = user.token

    const skip = 0
    const limit = 10
    const res = await fetch(API_BASE_URL + `boards/${id}/users?skip=${skip}&limit=${limit}`, {
        headers: {
            "Authorization": "Bearer " + token
        }
    })

    if(res.status === 200){
        return await res.json()
    }
    throw res
}