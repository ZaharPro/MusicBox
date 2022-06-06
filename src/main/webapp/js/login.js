const passwordInput = document.getElementById('passwordInput');

function toggle(checkbox) {
    checkbox.type =
        checkbox.type === "password" ?
            "text" :
            "password";
}

function togglePasswordCheckbox() {
    toggle(passwordInput);
}