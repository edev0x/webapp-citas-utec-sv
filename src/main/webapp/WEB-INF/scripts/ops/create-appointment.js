import { getBaseUrl, defaultJsonRequestHeaders } from "../components/utils";

function createAppointment() {
  const appointmentCreationDialog = document.getElementById("appointment-creation-dialog");
  if (!appointmentCreationDialog) return;

  const createAppointmentForm = document.getElementById("appointment-creation-form");
  const cancelButton = createAppointmentForm.querySelector("#cancel-appointment-button");
  const submitButton = createAppointmentForm.querySelector("#save-appointment-button");

  const generalError = createAppointmentForm.querySelector("#general-error");

  cancelButton.addEventListener("click", function () {
    appointmentCreationDialog.close();
    createAppointmentForm.reset();
    cleanUpErrorMessages();
  });

  createAppointmentForm.addEventListener("submit", function (event) {
    event.preventDefault();

    try {
      submitButton.disabled = true;
      cancelButton.disabled = true;
      submitButton.textContent = "Por favor espere...";

      const formData = new FormData(event.target);
      const payload = {
        userId: Number(formData.get("userId")),
        professionalId: Number(formData.get("professionalId")),
        appointmentDate: formData.get("appointmentDate"),
        appointmentStartTime: formData.get("appointmentStartTime"),
        appointmentEndTime: formData.get("appointmentEndTime"),
        state: formData.get("appointmentState"),
        reason: formData.get("appointmentDescription"),
      };

      console.log(payload);

      fetch(`${getBaseUrl()}/appointments`, {
        method: "POST",
        headers: defaultJsonRequestHeaders,
        body: JSON.stringify(payload),
      }).then(async (res) => {
        if (res.status === 201) {
          appointmentCreationDialog.close();
          toastr.success("Cita agendada exitosamente.");
          setTimeout(() => {
            window.location.reload();
          }, 3000);
        } else {
          const data = await res.json();
          if (data.validationErrors) {
            Object.entries(data.validationErrors).forEach(([field, message]) => {
              const errorElement = document.getElementById(`${field}-error`);
              if (errorElement) {
                errorElement.textContent = message;
                errorElement.classList.remove("hidden");
              }
            })
          }

          if (data.message) generalError.textContent = data.message;
          toastr.error("Ha ocurrido un error al procesar la solicitud.");
        }
      })
    } catch (error) {
      console.error("Error creating appointment:", error);
      toastr.error("Ha ocurrido un error al procesar la solicitud.");
    } finally {
      submitButton.disabled = false;
      submitButton.textContent = "Guardar";
      cancelButton.disabled = false;
      cleanUpErrorMessages();
    }
  })
}

function cleanUpErrorMessages() {
  const errorElements = document.getElementsByTagName("p");
  [...errorElements].forEach(element => {
    if (element.id.includes("-error") && !element.classList.contains("hidden")) {
      element.classList.add("hidden");
      element.textContent = "";
    }
  })
}

document.addEventListener("DOMContentLoaded", createAppointment);