import getHomeHandler from "./handlers/getHomeHandler.js";
import getNotFoundHandler from "./handlers/getNotFoundHandler.js";
import createUserHandler from "./handlers/users/createUserHandler.js";
import loginUserHandler from "./handlers/users/userLoginHandler.js";
import userDetailsHandler from "./handlers/users/userDetailsHandler.js"

import searchBoardResultsHandler from "./handlers/boards/searchBoardResultsHandler.js";
import getBoardsHandler from "./handlers/boards/getBoardsHandler.js";
import createBoardHandler from "./handlers/boards/createBoardHandler.js";
import getBoardDetailsHandler from "./handlers/boards/getBoardDetailsHandler.js";

import createListHandler from "./handlers/lists/createListHandler.js";
import listDetailsHandler from "./handlers/lists/listDetailsHandler.js";
import createCardHandler from "./handlers/cards/createCardHandler.js";
import cardDetailsHandler from "./handlers/cards/cardDetailsHandler.js";
import searchBoardHandler from "./handlers/boards/searchBoardHandler.js";

export const handlers = {
    getHomeHandler,
    getNotFoundHandler,
    createUserHandler,
    loginUserHandler,
    userDetailsHandler,
    //------ Wolfie 17
    searchBoardResultsHandler,
    createBoardHandler,
    getBoardsHandler,
    getBoardDetailsHandler,
    searchBoardHandler,
    /*
    getUsersFromBoardHandler,
    */
    // ---------- RICHIE 95
    createListHandler,
    listDetailsHandler,
    createCardHandler,
    cardDetailsHandler,
}

export default handlers

/*
boardDetailsHandler(mainContent, id) {
    const board = getBoardDetails(id)
    const view = getBoardDetailsView()
    mainContent.replaceChildren(view)
}
*/