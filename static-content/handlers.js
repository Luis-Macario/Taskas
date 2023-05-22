import getUser from "./views/users/UserView.js";
import createUser from "./views/users/UserCreation.js";
import loginUser from "./views/users/UserLogin.js";
import searchBoard from "./views/boards/BoardSearch.js";
import searchBoardResults from "./views/boards/BoardSearchResults.js";
import createBoard from "./views/boards/BoardCreate.js";
import getBoards from "./views/boards/BoardsView.js";
import getBoardDetails from "./views/boards/BoardDetailsView.js";
import getUsersFromBoard from "./views/boards/BoardUsersView.js";
import createList from "./views/lists/ListCreate.js";
import getList from "./views/lists/ListView.js";
import createCard from "./views/cards/CardCreate.js";
import getCard from "./views/cards/CardView.js";
import getHome from "./views/HomeView.js";

export const handlers = {
    getHome,
    createUser,
    loginUser,
    getUser,
    searchBoard,
    searchBoardResults,
    createBoard,
    getBoards,
    getBoardDetails,
    getUsersFromBoard,
    createList,
    getList,
    createCard,
    getCard
}

export default handlers