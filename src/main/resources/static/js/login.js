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