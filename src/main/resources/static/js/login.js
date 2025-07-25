const form = document.getElementById("login-form");
const dialog = document.getElementById("loading-dialog");
const dialogLoader = document.getElementById("loading-cat");
const loadingMessage = document.getElementById("loading-message");

const userInput = document.getElementById("username");
const rememberUsername = document.getElementById("remember-username");

const passwordInput = document.getElementById("password");
const showPasswordButton = document.getElementById("password-eye");
const eyeOpenPath = showPasswordButton.dataset.eyeOpenPath;
const eyeClosePath = showPasswordButton.dataset.eyeClosePath;

const doesForgetPassword = document.getElementById("does-forget-password");
const resetPasswordDialog = document.getElementById("reset-password-dialog");
const resetPasswordEmailInput = document.getElementById("reset-password-email-input");
const confirmResetPasswordButton = document.getElementById("confirm-reset-password-button");
const resetPasswordMessage = document.getElementById("reset-password-message");
const resetPasswordMessage2 = document.getElementById("reset-password-message2");
const cancelResetPasswordButton = document.getElementById("cancel-reset-password-button");
const resetPasswordErrorMessage = document.getElementById("reset-password-message-error");
const closeResetPasswordDialog = document.getElementById("close-reset-password-dialog");

const csrfToken = document.querySelector('meta[name="_csrf"]').content;
const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;

function showDialog(event){
    showLoadingDialog();
}

function showLoadingDialog() {
    dialog.showModal();
    dialogLoader.style.display = "block";

    setTimeout(() => {
        loadingMessage.textContent = "サーバーに接続中...";
    }, 3000);
}

window.addEventListener("load", function() {
    const savedUsername = localStorage.getItem("username");

    if (savedUsername) {
        userInput.value = savedUsername;
        rememberUsername.checked = true;
    }

    rememberUsername.addEventListener("change", function() {
        if (rememberUsername.checked) {
            localStorage.setItem("username", userInput.value);
        } else {
            localStorage.removeItem("username");
        }
    });

    userInput.addEventListener("input", function() {
        localStorage.setItem("username", userInput.value);
    });
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

doesForgetPassword.addEventListener("click", () => {
    resetPasswordDialog.showModal();

    confirmResetPasswordButton.addEventListener("click", () => {
        const email = resetPasswordEmailInput.value;
        resetPasswordErrorMessage.style.display = "none";

        if (email === "") {
            resetPasswordErrorMessage.textContent = "メールアドレスを入力してください";
            resetPasswordErrorMessage.style.display = "block";
            return;
        }

        if(email.match("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")){
            fetch('/login/reset_password', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    [csrfHeader]: csrfToken
                },
                body: JSON.stringify({ email })
            })
            .then(response => {
                if (response.ok) return response.text();
                else return response.text().then(msg => { throw new Error(msg); });
            })
            .then(message => {
                resetPasswordMessage.textContent = message;
                resetPasswordMessage2.style.display = "none";
                resetPasswordEmailInput.value = "";
                resetPasswordEmailInput.style.display = "none";
                resetPasswordErrorMessage.style.display = "none";
                confirmResetPasswordButton.style.display = "none";
                cancelResetPasswordButton.style.display = "none";
                closeResetPasswordDialog.style.display = "block";

                closeResetPasswordDialog.addEventListener("click", () => {
                    resetPasswordDialog.close();
                });
            })
        } else {
            resetPasswordErrorMessage.textContent = "正しいメールアドレスを入力してください";
            resetPasswordErrorMessage.style.display = "block";
        }
    });

    cancelResetPasswordButton.addEventListener("click", () => {
        resetPasswordDialog.close();
    });
});