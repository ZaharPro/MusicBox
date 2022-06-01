const passwordInput = document.getElementById('passwordInput');
const repeatPasswordInput = document.getElementById('repeatPasswordInput');
const invalidRepPassLabel = document.getElementById('invalidRepPassLabel');
const submitButton = document.getElementById('submit');

function togglePasswordCheckbox() {
    toggleCheckBox(passwordInput);
}

function toggleRepeatPasswordCheckbox() {
    toggleCheckBox(repeatPasswordInput);
}

function toggleCheckBox(input) {
    input.type =
        input.type === "password" ?
            "text" :
            "password";
}


function checkPassword(pass1, pass2) {
    if (pass1 === pass2) {
        invalidRepPassLabel.classList.remove("active");
        submitButton.classList.add("active");
    } else {
        invalidRepPassLabel.classList.add("active");
        submitButton.classList.remove("active");
    }
}

repeatPasswordInput.addEventListener("input",
    () => checkPassword(passwordInput.value, repeatPasswordInput.value));
