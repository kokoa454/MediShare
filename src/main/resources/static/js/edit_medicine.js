const timingRadio = document.getElementById("medication-method-timing")
const selectedTimeRadio = document.getElementById("medication-method-selected-time")
const timingSelectGroup = document.getElementById("medication-method-timing-select-group")
const selectedTimeSelectGroup = document.getElementById("medication-method-selected-time-select-group")
const selectSelectedTime = document.getElementById("medication-method-selected-time-select")
const selectTiming = document.getElementById("mediciation-method-timing-select")
const hiddenMedicationMethod = document.getElementById("hidden-medication-method")

function updateHiddenValue() {
    if (timingRadio.checked) {
        hiddenMedicationMethod.value = selectTiming.value
    } else if (selectedTimeRadio.checked) {
        hiddenMedicationMethod.value = selectSelectedTime.value
    }
}

function switchSelectGroup() {
    if (timingRadio.checked) {
        timingSelectGroup.style.display = "block"
        selectedTimeSelectGroup.style.display = "none"
    } else if (selectedTimeRadio.checked) {
        timingSelectGroup.style.display = "none"
        selectedTimeSelectGroup.style.display = "block"
    }
    updateHiddenValue()
}
const medicineNameMap = new Map();

function searchMedicine() {
    const inputMedicineName = document.getElementById("medicine-official-name").value;
    const suggestionsList = document.getElementById("medicine-official-name-suggestions");

    if (inputMedicineName.length < 1) {
        suggestionsList.innerHTML = "";
        return;
    }

    fetch(`/searchMedicineOfficialName?name=${encodeURIComponent(inputMedicineName)}`)
        .then(response => response.json())
        .then(data => {
            suggestionsList.innerHTML = "";
            data.forEach(medicine => {
                const option = document.createElement("option");
                option.value = medicine.medicineOfficialName;
                suggestionsList.appendChild(option);
                medicineNameMap.set(medicine.medicineOfficialName, medicine.urlKusurinoShiori);
            });
        });
}

function setKusuriNoShioriUrl() {
    const medicineOfficialNameInput = document.getElementById("medicine-official-name").value;
    const urlKusurinoshioriInput = document.getElementById("url-kusurinoshiori");
    const url = medicineNameMap.get(medicineOfficialNameInput);

    if (url === undefined) {
        urlKusurinoshioriInput.value = null;
    } else {
        urlKusurinoshioriInput.value = url;
    }
}


selectTiming.addEventListener("change", updateHiddenValue)
selectSelectedTime.addEventListener("change", updateHiddenValue)
timingRadio.addEventListener("change", switchSelectGroup)
selectedTimeRadio.addEventListener("change", switchSelectGroup)

window.addEventListener("DOMContentLoaded", () => {
    switchSelectGroup()
});
