const medicineList = document.querySelectorAll('#medicine-list-container .medicine-group')
const deleteButton = document.querySelector('#delete-button')
const csrfToken = document.querySelector('meta[name="_csrf"]').content
const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content

medicineList.forEach(medicine => {
    let prescriptionDays = medicine.querySelector('.prescription-days-text').textContent
    prescriptionDays = prescriptionDays.replace('残り ', '').replace('日分', '')
    prescriptionDays = parseInt(prescriptionDays)

    if (prescriptionDays <= 5) {
        const textElem = medicine.querySelector('.prescription-days-text')
        textElem.style.color = 'var(--red)'
        textElem.style.fontSize = '28px'
    }
});

deleteButton.addEventListener('click', () => {
    const selectedMedicines = []
    medicineList.forEach(medicine => {
        if (medicine.querySelector('.medicine-checkbox').checked) {
            selectedMedicines.push(medicine.querySelector('.hidden-user-medicine-id').value)
        }
    });

    if (selectedMedicines.length > 0) {
        console.log(selectedMedicines)
        const confirmDelete = confirm('選択した薬剤を削除しますか？')
        if (confirmDelete) {
            fetch('/medicine_dashboard/delete_medicine', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    [csrfHeader]: csrfToken
                },
                body: JSON.stringify(selectedMedicines)
            })
            .then(response => {
                if (response.ok) {
                    location.href = '/dashboard'
                } else {
                    alert("削除に失敗しました。")
                }
            })
            .catch(error => {
                console.error(error);
                alert("通信エラーが発生しました。")
            });
        }
    } else {
        alert("削除する薬剤を選択してください。")
    }
})
