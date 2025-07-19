function switchSelectGroup() {
    const methodSelect = document.getElementById("medicationMethod");
    const selectedType = document.querySelector('input[name="methodType"]:checked').value;

    methodSelect.innerHTML = "";

    if (selectedType === "timing") {
        methodSelect.innerHTML = `
            <option value="起床時">起床時</option>
            <option value="朝食前">朝食前</option>
            <option value="朝食後">朝食後</option>
            <option value="昼食前">昼食前</option>
            <option value="昼食後">昼食後</option>
            <option value="夕食前">夕食前</option>
            <option value="夕食後">夕食後</option>
            <option value="就寝前">就寝前</option>
            <option value="食間">食間</option>
        `;
    } else {
        methodSelect.innerHTML = `
            <option value="0時">0時</option>
            <option value="1時">1時</option>
            <option value="2時">2時</option>
            <option value="3時">3時</option>
            <option value="4時">4時</option>
            <option value="5時">5時</option>
            <option value="6時">6時</option>
            <option value="7時">7時</option>
            <option value="8時">8時</option>
            <option value="9時">9時</option>
            <option value="10時">10時</option>
            <option value="11時">11時</option>
            <option value="12時">12時</option>
            <option value="13時">13時</option>
            <option value="14時">14時</option>
            <option value="15時">15時</option>
            <option value="16時">16時</option>
            <option value="17時">17時</option>
            <option value="18時">18時</option>
            <option value="19時">19時</option>
            <option value="20時">20時</option>
            <option value="21時">21時</option>
            <option value="22時">22時</option>
            <option value="23時">23時</option>
        `;
    }
}

const medicineNameMap = new Map()

function searchMedicine(){
    const inputMedicineName = document.getElementById("medicine-official-name").value;
    const suggestionsList = document.getElementById("medicine-official-name-suggestions");

    if( inputMedicineName.length < 1) {
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
        }
    )
}

function setKusuriNoshioriUrl() {
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

function validateAndSubmit() {
    const input = document.getElementById("medicine-official-name").value;

    if (input === "") {
        document.getElementById("medicine-official-name-error").style.display = "none";
        return true;
    }

    if (validateMedicineName()) {
        setKusuriNoshioriUrl();
        return true;
    } else {
        document.getElementById("medicine-official-name-error").style.display = "block";
        return false;
    }
}

