import {input} from "../../DSL/tags";

function CheckboxInput(mainContent, parts) {

    const {handler} = parts;

    return input({
        class: "form-check-input",
        type: "checkbox",
        id: "flexSwitchCheckDefault",
        onChange: handler
    })
}

export default CheckboxInput