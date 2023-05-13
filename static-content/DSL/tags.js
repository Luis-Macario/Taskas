import createElement from "./createElements.js";

export function table(options = {}, ...tr) {
    return createElement("table", options, ...tr)
}

export function tr(options = {}, ...content) { //table row
    return createElement("tr", options, ...content)
}

export function th(options = {}, tableHeader) { //table header
    return createElement("th", options, tableHeader)
}

export function td(options = {}, data) { //table data
    return createElement("td", options, data)
}

export function h1(options = {}, string) {
    return createElement("h1", options, string)
}

export function ul(options = {}, ...listItems) {
    return createElement("ul", options, ...listItems)
}

export function li(options = {}, stringOrElement) {
    return createElement("li", options, stringOrElement)
}

export function p(options = {}, stringOrElement) {
    return createElement("p", options, stringOrElement)
}

export function div(options = {}, ...elements) {
    return createElement("div", options, ...elements)
}

export function a(href, string) {
    //TODO
    const a = createElement("a", {}, string)
    a.href = href
    return a
}

export function form(options = {}, ...elements) {
    return createElement("form", options, ...elements)
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
        h1({class: "display-4 mt-5 mb-4 text-center"}, "Home")
    )
}