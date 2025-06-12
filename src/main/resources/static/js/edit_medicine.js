const timingRadio = document.getElementById("medication-method-timing");
const selectedTimeRadio = document.getElementById("medication-method-selected-time");
const timingSelectGroup = document.getElementById("medication-method-timing-select-group");
const selectedTimeSelectGroup = document.getElementById("medication-method-selected-time-select-group");
const medicationMethodTimingSelect = document.getElementById("medication-method-timing-select");
const medicationMethodSelectedTimeSelect = document.getElementById("medication-method-selected-time-select");

timingRadio.addEventListener("change", function() {
    timingSelectGroup.style.display = "block";
    selectedTimeSelectGroup.style.display = "none";
    void selectedTimeSelectGroup.offsetHeight;
});

selectedTimeRadio.addEventListener("change", function() {
    timingSelectGroup.style.display = "none";
    selectedTimeSelectGroup.style.display = "block";
    void timingSelectGroup.offsetHeight;
});