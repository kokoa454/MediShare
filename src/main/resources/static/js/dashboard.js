const timingContent = document.getElementById("timing-content");
const selectedTimeContent = document.getElementById("selected-time-content");

const timingButton = document.getElementById("timing-button");
const selectedTimeButton = document.getElementById("selected-time-button");

const toRegisterMedicine = document.getElementById("toRegisterMedicine");


toRegisterMedicine.addEventListener("click", function() {
    window.location.href = "/register_medicine";
});

timingButton.addEventListener("click", function() {
    timingContent.style.display = "grid";
    selectedTimeContent.style.display = "none";
    selectedTimeContent.disabled = true;
    timingButton.disabled = false;
    timingButton.classList.add("active");
    timingButton.classList.remove("inactive");
    selectedTimeButton.classList.remove("active");
    selectedTimeButton.classList.add("inactive");
});

selectedTimeButton.addEventListener("click", function() {
    timingContent.style.display = "none";
    timingContent.disabled = true;
    selectedTimeContent.style.display = "grid";
    selectedTimeButton.disabled = false;
    timingButton.classList.remove("active");
    timingButton.classList.add("inactive");
    selectedTimeButton.classList.add("active");
    selectedTimeButton.classList.remove("inactive");
});