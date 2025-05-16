//register_account.html
const form = document.getElementById("register-account-form");

form.addEventListener("submit", function(event) {
    event.preventDefault();

    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    const usernamePattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    const passwordPattern = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,}$/;

    const username_error = document.getElementById("username-error");
    const password_error = document.getElementById("password-error");

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

    if (!isValid) {
        return;
    }

    const formData = new FormData(form);
    fetch("/register_account", {
        method: "POST",
        body: formData
    }).then(response => {
        if (response.ok) {
            alert("アカウント作成成功");
            window.location.href = "/login";
        } else {
            alert("アカウント作成失敗");
        }
    }).catch(error => {
        console.error(error);
    })
});