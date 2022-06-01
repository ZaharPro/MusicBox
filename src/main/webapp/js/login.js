const passwordInput = document.getElementById('passwordInput');

function togglePasswordCheckbox() {
    toggleCheckBox(passwordInput);
}

function toggleCheckBox(input) {
    input.type =
        input.type === "password" ?
            "text" :
            "password";
}