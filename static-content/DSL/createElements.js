
function createElement(tagName, options= {}, ...contents) {
    const element = document.createElement(tagName)
    if (options.id) element.id = options.id;
    if (options.class) element.className = options.class;
    contents.forEach(item => {
        const content = (typeof item === "string") ? document.createTextNode(item) : item
        element.appendChild(content)
    })
    return element
}

export default createElement