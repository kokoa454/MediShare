const medicineList = document.querySelectorAll('#medicine-list-container .medicine-group');

medicineList.forEach(medicine => {
    prescriptionDays = medicine.querySelector('.prescription-days-text').textContent;
    prescriptionDays = prescriptionDays.replace('残り ', '');
    prescriptionDays = prescriptionDays.replace('日', '');
    prescriptionDays = parseInt(prescriptionDays);

    console.log(prescriptionDays);

    if(prescriptionDays <= 5){
        medicine.querySelector('.prescription-days-text').style.color = 'var(--red)';
        medicine.querySelector('.prescription-days-text').style.fontSize = '28px';
    }
});