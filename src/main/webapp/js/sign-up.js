const passwordInput = document.getElementById('passwordInput');
const repeatPasswordInput = document.getElementById('repeatPasswordInput');

const invalidRepPassLabel = document.getElementById('invalidRepPassLabel');
const submitButton = document.getElementById('submit');

function toggle(checkbox) {
    checkbox.type =
        checkbox.type === "password" ?
            "text" :
            "password";
}

function togglePasswordCheckbox() {
    toggle(passwordInput);
}

function toggleRepeatPasswordCheckbox() {
    toggle(repeatPasswordInput);
}

function checkPassword(pass1, pass2) {
    if (pass1 === pass2) {
        invalidRepPassLabel.classList.add("d-none");
        submitButton.classList.remove("border-danger");
    } else {
        invalidRepPassLabel.classList.remove("d-none");
        submitButton.classList.add("border-danger");
    }
}

repeatPasswordInput.addEventListener("input",
    () => checkPassword(passwordInput.value, repeatPasswordInput.value));
