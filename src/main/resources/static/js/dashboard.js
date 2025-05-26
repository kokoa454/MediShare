const timingContent = document.getElementById("timing-content");
const selectedTimeContent = document.getElementById("selected-time-content");

const timingButton = document.getElementById("timing-button");
const selectedTimeButton = document.getElementById("selected-time-button");

timingButton.addEventListener("click", function() {
    timingContent.style.display = "grid";
    selectedTimeContent.style.display = "none";
    timingButton.classList.add("active");
    timingButton.classList.remove("inactive");
    selectedTimeButton.classList.remove("active");
    selectedTimeButton.classList.add("inactive");
});

selectedTimeButton.addEventListener("click", function() {
    timingContent.style.display = "none";
    selectedTimeContent.style.display = "grid";
    timingButton.classList.remove("active");
    timingButton.classList.add("inactive");
    selectedTimeButton.classList.add("active");
    selectedTimeButton.classList.remove("inactive");
});