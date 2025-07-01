const userEmailInput = document.getElementById("user-email");
const familyEmailInput = document.getElementById("family-email");
const changeUserEmailButton = document.getElementById("change-user-email");
const changeFamilyEmailButton = document.getElementById("change-family-email");
const confirmUserEmailButton = document.getElementById("confirm-user-email");
const confirmFamilyEmailButton = document.getElementById("confirm-family-email");
const errorUserEmail = document.getElementById("user-email-error");
const errorFamilyEmail = document.getElementById("family-email-error");
const successDialog = document.getElementById("success-dialog");
const errorDialog = document.getElementById("error-dialog");
const csrfToken = document.querySelector('meta[name="_csrf"]').content;
const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;

changeUserEmailButton.addEventListener("click", function() {
    userEmailInput.removeAttribute("readonly");
    changeUserEmailButton.style.display = "none";
    confirmUserEmailButton.style.display = "block";
});

changeFamilyEmailButton.addEventListener("click", function() {
    familyEmailInput.removeAttribute("readonly");
    changeFamilyEmailButton.style.display = "none";
    confirmFamilyEmailButton.style.display = "block";
    if(familyEmailInput.value === "登録されていません") {
        familyEmailInput.value = ""
        return;
    }
});

confirmUserEmailButton.addEventListener("click", function() {
    const confirmedUserEmail = userEmailInput.value;
    if(confirmedUserEmail.match(/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/)) {
        userEmailInput.setAttribute("readonly", true);
        changeUserEmailButton.style.display = "block";
        confirmUserEmailButton.style.display = "none";
        errorUserEmail.style.display = "none";

        fetch('/settings/userEmail', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                [csrfHeader]: csrfToken
            },
            body: JSON.stringify({confirmedUserEmail})
        })
        .then(() => {
            showSuccess('ユーザーメールアドレスを変更しました', "ログイン画面に戻ります");
            document.querySelector('#success-close-dialog').onclick = () => location.href = '/login';
        })
        .catch(() => {
            showError('通信エラーが発生しました');
        });
    } else {
        errorUserEmail.style.display = "block";
    }
});

confirmFamilyEmailButton.addEventListener("click", function() {
    const confirmedFamilyEmail = familyEmailInput.value;
    if(confirmedFamilyEmail.match(/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/)) {
        familyEmailInput.setAttribute("readonly", true);
        changeFamilyEmailButton.style.display = "block";
        confirmFamilyEmailButton.style.display = "none";
        errorFamilyEmail.style.display = "none";

        fetch('/settings/familyEmail', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                [csrfHeader]: csrfToken
            },
            body: JSON.stringify({confirmedFamilyEmail})
        })
        .then(response => {
            if (response.ok) {
                showSuccess('家族メールアドレスを変更しました', null);
                document.querySelector('#success-close-dialog').onclick = () => successDialog.close();
            } else {
                showError('家族メールアドレスの変更に失敗しました');
            }
        })
        .catch(() => {
            showError('通信エラーが発生しました');
        });
    } else {
        errorFamilyEmail.style.display = "block";
    }
});

function showError(message) {
    errorDialog.showModal();
    errorDialog.querySelector("h2").textContent = message;
    document.querySelector('#error-close-dialog').onclick = () => errorDialog.close();
}

function showSuccess(message, message2) {
    successDialog.showModal();
    successDialog.querySelector("h2").textContent = message;
    successDialog.querySelector("p").textContent = message2;
}