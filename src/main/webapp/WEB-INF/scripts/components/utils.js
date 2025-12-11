export const getBaseUrl = () => {
  const origin = window.location.origin;
  const contextPath = window.location.pathname.split("/")[1];
  return `${origin}/${contextPath}`;
}

export const decodeHtml = (html) => {
  const parser = new DOMParser();
  const decodedString = parser.parseFromString(html, "text/html");
  return decodedString.body.textContent;
}

export const togglePasswordVisibility = () => {
  const passwordInput = document.getElementById("password");

  if (!passwordInput) {
    console.debug("Create user form not found in DOM");
    return;
  }

  const eyeOnIcon = document.getElementById("eyeOnIcon");
  const eyeOffIcon = document.getElementById("eyeOffIcon");
  const togglePasswordVisibilityButton = document.getElementById("togglePasswordVisibilityButton");

  if (eyeOnIcon && eyeOffIcon && togglePasswordVisibilityButton) {
    togglePasswordVisibilityButton.addEventListener("click", function () {
      passwordInput.type = passwordInput.type === "password" ? "text" : "password";

      eyeOnIcon.classList.toggle("hidden");
      eyeOffIcon.classList.toggle("hidden");
    })
  }
}

export const defaultJsonRequestHeaders = {
  "Content-Type": "application/json;charset=utf-8",
}

export const setProfessionalDefaultValue = (id) => {
  const professionalIdInput = document.getElementById('professionalId-edit');
  const professionalSelectTrigger = document.getElementById('professionals-select-trigger');
  if (id) {
    professionalIdInput.value = id;

    const professionalOption = document.querySelector(`#professionals-select-listbox [data-value="${professionalId}"]`);
    if (!professionalOption) return;

    const triggerSpan = professionalSelectTrigger.querySelector('span');
    if (triggerSpan) {
      triggerSpan.textContent = professionalOption.textContent.trim();
    } else {
      console.error("Professional select trigger not found in DOM");
    }
  }
}

export const setDefaultAppointmentState = (state) => {
  const appointmentStateInput = document.getElementById('appointmentState-edit');
  const appointmentStateTrigger = document.getElementById('appointment-state-select-trigger');

  if (state) {
    appointmentStateInput.value = state;

    const appointmentStateOption = document.querySelector(`#appointment-state-select-listbox [data-value="${state}"]`);
    if (!appointmentStateOption) return;

    const triggerSpan = appointmentStateTrigger.querySelector('span');
    if (triggerSpan) {
      triggerSpan.textContent = appointmentStateOption.textContent.trim();
    } else {
      console.error("Appointment state select trigger not found in DOM");
    }
  }
}

export const setDefaultUserValue = (id) => {
  const userIdInput = document.getElementById('userId-edit');
  const userSelectTrigger = document.getElementById('users-select-trigger');

  if (id) {
    userIdInput.value = id;
    const userOption = document.querySelector(`#users-select-listbox [data-value="${id}"]`);
    if (!userOption) return;

    const triggerSpan = userSelectTrigger.querySelector('span');

    if (triggerSpan) {
      triggerSpan.textContent = userOption.textContent.trim();
    } else {
      console.error("User select trigger not found in DOM");
    }
  }
}

export const cleanUpFormErrors = () => {
  const errorElements = document.getElementsByTagName("p");
  [...errorElements].forEach(element => {
    if (element.id.includes("-error") && !element.classList.contains("hidden")) {
      element.classList.add("hidden");
      element.textContent = "";
    }
  })
}