import createElement from "./createElements.js";
export function table(...tr) {
    return createElement("table", ...tr)
}

export function tr(...content) { //table row
    return createElement("tr", ...content)
}

export function th(tableHeader) { //table header
    return createElement("th", tableHeader)
}

export function td(data) { //table data
    return createElement("td", data)
}

export function h1(string) {
    return createElement("h1", string)
}

export function ul(...listItems) {
    return createElement("ul", ...listItems)
}

export function li(stringOrElement) {
    return createElement("li", stringOrElement)
}

export function p(stringOrElement) {
    return createElement("p", stringOrElement)
}

export function div(...elements) {
    return createElement("div", ...elements)
}

export function a(href, string) {
    const a = createElement("a", string)
    a.href = href
    return a
}

export function form(...elements) {
    return createElement("form", ...elements)
}

export function label(string) {
    const label = createElement("label")
    const text = document.createTextNode(string)
    label.appendChild(text)
    return label
}

export function input(type, id) {
    const input = createElement("input")
    input.type = type
    if (id !== undefined) input.id = id
    return input
}

export function getHome(mainContent) {
    mainContent.replaceChildren(
        h1("Home")
    )
}