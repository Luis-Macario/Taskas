import {a, button, div, nav} from "../DSL/tags.js";
import {getStoredUser, storeUser} from "../configs/configs.js";
import Modal from "../partials/Modal.js";

export function navBar(navContent) {
    const user = getStoredUser()

    const signOutButton = button({class: "btn btn-primary btn-sm", onClick: showDeleteModal}, "Sign Out")
    const declineButton = button({type: "button", class: "btn btn-secondary", onClick: closeModal}, "Cancel")
    const confirmButton = button({type: "button", class: "btn btn-primary", onClick: signOut}, "Confirm")

    const navElements =
        user != null ?
            [
                a({href: "#users/boards/create", class: "nav-item nav-link"}, "Create Board"),
                a({href: "#users/boards/search", class: "nav-item nav-link"}, "Search Board"),
                a({href: "#users/boards", class: "nav-item nav-link"}, "My Boards"),
                a({href: `#users/me`, class: "nav-item nav-link"}, "My Details"),
                div({class: "nav ms-auto me-5"},
                    signOutButton
                )
            ]
            :
            [div({class: "nav ms-auto me-5"},
                a({href: "#users/login", class: "nav-item nav-link"}, "Login"),
                a({href: "#users/signup", class: "nav-item nav-link"}, "Sign Up"),
            )]

    const modal = Modal(navContent,
        {
            title: "Sign Out",
            body: "Are you sure you want to SIGN OUT?",
            buttons: div({}, declineButton, confirmButton)
        })

    function showDeleteModal() {
        navContent.appendChild(modal);
    }

    function closeModal() {
        navContent.removeChild(modal)
    }

    function signOut() {
        storeUser(null)
        window.location.hash = "#users/login"
    }

    navContent.replaceChildren(
        div({},
            nav({class: "nav nav-pills"},
                a({href: "#", class: "nav-link"}, "Home"),
                ...navElements
            )
        )
    )
}