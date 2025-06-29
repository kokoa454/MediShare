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

selectTiming.addEventListener("change", updateHiddenValue)
selectSelectedTime.addEventListener("change", updateHiddenValue)
timingRadio.addEventListener("change", switchSelectGroup)
selectedTimeRadio.addEventListener("change", switchSelectGroup)

window.addEventListener("DOMContentLoaded", () => {
    switchSelectGroup()
});
