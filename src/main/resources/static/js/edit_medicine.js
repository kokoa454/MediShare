const timingRadio = document.getElementById("medication-method-timing")
const selectedTimeRadio = document.getElementById("medication-method-selected-time")
const timingSelectGroup = document.getElementById("medication-method-timing-select-group")
const selectedTimeSelectGroup = document.getElementById("medication-method-selected-time-select-group")
const selectSelectedTime = document.getElementById("medication-method-selected-time-select")
const selectTiming = document.getElementById("mediciation-method-timing-select")
const hiddenMedicationMethod = document.getElementById("hidden-medication-method")
const alreadyInputMedicineOfficialName = document.getElementById("medicine-official-name").value

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

    if (inputMedicineName.length < 2) {
        suggestionsList.innerHTML = "";
    }

    fetch(`/searchMedicineOfficialName?name=${encodeURIComponent(inputMedicineName)}`)
        .then(response => response.json())
        .then(data => {
            suggestionsList.innerHTML = "";
            data.forEach(medicine => {
                const option = document.createElement("option");
                option.value = medicine.medicineOfficialName;
                suggestionsList.appendChild(option);
                medicineNameMap.set(medicine.medicineOfficialName, medicine.urlKusurinoshiori);
            });
        });
}

function setKusurinoshioriUrl() {
    const medicineOfficialNameInput = document.getElementById("medicine-official-name").value;
    const urlKusurinoshioriInput = document.getElementById("url-kusurinoshiori");
    const url = medicineNameMap.get(medicineOfficialNameInput);

    if (url === undefined) {
        urlKusurinoshioriInput.value = null;
    } else {
        urlKusurinoshioriInput.value = url;
    }
}

function validateMedicineName() {
    const input = document.getElementById("medicine-official-name").value;
    const options = document.getElementById("medicine-official-name-suggestions").options;
    const errorMessage = document.getElementById("medicine-official-name-error");

    if(input != alreadyInputMedicineOfficialName) {
        let match = false;
        for (let i = 0; i < options.length; i++) {
            if (options[i].value === input) {
                match = true;
                break;
            }
        }

        if (!match) {
            errorMessage.style.display = "block";
            return false;
        } else {
            errorMessage.style.display = "none";
            return true;
        }
    }
}

function validateAndSubmit() {
    const input = document.getElementById("medicine-official-name").value;

    if(input != alreadyInputMedicineOfficialName) {
        if (input === "") {
            document.getElementById("medicine-official-name-error").style.display = "none";
            return true;
        }

        if (validateMedicineName()) {
            setKusurinoshioriUrl();
            return true;
        } else {
            document.getElementById("medicine-official-name-error").style.display = "block";
            return false;
        }
    }
}

selectTiming.addEventListener("change", updateHiddenValue)
selectSelectedTime.addEventListener("change", updateHiddenValue)
timingRadio.addEventListener("change", switchSelectGroup)
selectedTimeRadio.addEventListener("change", switchSelectGroup)

window.addEventListener("DOMContentLoaded", () => {
    switchSelectGroup()
});

let debounceTimer;
function debouncedSearchMedicine() {
    clearTimeout(debounceTimer);
    debounceTimer = setTimeout(searchMedicine, 400);
}