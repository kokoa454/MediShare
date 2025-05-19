const form = document.getElementById("register-account-form");
const dialog = document.getElementById("success-dialog");
const dialogContent = document.getElementById("success-dialog-content");
const dialogLoader = document.getElementById("loading-cat");
const goToLogin = document.getElementById("close-dialog");
const strengthBar = document.getElementById("password-strength-bar");
const strengthText = document.getElementById("password-strength-text");
const passwordInput = document.getElementById("password");
const usernameInput = document.getElementById("username");
const passwordValidationInput = document.getElementById("password-validation");
const username_error = document.getElementById("username-error");
const password_error = document.getElementById("password-error");
const passwordValidation_error = document.getElementById("password-validation-error");

passwordInput.addEventListener("input", () => {
    let password = passwordInput.value;
    let username = usernameInput.value;

    let result = zxcvbn(password, [username]);

    let score = result.score;

    if(password.length === 0){
        strengthBar.style.width = "0";
        strengthText.textContent = "入力してください";
    } else{
        switch (score) {
        case 0:
            strengthBar.style.width = "20%";
            strengthBar.style.backgroundColor = "#ff4d4d";
            strengthText.textContent = "非常に弱いパスワードレベル";
            break;
        case 1:
            strengthBar.style.width = "40%";
            strengthBar.style.backgroundColor = "#ff8533";
            strengthText.textContent = "弱いパスワードレベル";
            break;
        case 2:
            strengthBar.style.width = "60%";
            strengthBar.style.backgroundColor = "#ffd633";
            strengthText.textContent = "普通のパスワードレベル";
            break;
        case 3:
            strengthBar.style.width = "80%";
            strengthBar.style.backgroundColor = "#99cc33";
            strengthText.textContent = "強いパスワードレベル";
            break;
        case 4:
            strengthBar.style.width = "100%";
            strengthBar.style.backgroundColor = "#33cc33";
            strengthText.textContent = "非常に強いパスワードレベル";
            break;
        default:
            strengthBar.style.width = "0";
            strengthText.textContent = "";
    }
    }
});


form.addEventListener("submit", function(event) {
    event.preventDefault();

    const usernamePattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    const passwordPattern = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,}$/;

    let username = usernameInput.value;
    let password = passwordInput.value;
    let passwordValidation = passwordValidationInput.value;

    username_error.style.display = "none";
    password_error.style.display = "none";

    let isValid = true;

    if (!usernamePattern.test(username)) {
        username_error.style.display = "block";
        isValid = false;
    }

    if (!passwordPattern.test(password)) {
        password_error.style.display = "block";
        isValid = false;
    }

    if (password !== passwordValidation) {
        passwordValidation_error.style.display = "block";
        isValid = false;
    }
    
    if (!isValid) {
        return;
    }

    const formData = new FormData(form);
    fetch("/register_account", {
        method: "POST",
        body: formData
    }).then(response => {
        if (response.ok) {
            showLoaderDialog();
        } else {
            alert("アカウント作成失敗");
        }
    }).catch(error => {
        console.error(error);
    })
});

goToLogin.addEventListener("click", function() {
    dialog.close();
    window.location.href = "/login";
});

function showLoaderDialog() {
    dialog.showModal();
    dialogContent.style.display = "none";
    dialogLoader.style.display = "block";

    setTimeout(() => {
        dialogLoader.style.display = "none";
        dialogContent.style.display = "block";
    }, 3000);
}