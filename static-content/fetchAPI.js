const API_BASE_URL = "http://localhost:8080/api/"

async function fetchAPI(url, token) {
    try {
        let res = await fetch(API_BASE_URL + url, {
            headers: {
                "Authorization": "Bearer " + token
            }
        })

        if (res.ok) return res.json()

    } catch (e) {
        //TODO: Criar excepçoes
        throw e
    }
}

export default fetchAPI