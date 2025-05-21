const form = document.getElementById("login-form");
const dialog = document.getElementById("loading-dialog");
const dialogLoader = document.getElementById("loading-cat");
const loadingMessage = document.getElementById("loading-message");

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
