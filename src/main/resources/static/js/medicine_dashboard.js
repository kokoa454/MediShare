const medicineList = document.querySelectorAll('#medicine-list-container .medicine-group');
const toDeleteMedicine = document.querySelector('#toDeleteMedicine');
const confirmDialog = document.querySelector('#confirm-dialog');
const successDialog = document.querySelector('#success-dialog');
const errorDialog = document.querySelector('#error-dialog');
const toReportMedicine = document.querySelector('#toReportMedicine');
const csrfToken = document.querySelector('meta[name="_csrf"]').content;
const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;

medicineList.forEach(medicine => {
    let days = parseInt(medicine.querySelector('.prescription-days-text').textContent.replace('残り ', '').replace('日分', ''));
    if (days <= 5) {
        medicine.querySelector('.prescription-days-text').style.color = 'var(--red)';
    }
});

toDeleteMedicine.addEventListener('click', () => {
    const selectedIds = [];
    const selectedNames = [];

    medicineList.forEach(medicine => {
        if (medicine.querySelector('input[type="checkbox"]').checked) {
            selectedIds.push(medicine.querySelector('.hidden-user-medicine-id').value);
            selectedNames.push(medicine.querySelector('.medicine-name-text').textContent);
        }
    });

    if (selectedIds.length === 0) {
        showError('削除する薬を選択してください');
        return;
    }

    const nameContainer = document.querySelector('#delete-medicine-name');
    nameContainer.innerHTML = '';
    selectedNames.forEach(name => {
        const p = document.createElement('p');
        p.textContent = name;
        nameContainer.appendChild(p);
    });

    confirmDialog.showModal();

    document.querySelector('#confirm-delete-button').onclick = () => deleteMedicine(selectedIds);
    document.querySelector('#cancel-delete-button').onclick = () => confirmDialog.close();
});

function deleteMedicine(selectedIds) {
    fetch('/medicine_dashboard/delete_medicine', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            [csrfHeader]: csrfToken
        },
        body: JSON.stringify(selectedIds)
    })
    .then(response => {
        confirmDialog.close();
        if (response.ok) {
            successDialog.showModal();
            document.querySelector('#success-close-dialog').onclick = () => location.href = '/dashboard';
        } else {
            showError('薬の削除に失敗しました');
        }
    })
    .catch(() => {
        confirmDialog.close();
        showError('通信エラーが発生しました');
    });
}

function showError(message) {
    document.querySelector('#error-message').textContent = message;
    errorDialog.showModal();
    document.querySelector('#error-close-dialog').onclick = () => errorDialog.close();
}

toReportMedicine.addEventListener('click', (event) => {
    const selectedIds = [];
    const selectedNames = [];

    medicineList.forEach(medicine => {
        if (medicine.querySelector('input[type="checkbox"]').checked) {
            selectedIds.push(medicine.querySelector('.hidden-user-medicine-id').value);
            selectedNames.push(medicine.querySelector('.medicine-name-text').textContent);
        }
    });

    if (selectedIds.length === 0) {
        event.preventDefault();
        showError('報告する薬を選択してください')
        return
    } else {
        const userMedicineIds = selectedIds.join(',')
        location.href = `/report_medicine?method=${method}&userMedicineIds=${userMedicineIds}`
    }
})
