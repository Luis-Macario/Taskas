import getHomeHandler from "./handlers/getHomeHandler.js";
import getNotFoundHandler from "./handlers/getNotFoundHandler.js";
import createUserHandler from "./handlers/createUserHandler.js";
import loginUserHandler from "./handlers/userLoginHandler.js";
import userDetailsHandler from "./handlers/userDetailsHandler.js"

import searchBoardResultsHandler from "./handlers/boards/searchBoardResultsHandler.js";
import getBoardsHandler from "./handlers/boards/getBoardsHandler.js";
import createBoardHandler from "./handlers/boards/createBoardHandler.js";
import getBoardDetailsHandler from "./handlers/boards/getBoardDetailsHandler.js";

import createListHandler from "./handlers/createListHandler.js";
import listDetailsHandler from "./handlers/listDetailsHandler.js";
import createCardHandler from "./handlers/createCardHandler.js";
import cardDetailsHandler from "./handlers/cardDetailsHandler.js";

export const handlers = {
    getHomeHandler,
    getNotFoundHandler,
    createUserHandler,
    loginUserHandler,
    userDetailsHandler,
    /*
    searchBoardHandler,
    */
    //------ Wolfie 17
    searchBoardResultsHandler,
    createBoardHandler,
    getBoardsHandler,
    getBoardDetailsHandler,
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