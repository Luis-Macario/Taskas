
function createElement(tagName, options= {}, ...contents) {
    const element = document.createElement(tagName)
    setAttributes(element,options)
    contents.forEach(item => {
        const content = (typeof item === "string") ? document.createTextNode(item) : item
        element.appendChild(content)
    })
    return element
}

function setAttributes(element, options) {
    for (const option in options) {
        if (options[option] == null) continue;
        const val = options[option];
        if(val === null) continue

        switch (option) {
            case "onClick":
                element.addEventListener("click", val);
                break;
            case "onChange":
                element.addEventListener("change", val);
                break;
            case "onInput":
                element.addEventListener("input", val);
                break;
            case "onSubmit":
                element.addEventListener("submit", val);
                break;
            default:
                element.setAttribute(option, val);
        }
    }
}

export default createElement