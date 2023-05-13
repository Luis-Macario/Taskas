import {getHome} from "./DSL/tags.js";
import getUser from "./views/users/UserView.js";
import createUser from "./views/users/UserCreation.js";
import searchBoard from "./views/boards/BoardSearch.js";
import getBoards from "./views/boards/BoardsView.js";
import getBoardDetails from "./views/boards/BoardDetailsView.js";
import getUsersFromBoard from "./views/boards/BoardUsersView.js";
import getListsFromBoard from "./views/boards/BoardListsView.js";
import getList from "./views/lists/ListView.js";
import getCardsFromList from "./views/lists/ListCardsView.js";
import getCard from "./views/cards/CardView.js";


export const handlers = {
    getHome,
    createUser,
    getUser,
    searchBoard,
    getBoards,
    getBoardDetails,
    getUsersFromBoard,
    getListsFromBoard,
    getList,
    getCardsFromList,
    getCard
}

export default handlers