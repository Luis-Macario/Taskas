
function createElement(tagName, ...stringsOrElements) {
    const element = document.createElement(tagName)
    stringsOrElements.forEach(item => {
        const content = (typeof item === "string") ? document.createTextNode(item) : item
        element.appendChild(content)
    })
    return element
}
export default createElement