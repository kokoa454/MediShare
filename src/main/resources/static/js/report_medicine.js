const reportButton = document.getElementById("send-mail-button");
const confirmDialog = document.getElementById("confirm-dialog");
const successDialog = document.getElementById("success-dialog");
const selectReportDialog = document.getElementById("select-report-dialog");
const errorDialog = document.getElementById("error-dialog");
const familyEmail = document.getElementById("family-email").value
const familyLineId = document.getElementById("family-line-id").value
const userEmail = document.getElementById("user-email").value
const selectEmail = document.getElementById("select-report-mail-button");
const selectLine = document.getElementById("select-report-line-button");
const sendingMessage = document.getElementById("sending-message");
const csrfToken = document.querySelector('meta[name="_csrf"]').content;
const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;

const urlParams = new URLSearchParams(window.location.search);
const userMedicineIds = urlParams.get('userMedicineIds');


reportButton.addEventListener("click", function() {
    sendingMessage.style.display = "none";

    if (!familyEmail && !familyLineId) {
        showError('設定画面からご家族のメールアドレスかLINE IDを入力してください');
        return;
    } else {
        const medicineElements = document.querySelectorAll('.medicine-name');
        const medicines = Array.from(medicineElements).map(el => el.textContent);
        const method = document.getElementById('medication-method').textContent;
        let userCondition = document.querySelector('input[name="user-condition"]:checked').value;
        switch(userCondition){
            case "good":
                userCondition = "体調は良いです。"
                break
            case "normal":
                userCondition = "体調はいつも通りです。"
                break
            case "bad":
                userCondition = "体調はいつもより悪いです。"
                break
        }
        let userComment = document.getElementById('user-comment').value;

        if(userComment == ""){
            userComment = userEmail + "さんからのコメントはありません。"
        }

        const now = new Date();
        const year = now.getFullYear();
        const month = String(now.getMonth() + 1).padStart(2, '0');
        const day = String(now.getDate()).padStart(2, '0');
        const formattedDate = `${year}-${month}-${day}`;

        confirmDialog.showModal();

        document.querySelector('#cancel-report-button').onclick = function() {
            confirmDialog.close();
        };

        document.querySelector('#confirm-report-button').onclick = function() {
            confirmDialog.close();
            selectReportDialog.showModal();
            document.querySelector('#select-report-mail-button').onclick = function() {
                if(familyEmail){
                    const reportData = {
                        userEmail: userEmail,
                        familyEmail: familyEmail,
                        medicines: medicines,
                        medicationMethod: method,
                        userCondition: userCondition,
                        userComment: userComment,
                        completedDate: formattedDate
                    };

                    sendingMessage.style.display = "block";

                    fetch('/report_medicine/send_mail?userMedicineIds=' + userMedicineIds, {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json',
                            [csrfHeader]: csrfToken
                        },
                        body: JSON.stringify(reportData)
                    })
                    .then(response => {
                        if (response.ok) return response.text();
                        else return response.text().then(msg => { throw new Error(msg); });
                    })
                    .then(message => {
                            selectReportDialog.close();
                            showSuccess(message);
                            document.querySelector('#success-close-dialog').onclick = () => location.href = '/dashboard';
                    })
                    .catch((error) => {
                        selectReportDialog.close();
                        showError(error.message || '通信エラーが発生しました');
                    });
                } else {
                    selectReportDialog.close();
                    showError('設定画面からご家族のメールアドレスを入力してください');
                }

            };
            document.querySelector('#select-report-line-button').onclick = function() {
                if(familyLineId){
                    const reportData = {
                        userEmail: userEmail,
                        medicines: medicines,
                        medicationMethod: method,
                        userCondition: userCondition,
                        userComment: userComment,
                        completedDate: formattedDate
                    };

                    sendingMessage.style.display = "block";

                    fetch('/report_medicine/send_line?userMedicineIds=' + userMedicineIds, {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json',
                            [csrfHeader]: csrfToken
                        },
                        body: JSON.stringify(reportData)
                    })
                    .then(response => {
                        if (response.ok) return response.text();
                        else return response.text().then(msg => { throw new Error(msg); });
                    })
                    .then(message => {
                            selectReportDialog.close();
                            showSuccess(message);
                            document.querySelector('#success-close-dialog').onclick = () => location.href = '/dashboard';
                    })
                    .catch((error) => {
                        selectReportDialog.close();
                        showError(error.message || '通信エラーが発生しました');
                    });
                } else {                    
                    selectReportDialog.close();
                    showError('設定画面からご家族のLINE IDを入力してください');
                }
            }
        };
    }
});

function showSuccess(message) {
    document.querySelector('#success-message').textContent = message;
    successDialog.showModal();
    document.querySelector('#success-close-dialog').onclick = () => successDialog.close();
}

function showError(message) {
    document.querySelector('#error-message').textContent = message;
    errorDialog.showModal();
    document.querySelector('#error-close-dialog').onclick = () => errorDialog.close();
}