const oldPasswordInput = document.getElementById('oldPasswordInput');
const newPasswordInput = document.getElementById('newPasswordInput');
const repeatPasswordInput = document.getElementById('repeatPasswordInput');

const invalidRepPassLabel = document.getElementById('invalidRepPassLabel');
const submitButton = document.getElementById('submit');

function toggle(checkbox) {
    checkbox.type =
        checkbox.type === "password" ?
            "text" :
            "password";
}

function toggleOldPasswordCheckbox() {
    toggle(oldPasswordInput);
}

function toggleNewPasswordCheckbox() {
    toggle(newPasswordInput);
}

function toggleRepeatPasswordCheckbox() {
    toggle(repeatPasswordInput);
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
    () => checkPassword(newPasswordInput.value, repeatPasswordInput.value));
