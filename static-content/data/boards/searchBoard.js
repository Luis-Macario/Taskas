export default function searchBoard(event, selectedParameter) {
    event.preventDefault()
    const inputValue = document.querySelector("#parameterValue").value

    switch (selectedParameter) {
        case 'Name':
            window.location.hash = `users/boards/search/${inputValue}`
            break
        default:
            alert("Invalid Parameter")
    }
}