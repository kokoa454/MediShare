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

const wakeUpInput = document.getElementById("wakeUp");
const beforeBreakfastInput = document.getElementById("beforeBreakfast");
const afterBreakfastInput = document.getElementById("afterBreakfast");
const beforeLunchInput = document.getElementById("beforeLunch");
const afterLunchInput = document.getElementById("afterLunch");
const beforeDinnerInput = document.getElementById("beforeDinner");
const afterDinnerInput = document.getElementById("afterDinner");
const beforeSleepInput = document.getElementById("beforeSleep");
const betweenMealsInput = document.getElementById("betweenMeals");

const changeWakeUpButton = document.getElementById("change-wakeUp");
const changeBeforeBreakfastButton = document.getElementById("change-beforeBreakfast");
const changeAfterBreakfastButton = document.getElementById("change-afterBreakfast");
const changeBeforeLunchButton = document.getElementById("change-beforeLunch");
const changeAfterLunchButton = document.getElementById("change-afterLunch");
const changeBeforeDinnerButton = document.getElementById("change-beforeDinner");
const changeAfterDinnerButton = document.getElementById("change-afterDinner");
const changeBeforeSleepButton = document.getElementById("change-beforeSleep");
const changeBetweenMealsButton = document.getElementById("change-betweenMeals");

const confirmWakeUpButton = document.getElementById("confirm-wakeUp");
const confirmBeforeBreakfastButton = document.getElementById("confirm-beforeBreakfast");
const confirmAfterBreakfastButton = document.getElementById("confirm-afterBreakfast");
const confirmBeforeLunchButton = document.getElementById("confirm-beforeLunch");
const confirmAfterLunchButton = document.getElementById("confirm-afterLunch");
const confirmBeforeDinnerButton = document.getElementById("confirm-beforeDinner");
const confirmAfterDinnerButton = document.getElementById("confirm-afterDinner");
const confirmBeforeSleepButton = document.getElementById("confirm-beforeSleep");
const confirmBetweenMealsButton = document.getElementById("confirm-betweenMeals");

const errorWakeUp = document.getElementById("wakeUp-error");
const errorBeforeBreakfast = document.getElementById("beforeBreakfast-error");
const errorAfterBreakfast = document.getElementById("afterBreakfast-error");
const errorBeforeLunch = document.getElementById("beforeLunch-error");
const errorAfterLunch = document.getElementById("afterLunch-error");
const errorBeforeDinner = document.getElementById("beforeDinner-error");
const errorAfterDinner = document.getElementById("afterDinner-error");
const errorBeforeSleep = document.getElementById("beforeSleep-error");
const errorBetweenMeals = document.getElementById("betweenMeals-error");

const beforeInputWakeup = wakeUpInput.value;
const beforeInputBeforeBreakfast = beforeBreakfastInput.value;
const beforeInputAfterBreakfast = afterBreakfastInput.value;
const beforeInputBeforeLunch = beforeLunchInput.value;
const beforeInputAfterLunch = afterLunchInput.value;
const beforeInputBeforeDinner = beforeDinnerInput.value;
const beforeInputAfterDinner = afterDinnerInput.value;
const beforeInputBeforeSleep = beforeSleepInput.value;
const beforeInputBetweenMeals = betweenMealsInput.value;

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

changeWakeUpButton.addEventListener("click", () => {
    wakeUpInput.removeAttribute("readonly");
    changeWakeUpButton.style.display = "none";
    confirmWakeUpButton.style.display = "block";
    errorWakeUp.style.display = "none";
});

changeBeforeBreakfastButton.addEventListener("click", () => {
    beforeBreakfastInput.removeAttribute("readonly");
    changeBeforeBreakfastButton.style.display = "none";
    confirmBeforeBreakfastButton.style.display = "block";
    errorBeforeBreakfast.style.display = "none";
});

changeAfterBreakfastButton.addEventListener("click", () => {
    afterBreakfastInput.removeAttribute("readonly");
    changeAfterBreakfastButton.style.display = "none";
    confirmAfterBreakfastButton.style.display = "block";
    errorAfterBreakfast.style.display = "none";
});

changeBeforeLunchButton.addEventListener("click", () => {
    beforeLunchInput.removeAttribute("readonly");
    changeBeforeLunchButton.style.display = "none";
    confirmBeforeLunchButton.style.display = "block";
    errorBeforeLunch.style.display = "none";
});

changeAfterLunchButton.addEventListener("click", () => {
    afterLunchInput.removeAttribute("readonly");
    changeAfterLunchButton.style.display = "none";
    confirmAfterLunchButton.style.display = "block";
    errorAfterLunch.style.display = "none";
});

changeBeforeDinnerButton.addEventListener("click", () => {
    beforeDinnerInput.removeAttribute("readonly");
    changeBeforeDinnerButton.style.display = "none";
    confirmBeforeDinnerButton.style.display = "block";
    errorBeforeDinner.style.display = "none";
});

changeAfterDinnerButton.addEventListener("click", () => {
    afterDinnerInput.removeAttribute("readonly");
    changeAfterDinnerButton.style.display = "none";
    confirmAfterDinnerButton.style.display = "block";
    errorAfterDinner.style.display = "none";
});

changeBeforeSleepButton.addEventListener("click", () => {
    beforeSleepInput.removeAttribute("readonly");
    changeBeforeSleepButton.style.display = "none";
    confirmBeforeSleepButton.style.display = "block";
    errorBeforeSleep.style.display = "none";
});

changeBetweenMealsButton.addEventListener("click", () => {
    betweenMealsInput.removeAttribute("readonly");
    changeBetweenMealsButton.style.display = "none";
    confirmBetweenMealsButton.style.display = "block";
    errorBetweenMeals.style.display = "none";
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

confirmWakeUpButton.addEventListener("click", () => {
    const confirmedWakeUpTime = wakeUpInput.value;

    if (confirmedWakeUpTime === "") {
        wakeUpInput.value = beforeInputWakeup;
        showError('起床時間を入力してください');
        return;
    }

    errorWakeUp.style.display = "none";
    if(confirmedWakeUpTime.match(/^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$/)) {
        errorWakeUp.style.display = "none";

        fetch('/settings/wakeUpTime', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                [csrfHeader]: csrfToken
            },
            body: JSON.stringify({ confirmedWakeUpTime })
        })
        .then(response => {
            if (response.ok) return response.text();
            else return response.text().then(msg => { throw new Error(msg); });
        })
        .then(message => {
            wakeUpInput.setAttribute("readonly", true);
            changeWakeUpButton.style.display = "block";
            confirmWakeUpButton.style.display = "none";
            showSuccess(message, null);
            document.querySelector('#success-close-dialog').onclick = () => successDialog.close();
        })
        .catch(error => {
            wakeUpInput.value = beforeInputWakeup;
            showError(error.message || '通信エラーが発生しました');
        });
    } else {
        errorWakeUp.style.display = "block";
    }
 })

 confirmBeforeBreakfastButton.addEventListener("click", () => {
    const confirmedBeforeBreakfastTime = beforeBreakfastInput.value;

    if (confirmedBeforeBreakfastTime === "") {
        beforeBreakfastInput.value = beforeInputBeforeBreakfast;
        showError('朝食前時間を入力してください');
        return;
    }

    errorBeforeBreakfast.style.display = "none";
    if (confirmedBeforeBreakfastTime.match(/^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$/)) {
        errorBeforeBreakfast.style.display = "none";

        fetch('/settings/beforeBreakfastTime', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json', [csrfHeader]: csrfToken },
            body: JSON.stringify({ confirmedBeforeBreakfastTime })
        })
        .then(response => response.ok ? response.text() : response.text().then(msg => { throw new Error(msg); }))
        .then(message => {
            beforeBreakfastInput.setAttribute("readonly", true);
            changeBeforeBreakfastButton.style.display = "block";
            confirmBeforeBreakfastButton.style.display = "none";
            showSuccess(message, null);
            document.querySelector('#success-close-dialog').onclick = () => successDialog.close();
        })
        .catch(error => {
            beforeBreakfastInput.value = beforeInputBeforeBreakfast;
            showError(error.message || '通信エラーが発生しました');
        });
    } else {
        errorBeforeBreakfast.style.display = "block";
    }
});

confirmAfterBreakfastButton.addEventListener("click", () => {
    const confirmedAfterBreakfastTime = afterBreakfastInput.value;

    if (confirmedAfterBreakfastTime === "") {
        afterBreakfastInput.value = beforeInputAfterBreakfast;
        showError('朝食後時間を入力してください');
        return;
    }

    errorAfterBreakfast.style.display = "none";
    if (confirmedAfterBreakfastTime.match(/^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$/)) {
        errorAfterBreakfast.style.display = "none";

        fetch('/settings/afterBreakfastTime', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json', [csrfHeader]: csrfToken },
            body: JSON.stringify({ confirmedAfterBreakfastTime })
        })
        .then(response => response.ok ? response.text() : response.text().then(msg => { throw new Error(msg); }))
        .then(message => {
            afterBreakfastInput.setAttribute("readonly", true);
            changeAfterBreakfastButton.style.display = "block";
            confirmAfterBreakfastButton.style.display = "none";
            showSuccess(message, null);
            document.querySelector('#success-close-dialog').onclick = () => successDialog.close();
        })
        .catch(error => {
            afterBreakfastInput.value = beforeInputAfterBreakfast;
            showError(error.message || '通信エラーが発生しました');
        });
    } else {
        errorAfterBreakfast.style.display = "block";
    }
});

confirmBeforeLunchButton.addEventListener("click", () => {
    const confirmedBeforeLunchTime = beforeLunchInput.value;

    if (confirmedBeforeLunchTime === "") {
        beforeLunchInput.value = beforeInputBeforeLunch;
        showError('昼食前時間を入力してください');
        return;
    }

    errorBeforeLunch.style.display = "none";
    if (confirmedBeforeLunchTime.match(/^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$/)) {
        errorBeforeLunch.style.display = "none";

        fetch('/settings/beforeLunchTime', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json', [csrfHeader]: csrfToken },
            body: JSON.stringify({ confirmedBeforeLunchTime })
        })
        .then(response => response.ok ? response.text() : response.text().then(msg => { throw new Error(msg); }))
        .then(message => {
            beforeLunchInput.setAttribute("readonly", true);
            changeBeforeLunchButton.style.display = "block";
            confirmBeforeLunchButton.style.display = "none";
            showSuccess(message, null);
            document.querySelector('#success-close-dialog').onclick = () => successDialog.close();
        })
        .catch(error => {
            beforeLunchInput.value = beforeInputBeforeLunch;
            showError(error.message || '通信エラーが発生しました');
        });
    } else {
        errorBeforeLunch.style.display = "block";
    }
});

confirmAfterLunchButton.addEventListener("click", () => {
    const confirmedAfterLunchTime = afterLunchInput.value;

    if (confirmedAfterLunchTime === "") {
        afterLunchInput.value = beforeInputAfterLunch;
        showError('昼食後時間を入力してください');
        return;
    }

    errorAfterLunch.style.display = "none";
    if (confirmedAfterLunchTime.match(/^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$/)) {
        errorAfterLunch.style.display = "none";

        fetch('/settings/afterLunchTime', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json', [csrfHeader]: csrfToken },
            body: JSON.stringify({ confirmedAfterLunchTime })
        })
        .then(response => response.ok ? response.text() : response.text().then(msg => { throw new Error(msg); }))
        .then(message => {
            afterLunchInput.setAttribute("readonly", true);
            changeAfterLunchButton.style.display = "block";
            confirmAfterLunchButton.style.display = "none";
            showSuccess(message, null);
            document.querySelector('#success-close-dialog').onclick = () => successDialog.close();
        })
        .catch(error => {
            afterLunchInput.value = beforeInputAfterLunch;
            showError(error.message || '通信エラーが発生しました');
        });
    } else {
        errorAfterLunch.style.display = "block";
    }
});

confirmBeforeDinnerButton.addEventListener("click", () => {
    const confirmedBeforeDinnerTime = beforeDinnerInput.value;

    if (confirmedBeforeDinnerTime === "") {
        beforeDinnerInput.value = beforeInputBeforeDinner;
        showError('夕食前時間を入力してください');
        return;
    }

    errorBeforeDinner.style.display = "none";
    if (confirmedBeforeDinnerTime.match(/^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$/)) {
        errorBeforeDinner.style.display = "none";

        fetch('/settings/beforeDinnerTime', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json', [csrfHeader]: csrfToken },
            body: JSON.stringify({ confirmedBeforeDinnerTime })
        })
        .then(response => response.ok ? response.text() : response.text().then(msg => { throw new Error(msg); }))
        .then(message => {
            beforeDinnerInput.setAttribute("readonly", true);
            changeBeforeDinnerButton.style.display = "block";
            confirmBeforeDinnerButton.style.display = "none";
            showSuccess(message, null);
            document.querySelector('#success-close-dialog').onclick = () => successDialog.close();
        })
        .catch(error => {
            beforeDinnerInput.value = beforeInputBeforeDinner;
            showError(error.message || '通信エラーが発生しました');
        });
    } else {
        errorBeforeDinner.style.display = "block";
    }
});

confirmAfterDinnerButton.addEventListener("click", () => {
    const confirmedAfterDinnerTime = afterDinnerInput.value;

    if (confirmedAfterDinnerTime === "") {
        afterDinnerInput.value = beforeInputAfterDinner;
        showError('夕食後時間を入力してください');
        return;
    }

    errorAfterDinner.style.display = "none";
    if (confirmedAfterDinnerTime.match(/^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$/)) {
        errorAfterDinner.style.display = "none";

        fetch('/settings/afterDinnerTime', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json', [csrfHeader]: csrfToken },
            body: JSON.stringify({ confirmedAfterDinnerTime })
        })
        .then(response => response.ok ? response.text() : response.text().then(msg => { throw new Error(msg); }))
        .then(message => {
            afterDinnerInput.setAttribute("readonly", true);
            changeAfterDinnerButton.style.display = "block";
            confirmAfterDinnerButton.style.display = "none";
            showSuccess(message, null);
            document.querySelector('#success-close-dialog').onclick = () => successDialog.close();
        })
        .catch(error => {
            afterDinnerInput.value = beforeInputAfterDinner;
            showError(error.message || '通信エラーが発生しました');
        });
    } else {
        errorAfterDinner.style.display = "block";
    }
});

confirmBeforeSleepButton.addEventListener("click", () => {
    const confirmedBeforeSleepTime = beforeSleepInput.value;

    if (confirmedBeforeSleepTime === "") {
        beforeSleepInput.value = beforeInputBeforeSleep;
        showError('就寝前時間を入力してください');
        return;
    }

    errorBeforeSleep.style.display = "none";
    if (confirmedBeforeSleepTime.match(/^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$/)) {
        errorBeforeSleep.style.display = "none";

        fetch('/settings/beforeSleepTime', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json', [csrfHeader]: csrfToken },
            body: JSON.stringify({ confirmedBeforeSleepTime })
        })
        .then(response => response.ok ? response.text() : response.text().then(msg => { throw new Error(msg); }))
        .then(message => {
            beforeSleepInput.setAttribute("readonly", true);
            changeBeforeSleepButton.style.display = "block";
            confirmBeforeSleepButton.style.display = "none";
            showSuccess(message, null);
            document.querySelector('#success-close-dialog').onclick = () => successDialog.close();
        })
        .catch(error => {
            beforeSleepInput.value = beforeInputBeforeSleep;
            showError(error.message || '通信エラーが発生しました');
        });
    } else {
        errorBeforeSleep.style.display = "block";
    }
});

confirmBetweenMealsButton.addEventListener("click", () => {
    const confirmedBetweenMealsTime = betweenMealsInput.value;

    if (confirmedBetweenMealsTime === "") {
        betweenMealsInput.value = beforeInputBetweenMeals;
        showError('食間時間を入力してください');
        return;
    }

    errorBetweenMeals.style.display = "none";
    if (confirmedBetweenMealsTime.match(/^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$/)) {
        errorBetweenMeals.style.display = "none";

        fetch('/settings/betweenMealsTime', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json', [csrfHeader]: csrfToken },
            body: JSON.stringify({ confirmedBetweenMealsTime })
        })
        .then(response => response.ok ? response.text() : response.text().then(msg => { throw new Error(msg); }))
        .then(message => {
            betweenMealsInput.setAttribute("readonly", true);
            changeBetweenMealsButton.style.display = "block";
            confirmBetweenMealsButton.style.display = "none";
            showSuccess(message, null);
            document.querySelector('#success-close-dialog').onclick = () => successDialog.close();
        })
        .catch(error => {
            betweenMealsInput.value = beforeInputBetweenMeals;
            showError(error.message || '通信エラーが発生しました');
        });
    } else {
        errorBetweenMeals.style.display = "block";
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
