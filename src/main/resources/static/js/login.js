const form = document.getElementById("login-form");
const dialog = document.getElementById("success-dialog");
const successDialogContent = document.getElementById("success-dialog-content");
const errorDialogContent = document.getElementById("error-dialog-content");
const goToDashboard = document.getElementById("success-close-dialog");
const goToLoginAgain = document.getElementById("error-close-dialog");
const usernameInput = document.getElementById("username");
const passwordInput = document.getElementById("password");

form.addEventListener("submit", function(event) {
    event.preventDefault();

    const formData = new FormData(form);
    fetch("/login", {
        method: "POST",
        body: formData
    }).then(response => response.text().then(text => {
        successDialogContent.style.display = "none";
        errorDialogContent.style.display = "none";
        dialog.style.display = "none";

        if(response.ok){
            showSuccessDialog();
        } else{
            showErrorDialog(text);
        }
    })).catch(error =>{
        console.error(error);
        showErrorDialog("サーバーエラーが発生しました。")
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
    goToLoginAgain.addEventListener("click", function() {
        window.location.href = "/login";
    });
}

goToDashboard.addEventListener("click", function() {
    dialog.close();
    window.location.href = "/register_account";
});

goToLoginAgain.addEventListener("click", function() {
    dialog.close();
    window.location.href = "/login";
});