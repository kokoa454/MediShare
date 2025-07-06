const resetPassword = document.getElementById("reset-password-button");
const successDialog = document.getElementById("reset-password-success-dialog");
const errorDialog = document.getElementById("reset-password-error-dialog");
const strengthBar = document.getElementById("password-strength-bar");
const strengthText = document.getElementById("password-strength-text");
const passwordInput = document.getElementById("password");
const passwordValidationInput = document.getElementById("password-validation");
const password_error = document.getElementById("password-error");
const passwordValidation_error = document.getElementById("password-validation-error");
const showPasswordButton = document.querySelector(".input-group:nth-of-type(1) .show-password");
const showPasswordValidationButton = document.querySelector(".input-group:nth-of-type(2) .show-password");
const eyeOpenPath = showPasswordButton.dataset.eyeOpenPath;
const eyeClosePath = showPasswordButton.dataset.eyeClosePath;
const eyeOpenValidationPath = showPasswordValidationButton.dataset.eyeOpenPath;
const eyeCloseValidationPath = showPasswordValidationButton.dataset.eyeClosePath;
const token = document.getElementById("token").value;
const csrfToken = document.querySelector('meta[name="_csrf"]').content;
const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;

passwordInput.addEventListener("input", () => {
    let password = passwordInput.value;

    let result = zxcvbn(password, []);

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


resetPassword.addEventListener("click", () => {
    const passwordPattern = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,}$/;

    let password = passwordInput.value;
    let passwordValidation = passwordValidationInput.value;

    let isValid = true;

    password_error.style.display = "none";
    passwordValidation_error.style.display = "none";

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

    const data = {
        password: password,
        token: token
    };

    fetch("/reset_password", {
        method: "POST",
        headers: {
            [csrfHeader]: csrfToken,
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
    })
    .then(response => {
        if (response.ok) return response.text();
        else return response.text().then(msg => { throw new Error(msg); });
    })
    .then(message => {
        showSuccessDialog(message || "パスワードを変更しました");
        document.querySelector('#close-reset-password-success-dialog').onclick = () => location.href = '/login';
    })
    .catch(error => {
        showErrorDialog(error.message || '通信エラーが発生しました');
        document.querySelector('#close-reset-password-error-dialog').onclick = () => location.href = '/login';
    });
});

function showSuccessDialog(message) {
    successDialog.showModal();
    successDialog.querySelector("h2").textContent = message;
}

function showErrorDialog(message) {
    errorDialog.showModal();
    errorDialog.querySelector("h2").textContent = message;
    errorDialog.querySelector('p').style.display = "block";
}

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