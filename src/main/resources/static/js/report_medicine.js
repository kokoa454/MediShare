const reportButton = document.getElementById("send-mail-button");
const confirmDialog = document.getElementById("confirm-dialog");
const successDialog = document.getElementById("success-dialog");
const errorDialog = document.getElementById("error-dialog");
const familyEmail = document.getElementById("family-email").value
const userEmail = document.getElementById("user-email").value
const csrfToken = document.querySelector('meta[name="_csrf"]').content;
const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;

const urlParams = new URLSearchParams(window.location.search);
const userMedicineIds = urlParams.get('userMedicineIds');


reportButton.addEventListener("click", function() {
    if (!familyEmail) {
        showError('設定画面からご家族のメールアドレスを入力してください');
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

        const reportData = {
            userEmail: userEmail,
            familyEmail: familyEmail,
            medicines: medicines,
            medicationMethod: method,
            userCondition: userCondition,
            userComment: userComment,
            completedDate: formattedDate
        };

        confirmDialog.showModal();

        document.querySelector('#cancel-report-button').onclick = function() {
            confirmDialog.close();
        };

        document.querySelector('#confirm-report-button').onclick = function() {
            fetch('/report_medicine?userMedicineIds=' + userMedicineIds, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    [csrfHeader]: csrfToken
                },
                body: JSON.stringify(reportData)
            })
            .then(response => {
                if (response.ok) {
                    successDialog.showModal();
                    document.querySelector('#success-close-dialog').onclick = () => location.href = '/dashboard';
                } else {
                    confirmDialog.close()
                    showError('服薬の報告に失敗しました');
                }
            })
            .catch(() => {
                confirmDialog.close()
                showError('通信エラーが発生しました');
            });
        };
    }
});

function showError(message) {
    document.querySelector('#error-message').textContent = message;
    errorDialog.showModal();
    document.querySelector('#error-close-dialog').onclick = () => errorDialog.close();
}