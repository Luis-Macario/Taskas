
function createElement(tagName, options= {}, ...contents) {
    const element = document.createElement(tagName)
    setAttributes(element,options)
    contents.forEach(item => {
        const content = (typeof item === "string") ? document.createTextNode(item) : item
        element.appendChild(content)
    })
    console.log(element)
    return element
}

function setAttributes(element, options) {
    for (const option in options) {
        if (options[option] == null) continue;
        const val = options[option];
        if(val === null) continue

        /*if(option === "onClick"){
            console.log(`${options}---- ${val}`)
            element.addEventListener("click", val);
        } else {*/
            element.setAttribute(option, val);
        //}
    }
}

export default createElement