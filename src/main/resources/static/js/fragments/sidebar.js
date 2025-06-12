// for hamburger menu
document.querySelector('.hamburger-menu').addEventListener('click', function() {
    this.classList.toggle('active');
    document.querySelector('.overlay-menu').classList.toggle('active');
    document.body.classList.toggle('menu-open');
});

// for desktop tips
document.querySelectorAll('#tips-containers-desktop .tip').forEach(tip => {
    tip.addEventListener('change', function() {
        const content = this.nextElementSibling.nextElementSibling;
        
        // close unselected tips
        document.querySelectorAll('#tips-containers-desktop .tip').forEach(otherTip => {
            if (otherTip !== this) {
                otherTip.checked = false;
                const otherContent = otherTip.nextElementSibling.nextElementSibling;
                otherContent.style.height = "0px";
            }
        });

        // open selected tip
        if (this.checked) {
            content.style.height = "auto";
            const height = content.scrollHeight + 20 + "px"; //20 means bottom padding height
            content.style.height = "0px";
            requestAnimationFrame(() => {
                content.style.height = height;
            });
        } else {
            content.style.height = "0px";
        }
    });
});

// for mobile tips
document.querySelectorAll('#tips-containers-mobile .tip').forEach(tip => {
    tip.addEventListener('change', function() {
        const content = this.nextElementSibling.nextElementSibling;
        
        // close unselected tips
        document.querySelectorAll('#tips-containers-mobile .tip').forEach(otherTip => {
            if (otherTip !== this) {
                otherTip.checked = false;
                const otherContent = otherTip.nextElementSibling.nextElementSibling;
                otherContent.style.height = "0px";
            }
        });

        // open selected tip
        if (this.checked) {
            content.style.height = "auto";
            const height = content.scrollHeight + 20 + "px"; //20 means bottom padding height
            content.style.height = "0px";
            requestAnimationFrame(() => {
                content.style.height = height;
            });
        } else {
            content.style.height = "0px";
        }
    });
});