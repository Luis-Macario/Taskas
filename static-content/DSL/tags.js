import createElement from "./createElements.js";

export function a(options = {}, ...elements) {
    return createElement("a", options, ...elements);
}

export function br() {
    return createElement("br");
}

export function button(options = {}, ...elements) {
    return createElement("button", options, ...elements);
}

export function caption(options = {}, ...elements) {
    return createElement("caption", options, ...elements);
}

export function div(options = {}, ...elements) {
    return createElement("div", options, ...elements);
}

export function form(options = {}, ...elements) {
    return createElement("form", options, ...elements);
}

export function h1(options = {}, string) {
    return createElement("h1", options, string);
}

export function h2(options = {}, string) {
    return createElement("h2", options, string);
}

export function input(options = {}, ...elements) {
    return createElement("input", options, ...elements);
}

export function label(options = {}, ...elements) {
    return createElement("label", options, ...elements);
}

export function li(options = {}, stringOrElement) {
    return createElement("li", options, stringOrElement);
}

export function nav(options = {}, ...elements) {
    return createElement("nav", options, ...elements);
}

export function option(options = {}, ...elements) {
    return createElement("option", options, ...elements);
}

export function p(options = {}, stringOrElement) {
    return createElement("p", options, stringOrElement);
}

export function select(options = {}, ...elements) {
    return createElement("select", options, ...elements);
}

export function span(options = {}, stringOrElement) {
    return createElement("span", options, stringOrElement);
}

export function table(options = {}, ...tr) {
    return createElement("table", options, ...tr);
}

export function tbody(options = {}, data) {
    return createElement("tbody", options, data);
}

export function td(options = {}, data) {
    return createElement("td", options, data);
}

export function textarea(options = {}, ...elements) {
    return createElement("textarea", options, ...elements);
}

export function tfoot(options = {}, data) {
    return createElement("tfoot", options, data);
}

export function th(options = {}, tableHeader) {
    return createElement("th", options, tableHeader);
}

export function thead(options = {}, data) {
    return createElement("thead", options, data);
}

export function tr(options = {}, ...content) {
    return createElement("tr", options, ...content);
}

export function ul(options = {}, ...listItems) {
    return createElement("ul", options, ...listItems);
}
