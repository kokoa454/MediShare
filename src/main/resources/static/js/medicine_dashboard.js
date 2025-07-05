const medicineList = document.querySelectorAll('#medicine-list-container .medicine-group');
const toDeleteMedicine = document.querySelector('#toDeleteMedicine');
const confirmDialog = document.querySelector('#confirm-dialog');
const successDialog = document.querySelector('#success-dialog');
const errorDialog = document.querySelector('#error-dialog');
const toReportMedicine = document.querySelector('#toReportMedicine');
const csrfToken = document.querySelector('meta[name="_csrf"]').content;
const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;
const searchButton = document.querySelector('#search-button');

medicineList.forEach(medicine => {
    let days = parseInt(medicine.querySelector('.prescription-days-text').textContent.replace('残り ', '').replace('日分', ''));
    if (days <= 5) {
        medicine.querySelector('.prescription-days-text').style.color = 'var(--red)';
    }

    let isCompleted = medicine.querySelector('.hidden-is-completed').value;
    if (isCompleted == 'true') {
        medicine.style.backgroundColor = 'var(--mikan)';
        medicine.querySelector('.medicine-icon').style.backgroundColor = 'var(--mikan)';
        medicine.querySelector('.medicine-name').style.backgroundColor = 'var(--mikan)';
        medicine.querySelector('.medicine-name-text').style.backgroundColor = 'var(--mikan)';
        medicine.querySelector('.prescription-days-container').style.backgroundColor = 'var(--mikan)';
        medicine.querySelector('.prescription-days-text').style.backgroundColor = 'var(--mikan)';
        medicine.querySelector('.calender-icon').style.backgroundColor = 'var(--mikan)';
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
        if (response.ok) return response.text();
        else return response.text().then(msg => { throw new Error(msg); });
    })
    .then(message => {
        confirmDialog.close();
        showSuccess(message);
        document.querySelector('#success-close-dialog').onclick = () => location.href = '/dashboard';
    })
    .catch((error) => {
        confirmDialog.close();
        showError(error.message || '通信エラーが発生しました');
    });
}

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

toReportMedicine.addEventListener('click', (event) => {
    const selectedIds = [];
    const selectedNames = [];
    let hasCompleted = false;
    let noMoreMedicine = false;

    medicineList.forEach(medicine => {
        if (medicine.querySelector('input[type="checkbox"]').checked) {
            if(medicine.querySelector('.hidden-is-completed').value == 'true') {
                hasCompleted = true;
            } else if (medicine.querySelector('.prescription-days-text').textContent == '残り 0日分') {
                noMoreMedicine = true;
            } else{
                selectedIds.push(medicine.querySelector('.hidden-user-medicine-id').value);
                selectedNames.push(medicine.querySelector('.medicine-name-text').textContent);
            }
        }
    });

    if (hasCompleted == true) {
        event.preventDefault();
        showError('本日服用した薬は報告できません')
        return
    } else if (noMoreMedicine == true) {
        event.preventDefault();
        showError('残りがない薬は報告できません')
        return
    } else if (selectedIds.length === 0) {
        event.preventDefault();
        showError('報告する薬を選択してください')
        return
    } else {
        const userMedicineIds = selectedIds.join(',')
        location.href = `/report_medicine?method=${method}&userMedicineIds=${userMedicineIds}`
    }
})

searchButton.addEventListener('click', () => {
    const searchKeyword = document.querySelector('#search-input').value
    location.href = `/medicine_dashboard?method=${method}&search=${encodeURIComponent(searchKeyword)}`
    document.querySelector('#search-input').value = ''
})