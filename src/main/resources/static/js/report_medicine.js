const reportButton = document.getElementById("send-mail-button");
const confirmDialog = document.getElementById("confirm-dialog");
const successDialog = document.getElementById("success-dialog");
const errorDialog = document.getElementById("error-dialog");
const familyEmail = document.getElementById("family-email");
const csrfToken = document.querySelector('meta[name="_csrf"]').content;
const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;

reportButton.addEventListener("click", function() {
    if (!familyEmail.value) {
        showError('設定画面からご家族のメールアドレスを入力してください');
        return;
    } else {
        const medicineElements = document.querySelectorAll('.medicine-name');
        const medicines = Array.from(medicineElements).map(el => el.textContent);
        const method = document.getElementById('medication-method').textContent;
        const userCondition = document.querySelector('input[name="user-condition"]:checked').value;
        const userComment = document.getElementById('user-comment').value;

        const reportData = {
            medicines: medicines,
            medicationMethod: method,
            userCondition: userCondition,
            userComment: userComment
        };

        confirmDialog.showModal();

        document.querySelector('#cancel-report-button').onclick = function() {
            confirmDialog.close();
        };

        document.querySelector('#confirm-report-button').onclick = function() {
            fetch('/report_medicine', {
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
                    showError('服薬の報告に失敗しました');
                }
            })
            .catch(() => {
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