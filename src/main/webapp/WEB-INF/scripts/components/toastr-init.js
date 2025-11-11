import toastr from 'toastr';

document.addEventListener('DOMContentLoaded', () => {
    const icons = {
        success: `<svg xmlns="http://www.w3.org/2000/svg" class="w-5 h-5 text-green-500" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7" /></svg>`,
        error: `<svg xmlns="http://www.w3.org/2000/svg" class="w-5 h-5 text-red-500" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" /></svg>`,
        warning: `<svg xmlns="http://www.w3.org/2000/svg" class="w-5 h-5 text-yellow-500" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 9v2m0 4h.01M12 5a7 7 0 100 14a7 7 0 000-14z" /></svg>`,
        info: `<svg xmlns="http://www.w3.org/2000/svg" class="w-5 h-5 text-blue-500" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 16h-1v-4h-1m1-4h.01M12 5a7 7 0 100 14a7 7 0 000-14z" /></svg>`
    };

    toastr.options = {
        closeButton: true,
        progressBar: false,
        timeOut: 3000,
        extendedTimeOut: 1000,
        tapToDismiss: true,
        positionClass: "toast-top-right",
        onShown: function(e) {
            const toastType = e.classList.contains("toast-success")
                ? "success"
                : e.classList.contains("toast-error")
                    ? "error"
                    : e.classList.contains("toast-warning")
                        ? "warning"
                        : "info";
            const icon = icons[toastType];
            if (icon) {
                e.querySelector(".toast-message").insertAdjacentHTML("afterbegin", `<div class="mr-2">${icon}</div>`);
            } else {
                console.error("toastr icon not found");
            }
        }
    };
})