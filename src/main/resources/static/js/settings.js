const userEmailInput = document.getElementById("user-email");
const userNameInput = document.getElementById("user-name");
const familyEmailInput = document.getElementById("family-email");
const familyLineInput = document.getElementById("family-line");
const changeUserEmailButton = document.getElementById("change-user-email");
const changeUserNameButton = document.getElementById("change-user-name");
const changeFamilyEmailButton = document.getElementById("change-family-email");
const changeFamilyLineButton = document.getElementById("change-family-line");
const confirmUserEmailButton = document.getElementById("confirm-user-email");
const confirmFamilyEmailButton = document.getElementById("confirm-family-email");
const confirmUserNameButton = document.getElementById("confirm-user-name");
const confirmFamilyLineButton = document.getElementById("confirm-family-line");
const errorUserEmail = document.getElementById("user-email-error");
const errorUserName = document.getElementById("user-name-error");
const errorFamilyEmail = document.getElementById("family-email-error");
const errorFamilyLine = document.getElementById("family-line-error");
const successDialog = document.getElementById("success-dialog");
const errorDialog = document.getElementById("error-dialog");
const howToRegisterFamilyLineDialog = document.getElementById("how-to-register-family-line-dialog");
const csrfToken = document.querySelector('meta[name="_csrf"]').content;
const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;
const beforeInputUserEmail = userEmailInput.value;
const beforeInputUserName = userNameInput.value;
const beforeInputFamilyEmail = familyEmailInput.value;
const beforeInputFamilyLine = familyLineInput.value;

changeUserEmailButton.addEventListener("click", () => {
    userEmailInput.removeAttribute("readonly");
    changeUserEmailButton.style.display = "none";
    confirmUserEmailButton.style.display = "block";
});

changeFamilyEmailButton.addEventListener("click", () => {
    familyEmailInput.removeAttribute("readonly");
    changeFamilyEmailButton.style.display = "none";
    confirmFamilyEmailButton.style.display = "block";
    if (familyEmailInput.value === "登録されていません") familyEmailInput.value = "";
});

changeUserNameButton.addEventListener("click", () => {
    userNameInput.removeAttribute("readonly");
    changeUserNameButton.style.display = "none";
    confirmUserNameButton.style.display = "block";
    if (userNameInput.value === "登録されていません") userNameInput.value = "";
});

changeFamilyLineButton.addEventListener("click", () => {
    showHowToRegisterFamilyLineDialog();
    familyLineInput.removeAttribute("readonly");
    changeFamilyLineButton.style.display = "none";
    confirmFamilyLineButton.style.display = "block";
    errorFamilyLine.style.display = "none";
    if (familyLineInput.value === "登録されていません") familyLineInput.value = "";
});

confirmUserEmailButton.addEventListener("click", () => {
    const confirmedUserEmail = userEmailInput.value;

    if (confirmedUserEmail === "") {
        userEmailInput.value = beforeInputUserEmail;
        showError('メールアドレスを入力してください');
        return;
    }

    if (confirmedUserEmail.match(/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/)) {
        errorUserEmail.style.display = "none";

        fetch('/settings/userEmail', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                [csrfHeader]: csrfToken
            },
            body: JSON.stringify({ confirmedUserEmail })
        })
        .then(response => {
            if (response.ok) return response.text();
            else return response.text().then(msg => { throw new Error(msg); });
        })
        .then(message => {
            userEmailInput.setAttribute("readonly", true);
            changeUserEmailButton.style.display = "block";
            confirmUserEmailButton.style.display = "none";
            showSuccess(message, "ログイン画面に戻ります");
            document.querySelector('#success-close-dialog').onclick = () => location.href = '/login';
        })
        .catch(error => {
            userEmailInput.value = beforeInputUserEmail;
            showError(error.message || '通信エラーが発生しました');
        });
    } else {
        errorUserEmail.style.display = "block";
    }
});

confirmFamilyEmailButton.addEventListener("click", () => {
    const confirmedFamilyEmail = familyEmailInput.value;

    if (confirmedFamilyEmail === "") {
        familyEmailInput.value = beforeInputFamilyEmail;
        showError('家族メールアドレスを入力してください');
        return;
    }

    if (confirmedFamilyEmail.match(/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/)) {
        errorFamilyEmail.style.display = "none";

        fetch('/settings/familyEmail', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                [csrfHeader]: csrfToken
            },
            body: JSON.stringify({ confirmedFamilyEmail })
        })
        .then(response => {
            if (response.ok) return response.text();
            else return response.text().then(msg => { throw new Error(msg); });
        })
        .then(message => {
            familyEmailInput.setAttribute("readonly", true);
            changeFamilyEmailButton.style.display = "block";
            confirmFamilyEmailButton.style.display = "none";
            showSuccess(message, null);
            document.querySelector('#success-close-dialog').onclick = () => successDialog.close();
        })
        .catch(error => {
            familyEmailInput.value = beforeInputFamilyEmail;
            showError(error.message || '通信エラーが発生しました');
        });
    } else {
        errorFamilyEmail.style.display = "block";
    }
});

confirmUserNameButton.addEventListener("click", () => {
    const confirmedUserName = userNameInput.value;

    if (confirmedUserName === "") {
        userNameInput.value = beforeInputUserName;
        showError('ユーザー名を入力してください');
        return;
    }

    errorUserName.style.display = "none";

    fetch('/settings/userName', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            [csrfHeader]: csrfToken
        },
        body: JSON.stringify({ confirmedUserName })
    })
    .then(response => {
        if (response.ok) return response.text();
        else return response.text().then(msg => { throw new Error(msg); });
    })
    .then(message => {
        userNameInput.setAttribute("readonly", true);
        changeUserNameButton.style.display = "block";
        confirmUserNameButton.style.display = "none";
        showSuccess(message, null);
        document.querySelector('#success-close-dialog').onclick = () => successDialog.close();
    })
    .catch(error => {
        userNameInput.value = beforeInputUserName;
        showError(error.message || '通信エラーが発生しました');
    });
});

confirmFamilyLineButton.addEventListener("click", () => {
    const confirmedFamilyLineId = familyLineInput.value;

    if (confirmedFamilyLineId === "") {
        familyLineInput.value = beforeInputFamilyLine;
        showError('家族LINE IDを入力してください');
        return;
    }

    if (confirmedFamilyLineId.match(/^U[a-fA-F0-9]{32}$/)) {
        errorFamilyLine.style.display = "none";

        fetch('/settings/familyLineId', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                [csrfHeader]: csrfToken
            },
            body: JSON.stringify({ confirmedFamilyLineId })
        })
        .then(response => {
            if (response.ok) return response.text();
            else return response.text().then(msg => { throw new Error(msg); });
        })
        .then(message => {
            familyLineInput.setAttribute("readonly", true);
            changeFamilyLineButton.style.display = "block";
            confirmFamilyLineButton.style.display = "none";
            showSuccess(message, null);
            document.querySelector('#success-close-dialog').onclick = () => successDialog.close();
        })
        .catch(error => {
            familyLineInput.value = beforeInputFamilyLine;
            showError(error.message || '通信エラーが発生しました');
        });
    } else {
        errorFamilyLine.style.display = "block";
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

function showHowToRegisterFamilyLineDialog() {
    howToRegisterFamilyLineDialog.showModal();
    document.querySelector('#how-to-register-family-line-close-dialog').onclick = () => howToRegisterFamilyLineDialog.close();
}
