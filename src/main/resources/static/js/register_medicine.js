const timingRadio = document.getElementById("medication-method-timing");
const selectedTimeRadio = document.getElementById("medication-method-selected-time");
const timingSelectGroup = document.getElementById("medication-method-timing-select-group");
const selectedTimeSelectGroup = document.getElementById("medication-method-selected-time-select-group");
const selectSelectedTime = document.getElementById("medication-method-selected-time-select");
const selectTiming = document.getElementById("medication-method-timing-select")

timingRadio.addEventListener("change", function() {
    timingSelectGroup.style.display = "block";
    selectedTimeSelectGroup.style.display = "none";
    
    selectTiming.setAttribute("name", "methodCode")
    selectSelectedTime.removeAttribute("name")

    selectTiming.setAttribute("required", "required")
    selectSelectedTime.removeAttribute("required")

    timingRadio.checked = true
    selectedTimeRadio.checked = false
});

selectedTimeRadio.addEventListener("change", function() {
    timingSelectGroup.style.display = "none";
    selectedTimeSelectGroup.style.display = "block";

    selectTiming.removeAttribute("name")
    selectSelectedTime.setAttribute("name", "methodCode")

    selectTiming.removeAttribute("required")
    selectSelectedTime.setAttribute("required", "required")

    selectedTimeRadio.checked = true
    timingRadio.checked = false
});
