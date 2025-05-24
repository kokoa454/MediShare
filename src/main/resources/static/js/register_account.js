const form = document.getElementById("register-account-form");
const dialog = document.getElementById("success-dialog");
const successDialogContent = document.getElementById("success-dialog-content");
const errorDialogContent = document.getElementById("error-dialog-content");
const dialogLoader = document.getElementById("loading-cat");
const goToLogin = document.getElementById("success-close-dialog");
const goToRegisterAccountAgain = document.getElementById("error-close-dialog");
const strengthBar = document.getElementById("password-strength-bar");
const strengthText = document.getElementById("password-strength-text");
const passwordInput = document.getElementById("password");
const usernameInput = document.getElementById("username");
const passwordValidationInput = document.getElementById("password-validation");
const username_error = document.getElementById("username-error");
const password_error = document.getElementById("password-error");
const passwordValidation_error = document.getElementById("password-validation-error");
const showPasswordButton = document.getElementById("password-eye");
const showPasswordValidationButton = document.getElementById("password-validation-eye");
const eyeOpenPath = showPasswordButton.dataset.eyeOpenPath;
const eyeClosePath = showPasswordButton.dataset.eyeClosePath;
const eyeOpenValidationPath = showPasswordValidationButton.dataset.eyeOpenPath;
const eyeCloseValidationPath = showPasswordValidationButton.dataset.eyeClosePath;

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
    }).then(response => response.text().then(text => {
        successDialogContent.style.display = "none";
        errorDialogContent.style.display = "none";
        dialogLoader.style.display = "none";

        if (response.ok) {
            showSuccessDialog();
        } else {
            showErrorDialog(text);
        }
    })).catch(error => {
        console.error(error);
        showErrorDialog("サーバーエラーが発生しました。");
    });
});

function showSuccessDialog() {
    dialog.showModal();
    dialogLoader.style.display = "block";

    setTimeout(() => {
        dialogLoader.style.display = "none";
        successDialogContent.style.display = "block";
    }, 3000);
}

function showErrorDialog(message) {
    dialog.showModal();
    
    errorDialogContent.style.display = "block";
    errorDialogContent.querySelector("p").textContent = message;
}

goToLogin.addEventListener("click", function() {
    dialog.close();
    window.location.href = "/login";
});

goToRegisterAccountAgain.addEventListener("click", function() {
    dialog.close();
    window.location.href = "/register_account";
});

showPasswordButton.addEventListener("click", showPassword);

function showPassword(){
    if(passwordInput.type === "password"){
        passwordInput.type = "text";
        passwordInput.style.fontSize = "20px"
        showPasswordButton.src = eyeOpenPath;
    }else{
        passwordInput.type = "password";
        showPasswordButton.src = eyeClosePath;
    }

    if(passwordInput.value.length > 0){
        passwordInput.setSelectionRange(passwordInput.value.length, passwordInput.value.length);
    }
}

showPasswordValidationButton.addEventListener("click", showPasswordValidation);

function showPasswordValidation(){
    if(passwordValidationInput.type === "password"){
        passwordValidationInput.type = "text";
        passwordValidationInput.style.fontSize = "20px"
        showPasswordValidationButton.src = eyeOpenValidationPath;
    }else{
        passwordValidationInput.type = "password";
        showPasswordValidationButton.src = eyeCloseValidationPath;
    }

    if(passwordValidationInput.value.length > 0){
        passwordValidationInput.setSelectionRange(passwordValidationInput.value.length, passwordValidationInput.value.length);
    }
}