
const UI_ELEMENTS = {
    SPINNER_ID: "login-spinner",
    LOGIN_ICON_ID: "login-icon",
    WAIT_MESSAGE: "Por favor espere...",
    EMAIL_ID: "email",
    PASSWORD_ID: "password",
    LOGIN_BUTTON_ID: "loginButton",
    LOGIN_FORM_ID: "loginForm",
    LOGIN_TEXT_ID: "login-text"
};

const email = document.getElementById(UI_ELEMENTS.EMAIL_ID);
const password = document.getElementById(UI_ELEMENTS.PASSWORD_ID);
const loginForm = document.getElementById(UI_ELEMENTS.LOGIN_FORM_ID);
const loginButton = document.getElementById(UI_ELEMENTS.LOGIN_BUTTON_ID);
const loginButtonText = document.getElementById(UI_ELEMENTS.LOGIN_TEXT_ID);

function handleFormSubmit(e) {
    if (!e) return;
    e.preventDefault();

    if (email.value === "" || password.value === "" || loginButton.disabled) {
        return;
    }

    loginForm.submit();

    const spinner = document.getElementById(UI_ELEMENTS.SPINNER_ID);
    const loginIcon = document.getElementById(UI_ELEMENTS.LOGIN_ICON_ID);

    toggleElementVisibility(loginIcon, spinner);
    disableLoginButton();
}

function toggleElementVisibility(loginIcon, spinner) {
    loginIcon.classList.add("hidden");
    spinner.classList.remove("hidden");
}

function disableLoginButton() {
    loginButton.disabled = true;
    loginButtonText.textContent = UI_ELEMENTS.WAIT_MESSAGE;
    loginButtonText.classList.add("disabled");
}


document.addEventListener("DOMContentLoaded", function () {
    if (loginForm) {
        loginForm.addEventListener("submit", handleFormSubmit);
    }
})