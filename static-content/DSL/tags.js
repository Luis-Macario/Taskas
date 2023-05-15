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

export function h2(options = {}, string) {
    return createElement("h2", options, string)
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

export function span(options = {}, stringOrElement) {
    return createElement("span", options, stringOrElement)
}

export function div(options = {}, ...elements) {
    return createElement("div", options, ...elements)
}

export function a(href, string) {
    const a = createElement("a", {}, string)
    a.href = href
    return a
}

export function aV2(options = {}, ...elements){
    return createElement("a", options, ...elements)
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

export function labelV2( options = {}, ...elements){
    return createElement("label",options, ...elements )
}
export function input(type, id) {
    const input = createElement("input")
    input.type = type
    if (id !== undefined) input.id = id
    return input
}

export function inputV2(options = {}, ...elements) {
    return createElement("input", options, ...elements);
}

export function button(options = {}, ...elements) {
    return createElement("button", options, ...elements);
}

export function br() {
    const input = createElement("br")
    input.type = "br"
    return input
}