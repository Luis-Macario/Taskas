
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

        switch (option) { //TODO:'Adicinonar mais casos'
            case "onClick" :
                element.addEventListener("click", val)
                break
            default:
                element.setAttribute(option, val);
        }
    }
}

export default createElement