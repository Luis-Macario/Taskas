import searchBoardView from "../../views/boards/BoardSearch.js";
import searchBoard from "../../data/boards/searchBoard.js";
import showErrorResponse from "../../configs/configs.js";
export default function searchBoardHandler(mainContent){

    let selectedParameter = null

    try{
        const searchFunction= searchBoard(selectedParameter)
        const view = searchBoardView(searchFunction, changeParameter)
        mainContent.replaceChildren(view)
    }catch (error){
        showErrorResponse(error)
    }

    function changeParameter() {
        //TODO:Review Implementations
        selectedParameter = this.innerText
        document.querySelector("#parameterButton").innerText = selectedParameter;
        document.querySelector("#parameterValue").placeholder = `Insert desired ${selectedParameter}`;
    }
}